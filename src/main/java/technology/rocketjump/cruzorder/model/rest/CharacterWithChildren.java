package technology.rocketjump.cruzorder.model.rest;

import technology.rocketjump.cruzorder.codegen.tables.pojos.Character;
import technology.rocketjump.cruzorder.codegen.tables.pojos.CharacterChild;

import java.util.ArrayList;
import java.util.List;

public class CharacterWithChildren {

	private Character character;
	private List<CharacterChild> children = new ArrayList<>();
	private List<String> traits = new ArrayList<>();
	private Integer territoryId;

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public List<CharacterChild> getChildren() {
		return children;
	}

	public void setChildren(List<CharacterChild> children) {
		this.children = children;
	}

	public Integer getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(Integer territoryId) {
		this.territoryId = territoryId;
	}

	public List<String> getTraits() {
		return traits;
	}

	public void setTraits(List<String> traits) {
		this.traits = traits;
	}
}
