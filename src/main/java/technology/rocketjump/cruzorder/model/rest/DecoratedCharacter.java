package technology.rocketjump.cruzorder.model.rest;

import technology.rocketjump.cruzorder.codegen.tables.pojos.Character;
import technology.rocketjump.cruzorder.codegen.tables.pojos.Player;
import technology.rocketjump.cruzorder.codegen.tables.pojos.TerritorySelection;

public class DecoratedCharacter {

	private Character character;
	private TerritorySelection territory;
	private Player player;

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public TerritorySelection getTerritory() {
		return territory;
	}

	public void setTerritory(TerritorySelection territory) {
		this.territory = territory;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
