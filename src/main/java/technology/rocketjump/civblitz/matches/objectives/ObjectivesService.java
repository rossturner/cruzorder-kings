package technology.rocketjump.civblitz.matches.objectives;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import technology.rocketjump.civblitz.codegen.tables.pojos.Match;
import technology.rocketjump.civblitz.codegen.tables.pojos.Player;
import technology.rocketjump.civblitz.codegen.tables.pojos.SecretObjective;
import technology.rocketjump.civblitz.mapgen.StartEra;
import technology.rocketjump.civblitz.matches.guilds.GuildDefinition;
import technology.rocketjump.civblitz.matches.guilds.GuildDefinitionRepo;
import technology.rocketjump.civblitz.matches.guilds.GuildRepo;
import technology.rocketjump.civblitz.model.MatchSignupWithPlayer;
import technology.rocketjump.civblitz.model.MatchWithPlayers;

import java.util.*;
import java.util.stream.Collectors;

import static technology.rocketjump.civblitz.matches.MatchState.IN_PROGRESS;
import static technology.rocketjump.civblitz.matches.objectives.ObjectiveDefinition.ObjectiveType.PUBLIC;
import static technology.rocketjump.civblitz.matches.objectives.ObjectiveDefinition.ObjectiveType.SECRET;

@Service
public class ObjectivesService {

	private static final long MINIMUM_MILITARY_PUBLIC_OBJECTIVES = 2;
	private static final int NUM_GUILDS_PER_MATCH = 3;
	private final AllObjectivesService allObjectivesService;
	private final ObjectivesRepo objectivesRepo;
	private final ObjectiveDefinitionRepo objectiveDefinitionRepo;
	private final GuildDefinitionRepo guildDefinitionRepo;
	private final GuildRepo guildRepo;
	private final Random random = new Random();

	@Autowired
	public ObjectivesService(AllObjectivesService allObjectivesService, ObjectivesRepo objectivesRepo,
							 ObjectiveDefinitionRepo objectiveDefinitionRepo, GuildDefinitionRepo guildDefinitionRepo, GuildRepo guildRepo) {
		this.allObjectivesService = allObjectivesService;
		this.objectivesRepo = objectivesRepo;
		this.objectiveDefinitionRepo = objectiveDefinitionRepo;
		this.guildDefinitionRepo = guildDefinitionRepo;
		this.guildRepo = guildRepo;
	}

	public void initialiseObjectives(MatchWithPlayers match) {
		clearObjectives(match);

		List<ObjectiveDefinition> activeByEra = objectiveDefinitionRepo.getActiveByEra(match.getStartEra());

		List<ObjectiveDefinition> allPublicObjectives = activeByEra.stream()
				.filter(o -> o.objectiveType.equals(PUBLIC))
				.collect(Collectors.toList());

		List<ObjectiveDefinition> selectedPublicObjectives = pickPublicObjectives(allPublicObjectives, match.getStartEra());

		objectivesRepo.addPublicObjectives(match, selectedPublicObjectives);

		List<ObjectiveDefinition> allSecretObjectives = activeByEra.stream()
				.filter(o -> o.objectiveType.equals(SECRET))
				.collect(Collectors.toList());

		for (MatchSignupWithPlayer signup : match.signups) {
			Set<ObjectiveDefinition> objectivesForPlayer = new HashSet<>();
			pickObjective(1, objectivesForPlayer, allSecretObjectives, match.getStartEra());
			pickObjective(2, objectivesForPlayer, allSecretObjectives, match.getStartEra());
			pickObjective(3, objectivesForPlayer, allSecretObjectives, match.getStartEra());
			pickObjective(1, objectivesForPlayer, allSecretObjectives, match.getStartEra());
			pickObjective(2, objectivesForPlayer, allSecretObjectives, match.getStartEra());
			pickObjective(3, objectivesForPlayer, allSecretObjectives, match.getStartEra());

			for (ObjectiveDefinition objective : objectivesForPlayer) {
				objectivesRepo.add(match, signup, objective);
			}
		}

		Set<GuildDefinition> selectedGuilds = new HashSet<>();
		List<GuildDefinition> allGuilds = guildDefinitionRepo.getAll();

		while (selectedGuilds.size() < NUM_GUILDS_PER_MATCH) {
			GuildDefinition randomGuild = allGuilds.get(random.nextInt(allGuilds.size()));
			boolean alreadySelectedThisCategory = selectedGuilds.stream().anyMatch(g -> g.category.equals(randomGuild.category));
			if (!alreadySelectedThisCategory) {
				selectedGuilds.add(randomGuild);
			}
		}
		for (GuildDefinition selectedGuild : selectedGuilds) {
			guildRepo.add(match, selectedGuild);
		}

	}

	public void clearObjectives(Match match) {
		objectivesRepo.clear(match);
		guildRepo.clear(match);
	}

	public List<SecretObjective> getSecretObjectives(int matchId, Player player) {
		return objectivesRepo.getSecretObjectives(matchId, player);
	}

	public void update(SecretObjective secretObjective) {
		objectivesRepo.update(secretObjective);
	}

	private List<ObjectiveDefinition> pickPublicObjectives(List<ObjectiveDefinition> allPublicObjectives, StartEra startEra) {
		Set<ObjectiveDefinition> selectedObjectives = new HashSet<>();

		pickObjective(1, selectedObjectives, allPublicObjectives, startEra);
		pickObjective(1, selectedObjectives, allPublicObjectives, startEra);
		pickObjective(1, selectedObjectives, allPublicObjectives, startEra);
		pickObjective(2, selectedObjectives, allPublicObjectives, startEra);
		pickObjective(2, selectedObjectives, allPublicObjectives, startEra);
		pickObjective(3, selectedObjectives, allPublicObjectives, startEra);
		pickObjective(3, selectedObjectives, allPublicObjectives, startEra);

		long numMilitaryObjectives = selectedObjectives.stream().filter(o -> o.military).count();
		if (numMilitaryObjectives < MINIMUM_MILITARY_PUBLIC_OBJECTIVES) {
			// just select again until we get it right
			return pickPublicObjectives(allPublicObjectives, startEra);
		} else {
			return new ArrayList<>(selectedObjectives);
		}
	}

	private void pickObjective(Integer requiredNumStars, Set<ObjectiveDefinition> selectedObjectives, List<ObjectiveDefinition> objectivesToPickFrom, StartEra startEra) {
		ObjectiveDefinition selectedObjective = null;
		while (selectedObjective == null) {
			selectedObjective = objectivesToPickFrom.get(random.nextInt(objectivesToPickFrom.size()));
			if (selectedObjectives.contains(selectedObjective)) {
				selectedObjective = null;
			} else if (requiredNumStars != null && requiredNumStars != selectedObjective.getStars(startEra)) {
				selectedObjective = null;
			}
		}
		selectedObjectives.add(selectedObjective);
	}

	public void claimObjective(Player player, ObjectiveDefinition objective, MatchWithPlayers match) {
		if (!match.signups.stream().anyMatch(s -> s.getPlayer().equals(player))) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player is not part of the specified match");
		}

		if (!match.getMatchState().equals(IN_PROGRESS)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only claim objectives while match is in progress");
		}

		if (objective.objectiveType.equals(SECRET)) {
			List<SecretObjective> secretObjectives = getSecretObjectives(match.getMatchId(), player);
			SecretObjective secretObjective = secretObjectives.stream().filter(s -> s.getObjective().equals(objective.objectiveId) && s.getSelected()).findFirst()
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not have that secret objective"));

			if (secretObjective.getClaimed()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have already claimed this objective");
			}

			secretObjective.setClaimed(true);
			objectivesRepo.update(secretObjective);
		} else {
			List<PublicObjectiveWithClaimants> publicObjectives = allObjectivesService.getPublicObjectives(match.getMatchId());
			PublicObjectiveWithClaimants publicObjective = publicObjectives.stream().filter(p -> p.getObjective().equals(objective.objectiveId)).findAny()
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "That objective is not part of this match"));

			List<String> claimedByPlayerIds = publicObjective.getClaimedByPlayerIds();
			if (claimedByPlayerIds.contains(player.getPlayerId())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have already claimed this objective");
			}

			if (claimedByPlayerIds.size() >= maxClaimsFor(objective, match.getStartEra(), match.signups.size())) {
				throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Other players have already claimed this objective the maximum number of times");
			}

			objectivesRepo.createClaim(objective, player, match);
		}
	}

	public void unclaimObjective(Player player, ObjectiveDefinition objective, MatchWithPlayers match) {
		if (!match.getMatchState().equals(IN_PROGRESS)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only unclaim objectives while match is in progress");
		}

		if (objective.objectiveType.equals(SECRET)) {
			List<SecretObjective> secretObjectives = getSecretObjectives(match.getMatchId(), player);
			SecretObjective secretObjective = secretObjectives.stream().filter(s -> s.getObjective().equals(objective.objectiveId) && s.getSelected()).findFirst()
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not have that secret objective"));
			secretObjective.setClaimed(false);
			objectivesRepo.update(secretObjective);
		} else {
			// Just try the delete, if it doesn't exist it doesn't matter
			objectivesRepo.deleteClaim(objective, player, match);
		}
	}

	public static int maxClaimsFor(ObjectiveDefinition objective, StartEra startEra, int numPlayers) {
		if (objective.getStars(startEra) == 1) {
			if (numPlayers <= 2) {
				return 1;
			} else if (numPlayers <= 4) {
				return 2;
			} else if (numPlayers <= 7) {
				return 3;
			} else if (numPlayers <= 9) {
				return 4;
			} else {
				return 5;
			}
		} else if (objective.getStars(startEra) == 2) {
			if (numPlayers <= 3) {
				return 1;
			} else if (numPlayers <= 7) {
				return 2;
			} else if (numPlayers <= 9) {
				return 3;
			} else {
				return 4;
			}
		} else {
			if (numPlayers <= 9) {
				return 1;
			} else {
				return 2;
			}
		}
	}
}
