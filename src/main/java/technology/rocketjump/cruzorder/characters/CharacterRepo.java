package technology.rocketjump.cruzorder.characters;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Character;
import technology.rocketjump.cruzorder.codegen.tables.pojos.CharacterChild;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Player;
import technology.rocketjump.cruzorder.codegen.tables.records.CharacterTraitsRecord;
import technology.rocketjump.cruzorder.model.rest.CharacterRequest;
import technology.rocketjump.cruzorder.model.rest.ChildRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static technology.rocketjump.cruzorder.codegen.tables.Character.CHARACTER;
import static technology.rocketjump.cruzorder.codegen.tables.CharacterChild.CHARACTER_CHILD;
import static technology.rocketjump.cruzorder.codegen.tables.CharacterTraits.CHARACTER_TRAITS;

@Component
public class CharacterRepo {

	private final DSLContext create;
	private static final Integer FIRST_BASE_ID = 7770100;

	@Autowired
	public CharacterRepo(DSLContext create) {
		this.create = create;
	}

	public Optional<Character> getCharacterByDynastyName(String dynastyName) {
		return create.selectFrom(CHARACTER).where(CHARACTER.DYNASTY_NAME.equalIgnoreCase(dynastyName)).fetchOptionalInto(Character.class);
	}

	public Optional<Character> getCharacterByDynastyId(int dynastyId) {
		return create.selectFrom(CHARACTER).where(CHARACTER.BASE_ID.eq(dynastyId)).fetchOptionalInto(Character.class);
	}

	public Integer nextDynastyIdentifier() {
		Optional<Record1<Integer>> optional = create.select(CHARACTER.BASE_ID).from(CHARACTER).orderBy(CHARACTER.BASE_ID.desc()).limit(1).fetchOptional();
		return optional.map(r -> r.component1() + 100).orElse(FIRST_BASE_ID);
	}

	public void createCharacter(int dynastyId, Player player, CharacterRequest characterRequest) {
		Character character = new Character();
		character.setPlayerId(player.getPlayerId());
		character.setBaseId(dynastyId);
		character.setDynastyPrefix(characterRequest.getDynastyPrefix().trim().toLowerCase());
		character.setDynastyName(characterRequest.getDynastyName().trim());
		character.setDynastyMotto(characterRequest.getDynastyMotto().trim());
		character.setDynastyCoa(characterRequest.getDynastyCoa().trim());
		character.setCopyCoaToTitle(characterRequest.getCopyCoaToTitle());
		character.setPrimaryCharacterName(characterRequest.getPrimaryCharacterName().trim());
		character.setIsFemale(characterRequest.getIsFemale());
		character.setSexualOrientation(characterRequest.getSexualOrientation());
		character.setCultureGroup(characterRequest.getCultureGroup());
		character.setCulture(characterRequest.getCulture());
		character.setReligion(characterRequest.getReligion());
		character.setPrimaryDna(characterRequest.getPrimaryCharacterDna().trim());
		character.setPrimaryAge(characterRequest.getPrimaryCharacterAge());
		character.setDiplomacy(characterRequest.getBaseDiplomacy());
		character.setIntrigue(characterRequest.getBaseIntrigue());
		character.setMartial(characterRequest.getBaseMartial());
		character.setLearning(characterRequest.getBaseLearning());
		character.setStewardship(characterRequest.getBaseStewardship());
		character.setProwess(characterRequest.getBaseProwess());
		character.setSpouse(characterRequest.isHasSpouse());
		character.setSpouseName(characterRequest.getSpouseName());
		character.setChildrenAge(characterRequest.getChildrenAge());
		create.newRecord(CHARACTER, character).store();
	}

	public void updateCharacter(int dynastyId, Player player, CharacterRequest characterRequest) {
		create.update(CHARACTER)
				.set(CHARACTER.DYNASTY_PREFIX, characterRequest.getDynastyPrefix().trim().toLowerCase())
				.set(CHARACTER.DYNASTY_NAME, characterRequest.getDynastyName().trim())
				.set(CHARACTER.DYNASTY_MOTTO, characterRequest.getDynastyMotto().trim())
				.set(CHARACTER.DYNASTY_COA, characterRequest.getDynastyCoa().trim())
				.set(CHARACTER.COPY_COA_TO_TITLE, characterRequest.getCopyCoaToTitle())
				.set(CHARACTER.PRIMARY_CHARACTER_NAME, characterRequest.getPrimaryCharacterName().trim())
				.set(CHARACTER.IS_FEMALE, characterRequest.getIsFemale())
				.set(CHARACTER.SEXUAL_ORIENTATION, characterRequest.getSexualOrientation())
				.set(CHARACTER.CULTURE_GROUP, characterRequest.getCultureGroup())
				.set(CHARACTER.CULTURE, characterRequest.getCulture())
				.set(CHARACTER.RELIGION, characterRequest.getReligion())
				.set(CHARACTER.PRIMARY_DNA, characterRequest.getPrimaryCharacterDna().trim())
				.set(CHARACTER.PRIMARY_AGE, characterRequest.getPrimaryCharacterAge())
				.set(CHARACTER.DIPLOMACY, characterRequest.getBaseDiplomacy())
				.set(CHARACTER.INTRIGUE, characterRequest.getBaseIntrigue())
				.set(CHARACTER.MARTIAL, characterRequest.getBaseMartial())
				.set(CHARACTER.LEARNING, characterRequest.getBaseLearning())
				.set(CHARACTER.STEWARDSHIP, characterRequest.getBaseStewardship())
				.set(CHARACTER.PROWESS, characterRequest.getBaseProwess())
				.set(CHARACTER.SPOUSE, characterRequest.isHasSpouse())
				.set(CHARACTER.SPOUSE_NAME, characterRequest.getSpouseName())
				.set(CHARACTER.CHILDREN_AGE, characterRequest.getChildrenAge())
				.where(CHARACTER.BASE_ID.eq(dynastyId).and(CHARACTER.PLAYER_ID.eq(player.getPlayerId())))
				.execute();
	}

	public void addTrait(int dynastyId, String trait) {
		create.insertInto(CHARACTER_TRAITS, CHARACTER_TRAITS.BASE_ID, CHARACTER_TRAITS.TRAIT)
				.values(dynastyId, trait)
				.execute();
	}

	public void deleteTraits(int dynastyId) {
		create.deleteFrom(CHARACTER_TRAITS)
				.where(CHARACTER_TRAITS.BASE_ID.eq(dynastyId))
				.execute();
	}

	public List<String> getTraits(int dynastyId) {
		return create.selectFrom(CHARACTER_TRAITS)
				.where(CHARACTER_TRAITS.BASE_ID.eq(dynastyId))
				.fetchStream().map(CharacterTraitsRecord::getTrait).collect(Collectors.toList());
	}

	public void createChild(int dynastyId, ChildRequest childRequest, int index) {
		create.insertInto(CHARACTER_CHILD, CHARACTER_CHILD.BASE_ID, CHARACTER_CHILD.CHILD_INDEX,
							CHARACTER_CHILD.NAME, CHARACTER_CHILD.IS_FEMALE)
				.values(dynastyId, index, childRequest.getName().trim(), childRequest.getIsFemale())
				.execute();
	}

	public void deleteChildren(int dynastyId) {
		create.deleteFrom(CHARACTER_CHILD)
				.where(CHARACTER_CHILD.BASE_ID.eq(dynastyId))
				.execute();
	}

	public List<Character> getAllCharacters() {
		return create.selectFrom(CHARACTER).orderBy(CHARACTER.BASE_ID).fetchInto(Character.class);
	}

	public List<Character> getAllCharactersForPlayer(String playerId) {
		return create.selectFrom(CHARACTER).where(CHARACTER.PLAYER_ID.eq(playerId)).orderBy(CHARACTER.BASE_ID).fetchInto(Character.class);
	}

	public List<CharacterChild> getChildren(int dynastyId) {
		return create.selectFrom(CHARACTER_CHILD)
				.where(CHARACTER_CHILD.BASE_ID.eq(dynastyId))
				.orderBy(CHARACTER_CHILD.CHILD_INDEX.asc())
				.fetchInto(CharacterChild.class);
	}
}
