package technology.rocketjump.cruzorder.characters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Character;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Player;
import technology.rocketjump.cruzorder.model.rest.CharacterRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

	private final CharacterRepo characterRepo;

	@Autowired
	public CharacterService(CharacterRepo characterRepo) {
		this.characterRepo = characterRepo;
	}

	public Optional<Character> getByDynastyName(String dynastyName) {
		return characterRepo.getCharacterByDynastyName(dynastyName);
	}

	public void createNew(Player player, CharacterRequest characterRequest) {
		BigDecimal dynastyId = characterRepo.nextDynastyIdentifier();

		characterRepo.createCharacter(dynastyId, player, characterRequest);

		for (String trait : characterRequest.getTraits()) {
			characterRepo.addTrait(dynastyId, trait);
		}

		for (int index = 0; index < characterRequest.getChildren().size(); index++) {
			characterRepo.createChild(dynastyId, characterRequest.getChildren().get(index), index);
		}
	}

	public void updateExisting(BigDecimal dynastyId, Player player, CharacterRequest characterRequest) {
		Optional<Character> existing = characterRepo.getCharacterByDynastyId(dynastyId);
		if (existing.isEmpty() || !existing.get().getPlayerId().equals(player.getPlayerId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		characterRepo.updateCharacter(dynastyId, player, characterRequest);

		characterRepo.deleteTraits(dynastyId);
		for (String trait : characterRequest.getTraits()) {
			characterRepo.addTrait(dynastyId, trait);
		}

		characterRepo.deleteChildren(dynastyId);
		for (int index = 0; index < characterRequest.getChildren().size(); index++) {
			characterRepo.createChild(dynastyId, characterRequest.getChildren().get(index), index);
		}
	}

	public List<Character> getAllCharacters() {
		return characterRepo.getAllCharacters();
	}

	public List<Character> getAllCharactersForPlayer(Player player) {
		return characterRepo.getAllCharactersForPlayer(player.getPlayerId());
	}
}
