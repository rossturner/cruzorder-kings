package technology.rocketjump.civblitz.cards;

import technology.rocketjump.civblitz.codegen.tables.pojos.CardPack;
import technology.rocketjump.civblitz.model.Card;

import java.util.List;

public class PackDetails {

	private final CardPackType packType;
	private final List<Card> cards;

	public PackDetails(CardPack pack, List<Card> cards) {
		this.packType = pack.getPackType();
		this.cards = cards;
	}

	public CardPackType getPackType() {
		return packType;
	}

	public List<Card> getCards() {
		return cards;
	}
}
