package technology.rocketjump.civblitz.matches;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import technology.rocketjump.civblitz.auth.AuditLogger;
import technology.rocketjump.civblitz.cards.CollectionService;
import technology.rocketjump.civblitz.cards.PackService;
import technology.rocketjump.civblitz.codegen.tables.pojos.Match;
import technology.rocketjump.civblitz.codegen.tables.pojos.MatchSignup;
import technology.rocketjump.civblitz.codegen.tables.pojos.Player;
import technology.rocketjump.civblitz.codegen.tables.pojos.SecretObjective;
import technology.rocketjump.civblitz.mapgen.MapSettings;
import technology.rocketjump.civblitz.mapgen.MapSettingsGenerator;
import technology.rocketjump.civblitz.matches.objectives.ObjectivesService;
import technology.rocketjump.civblitz.model.*;
import technology.rocketjump.civblitz.players.PlayerRepo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static technology.rocketjump.civblitz.matches.MatchState.*;

@Service
public class MatchService {

	private static final Integer STARS_NEEDED_TO_WIN_GAME = 7;
	private final AdjectiveNounNameGenerator adjectiveNounNameGenerator;
	private final MatchRepo matchRepo;
	private final CollectionService collectionService;
	private final SourceDataRepo sourceDataRepo;
	private final PlayerRepo playerRepo;
	private final MapSettingsGenerator mapSettingsGenerator;
	private final Random random = new Random();
	private final ObjectivesService objectivesService;
	private final PackService packService;
	private final LeaderboardService leaderboardService;
	private final AuditLogger auditLogger;

	@Autowired
	public MatchService(AdjectiveNounNameGenerator adjectiveNounNameGenerator, MatchRepo matchRepo,
						CollectionService collectionService, SourceDataRepo sourceDataRepo,
						PlayerRepo playerRepo, MapSettingsGenerator mapSettingsGenerator,
						ObjectivesService objectivesService, PackService packService, LeaderboardService leaderboardService, AuditLogger auditLogger) {
		this.adjectiveNounNameGenerator = adjectiveNounNameGenerator;
		this.matchRepo = matchRepo;
		this.collectionService = collectionService;
		this.sourceDataRepo = sourceDataRepo;
		this.playerRepo = playerRepo;
		this.mapSettingsGenerator = mapSettingsGenerator;
		this.objectivesService = objectivesService;
		this.packService = packService;
		this.leaderboardService = leaderboardService;
		this.auditLogger = auditLogger;
	}


	public Match create(String matchName, String timeslot) {
		if (matchName == null || matchName.isBlank()) {
			Optional<Match> existingMatchWithSameName;
			do {
				matchName = adjectiveNounNameGenerator.generateName(random);
				existingMatchWithSameName = matchRepo.getMatchByName(matchName);
			} while (existingMatchWithSameName.isPresent());
		} else if (matchRepo.getMatchByName(matchName).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "A match with this name already exists");
		}

		return matchRepo.createMatch(matchName, timeslot);
	}

	public List<MatchWithPlayers> getUncompletedMatches() {
		return matchRepo.getUncompletedMatches();
	}

	public synchronized void signup(int matchId, Player player) {
		Optional<MatchWithPlayers> matchById = matchRepo.getMatchById(matchId);
		if (matchById.isPresent()) {
			matchRepo.signup(matchById.get(), player);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	public void resign(int matchId, Player player) {
		Optional<MatchWithPlayers> matchById = matchRepo.getMatchById(matchId);
		if (matchById.isPresent()) {
			if (!matchById.get().getMatchState().equals(SIGNUPS)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Can not resign from an in-progress match, only during signups");
			}
			matchRepo.resign(matchById.get(), player);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	public Optional<MatchWithPlayers> getById(int matchId) {
		return matchRepo.getMatchById(matchId);
	}

	public Match updateSecretObjectiveSelection(Match match, String name, String timeslot) {
		match.setMatchName(name);
		match.setTimeslot(timeslot);
		matchRepo.update(match);
		return match;
	}

	public synchronized Match switchState(MatchWithPlayers match, MatchState newState, Map<String, Object> payload, Player currentPlayer) {
		MatchState currentState = match.getMatchState();
		if (currentState.equals(SIGNUPS) && newState.equals(DRAFT)) {
			proceedToDraft(match, payload);
		} else if (currentState.equals(DRAFT) && newState.equals(SIGNUPS)) {
			revertToSignups(match);
		} else if (currentState.equals(DRAFT) && newState.equals(IN_PROGRESS)) {
			proceedToInProgress(match);
		} else if (currentState.equals(IN_PROGRESS) && newState.equals(POST_MATCH)) {
			proceedToPostMatch(match);
		} else if (currentState.equals(POST_MATCH) && newState.equals(IN_PROGRESS)) {
			revertToInProgress(match);
		} else if (currentState.equals(POST_MATCH) && newState.equals(COMPLETED)) {
			completeMatch(match, payload, currentPlayer);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not yet implemented, transition from " + currentState + " to " + newState);
		}
		return match;
	}

	private void proceedToDraft(MatchWithPlayers match, Map<String, Object> payload) {
		List<String> selectedPlayerIds = (List<String>) payload.get("playerIds");
		if (selectedPlayerIds.size() < 2) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must be a minimum of 2 selected players");
		}

		for (MatchSignupWithPlayer signup : match.signups) {
			if (!selectedPlayerIds.contains(signup.getPlayerId())) {
				resign(match.getMatchId(), signup.getPlayer());
			}
		}

		int numPlayers = selectedPlayerIds.size();
		if (Boolean.TRUE.equals(match.getSpectator())) {
			numPlayers++;
		}
		MapSettings mapSettings = mapSettingsGenerator.generate(numPlayers);
		apply(mapSettings, match);
		match.setMatchState(DRAFT);
		matchRepo.update(match);
		objectivesService.initialiseObjectives(match);
	}

	private void revertToSignups(Match match) {
		MatchWithPlayers matchWithPlayers = matchRepo.getMatchById(match.getMatchId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		for (MatchSignupWithPlayer signup : matchWithPlayers.signups) {
			signup = uncommitPlayer(matchWithPlayers, signup.getPlayer());

			for (CardCategory category : CardCategory.values()) {
				String selectedCard = signup.getCard(category);
				if (selectedCard != null) {
					removeCardFromMatchDeck(matchWithPlayers, signup.getPlayer(), selectedCard);
				}
			}

			signup.setStartBiasCivType(null);
			matchRepo.updateSignup(signup);
		}

		match.setMatchState(SIGNUPS);
		matchRepo.update(match);
		objectivesService.clearObjectives(match);
	}

	private void proceedToInProgress(Match match) {

		MatchWithPlayers matchWithPlayers = matchRepo.getMatchById(match.getMatchId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		for (MatchSignupWithPlayer signup : matchWithPlayers.signups) {
			packService.addMatchBooster(signup);
		}

		match.setMatchState(IN_PROGRESS);
		matchRepo.update(match);
	}

	private void proceedToPostMatch(Match match) {
		match.setMatchState(POST_MATCH);
		matchRepo.update(match);
	}

	private void revertToInProgress(Match match) {
		match.setMatchState(IN_PROGRESS);
		matchRepo.update(match);
	}

	private synchronized void completeMatch(MatchWithPlayers match, Map<String, Object> payload, Player currentPlayer) {
//		if (match.signups.stream().anyMatch(s -> s.getPlayerId().equals(currentPlayer.getPlayerId()))) {
//			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can not complete a match you played in");
//		}

		for (MatchSignupWithPlayer signup : match.signups) {
			if (!payload.containsKey(signup.getPlayerId())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload does not contain an entry for player " + signup.getPlayerId());
			}
		}

		Map<String, Integer> leaderboard = leaderboardService.getLeaderboard(match);

		StringBuilder auditBuilder = new StringBuilder();
		for (MatchSignupWithPlayer signup : match.signups) {
			int leaderboardStarAmount = leaderboard.get(signup.getPlayerId());
			int awardedStarAmount = (Integer) payload.get(signup.getPlayerId());
			if (awardedStarAmount != leaderboardStarAmount) {
				if (awardedStarAmount < leaderboardStarAmount) {
					auditBuilder.append("Decreased stars to ");
				} else {
					auditBuilder.append("Increased stars to ");
				}
				auditBuilder.append(awardedStarAmount).append(" from ").append(leaderboardStarAmount).append(" for ")
						.append(signup.getPlayer().getDiscordUsername()).append("\n");
			}

			signup.setFinalPointsAwarded(0.0 + awardedStarAmount);
			matchRepo.updateSignup(signup);

			signup.getPlayer().setBalance(signup.getPlayer().getBalance() + awardedStarAmount);
			signup.getPlayer().setTotalPointsEarned(signup.getPlayer().getTotalPointsEarned() + awardedStarAmount);
			signup.getPlayer().setRankingScore(calculateAveragePoints(signup.getPlayer()));
			playerRepo.updateBalances(signup.getPlayer());
		}

		match.setMatchState(COMPLETED);
		matchRepo.update(match);

		if (auditBuilder.length() > 0) {
			auditBuilder.append("For match ").append(match.getMatchName());
			auditLogger.record(currentPlayer, auditBuilder.toString(), match);
		}
	}

	public synchronized MatchSignupWithPlayer addCardToMatchDeck(MatchWithPlayers match, Player player, String cardTraitType, boolean applyFreeUse) {
		if (!match.getMatchState().equals(DRAFT)) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can only add to deck during draft phase");
		}

		List<CollectionCard> playerCollection = collectionService.getCollection(player);
		CollectionCard cardInCollection = playerCollection.stream().filter(c -> c.getTraitType().equals(cardTraitType)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card does not exist in player's collection"));
		if (cardInCollection.getQuantity() < 1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card has no quantity in player's collection");
		}

		MatchSignupWithPlayer matchSignup = getSignup(match, player);
		if (matchSignup.getCommitted()) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can not change cards once committed");
		}

		String existingCardSelection = matchSignup.getCard(cardInCollection.getCardCategory());
		if (existingCardSelection != null) {
			removeCardFromMatchDeck(match, player, existingCardSelection);
		}

		if (applyFreeUse) {
			String freeUseCardTraitType = cardInCollection.getGrantsFreeUseOfCard()
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card does not come with a free use card"));
			Card freeUseCard = sourceDataRepo.getByTraitType(freeUseCardTraitType);
			existingCardSelection = matchSignup.getCard(freeUseCard.getCardCategory());
			if (existingCardSelection != null) {
				removeCardFromMatchDeck(match, player, existingCardSelection);
			}

			matchSignup.setCard(freeUseCard);
			matchSignup.setFreeUse(freeUseCard, true);
		}

		matchSignup.setCard(cardInCollection);
		matchRepo.updateSignup(matchSignup);
		collectionService.removeFromCollection(cardInCollection, player);

		return matchSignup;
	}

	public synchronized MatchSignupWithPlayer removeCardFromMatchDeck(MatchWithPlayers match, Player player, String cardTraitType) {
		if (!match.getMatchState().equals(DRAFT)) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can only remove from deck during draft phase");
		}

		MatchSignupWithPlayer matchSignup = getSignup(match, player);
		if (matchSignup.getCommitted()) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can not change cards once committed");
		}

		Card card = sourceDataRepo.getByTraitType(cardTraitType);
		if (matchSignup.getCard(card.getCardCategory()) == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card is not already part of selected deck");
		}

		matchSignup.removeCard(card);
		if (card.getCivilizationType().equals(matchSignup.getStartBiasCivType())) {
			matchSignup.setStartBiasCivType(null);
		}
		if (matchSignup.isFreeUse(card)) {
			matchSignup.setFreeUse(card, false);
		} else {
			collectionService.addToCollection(card, player);
		}
		// check if need to remove free use granted card
		if (card.getGrantsFreeUseOfCard().isPresent()) {
			Card freeUseCard = sourceDataRepo.getByTraitType(card.getGrantsFreeUseOfCard().get());
			if (freeUseCard.getTraitType().equals(matchSignup.getCard(freeUseCard.getCardCategory())) &&
					matchSignup.isFreeUse(freeUseCard)) {
				removeCardFromMatchDeck(match, player, freeUseCard.getTraitType());
			}
		}
		matchRepo.updateSignup(matchSignup);
		return matchSignup;
	}

	public MatchSignupWithPlayer updateStartBias(MatchWithPlayers match, Player player, String biasCivType) {
		if (!match.getMatchState().equals(DRAFT)) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can only update start bias during draft phase");
		}

		if (!sourceDataRepo.civNameByCivType.containsKey(biasCivType)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unrecognised civ type " + biasCivType);
		}

		MatchSignupWithPlayer matchSignup = getSignup(match, player);
		if (matchSignup.getCommitted()) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can not change start bias once committed");
		}

		matchSignup.setStartBiasCivType(biasCivType);
		matchRepo.updateSignup(matchSignup);

		return matchSignup;
	}

	public void updateSecretObjectiveSelection(SecretObjective secretObjective) {
		MatchWithPlayers match = matchRepo.getMatchById(secretObjective.getMatchId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (!match.getMatchState().equals(DRAFT)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only modify secret objective selections during draft phase");
		}

		MatchSignupWithPlayer signup = getSignup(match, secretObjective.getPlayerId());
		if (signup.getCommitted()) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can not change objective selection once committed");
		}

		objectivesService.update(secretObjective);
	}

	public MatchSignupWithPlayer commitPlayer(MatchWithPlayers match, Player player) {
		MatchSignupWithPlayer matchSignup = getSignup(match, player);
		if (matchSignup.getCommitted()) {
			return matchSignup;
		}

		if (!match.getMatchState().equals(DRAFT)) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can only commit during the draft phase");
		}

		for (CardCategory category : CardCategory.values()) {
			if (matchSignup.getCard(category) == null) {
				throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "All cards must be assigned to commit");
			}
		}
		if (matchSignup.getStartBiasCivType() == null) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Start bias must be assigned to commit");
		}

		long selectedSecretObjectives = objectivesService.getSecretObjectives(match.getMatchId(), player)
				.stream()
				.filter(SecretObjective::getSelected)
				.count();
		if (selectedSecretObjectives != 3) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "You must select 3 secret objectives");
		}
//		if (selectedSecretObjectives > 5) {
//			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can select a maximum of 5 secret objectives");
//		}

		matchSignup.setCommitted(true);
		matchRepo.updateSignup(matchSignup);

		checkForAllCommitted(match);

		return matchSignup;
	}

	public MatchSignupWithPlayer uncommitPlayer(MatchWithPlayers match, Player player) {
		MatchSignupWithPlayer matchSignup = getSignup(match, player);
		if (!matchSignup.getCommitted()) {
			return matchSignup;
		}

		if (!match.getMatchState().equals(DRAFT)) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can only uncommit during the draft phase");
		}

		matchSignup.setCommitted(false);
		matchRepo.updateSignup(matchSignup);

		return matchSignup;
	}

	private MatchSignupWithPlayer getSignup(MatchWithPlayers match, Player player) {
		return getSignup(match, player.getPlayerId());
	}

	private MatchSignupWithPlayer getSignup(MatchWithPlayers match, String playerId) {
		return match.signups.stream().filter(signup -> signup.getPlayerId().equals(playerId)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player is not part of specified match"));
	}

	private void checkForAllCommitted(MatchWithPlayers match) {
		if (match.signups.stream().allMatch(MatchSignup::getCommitted)) {
			switchState(match, MatchState.IN_PROGRESS, null, null);
		}
	}

	private void apply(MapSettings mapSettings, Match match) {
		match.setStartEra(mapSettings.startEra);
		match.setMapType(mapSettings.mapType);
		match.setMapSize(mapSettings.mapSize);
		match.setWorldAge(mapSettings.worldAge);
		match.setSeaLevel(mapSettings.seaLevel);
		match.setTemperature(mapSettings.temperature);
		match.setRainfall(mapSettings.rainfall);
		match.setCityStates(mapSettings.numCityStates);
		match.setDisasterIntensity(mapSettings.disasterIntensity);
	}

	public void delete(MatchWithPlayers match) {
		if (match.getMatchState().equals(SIGNUPS)) {
			matchRepo.delete(match);
		} else {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can only delete a match in SIGNUPS phase");
		}
	}

	public void toggleSpectator(int matchId) {
		MatchWithPlayers match = matchRepo.getMatchById(matchId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		if (!match.getMatchState().equals(SIGNUPS)) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Can only toggle spectator during SIGNUPS phase");
		}

		match.setSpectator(!match.getSpectator());
		matchRepo.update(match);
	}

	private Double calculateAveragePoints(Player player) {
		double totalPointsFromMatches = 0.0;
		double numScoredMatches = 0.0;

		for (MatchSignup matchSignup : matchRepo.getSignupsForPlayer(player)) {
			if (matchSignup.getFinalPointsAwarded() != null) {
				totalPointsFromMatches += matchSignup.getFinalPointsAwarded();
				numScoredMatches += 1.0;
			}
		}

		if (numScoredMatches > 0.0) {
			return totalPointsFromMatches / numScoredMatches;
		} else {
			return 0.0;
		}
	}
}
