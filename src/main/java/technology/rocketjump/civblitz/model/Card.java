package technology.rocketjump.civblitz.model;

import java.util.*;

public class Card {

	public static final List<String> BANNED_CARDS = Arrays.asList(
			"TRAIT_CIVILIZATION_BYZANTIUM",
			"TRAIT_CIVILIZATION_BABYLON",
			"TRAIT_CIVILIZATION_UNIT_AMERICAN_P51",
			"TRAIT_CIVILIZATION_UNIT_GERMAN_UBOAT"
	);

	protected String cardName;
	protected String cardDescription;
	protected String traitType; // Use this as the unique identifier
	protected String civilizationType;
	protected Optional<String> leaderType = Optional.empty();
	protected CardCategory cardCategory;

	protected String civilizationFriendlyName;

	protected Optional<String> grantsTraitType = Optional.empty();
	protected Optional<String> grantsFreeUseOfCard = Optional.empty();
	protected String subtype;
	protected String mediaName;
	protected String requiredDlc;
	protected List<String> modifierIds = new ArrayList<>();

	public Card() {

	}

	public Card(Card original) {
		this.cardName = original.cardName;
		this.cardDescription = original.cardDescription;
		this.traitType = original.traitType;
		this.civilizationType = original.civilizationType;
		this.leaderType = original.leaderType;
		this.cardCategory = original.cardCategory;
		this.civilizationFriendlyName = original.civilizationFriendlyName;
		this.grantsTraitType = original.grantsTraitType;
		this.grantsFreeUseOfCard = original.grantsFreeUseOfCard;
		this.subtype = original.subtype;
		this.mediaName = original.mediaName;
		this.requiredDlc = original.requiredDlc;
		this.modifierIds = original.modifierIds;
	}

	@Override
	public String toString() {
		return cardName + " " + traitType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;
		return traitType.equals(card.traitType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(traitType);
	}

	public String getCivilizationType() {
		return civilizationType;
	}

	public void setCivilizationType(String civilizationType) {
		this.civilizationType = civilizationType;
	}

	public Optional<String> getLeaderType() {
		return leaderType;
	}

	public void setLeaderType(Optional<String> leaderType) {
		this.leaderType = leaderType;
	}

	public String getTraitType() {
		return traitType;
	}

	public void setTraitType(String traitType) {
		this.traitType = traitType;
	}

	public CardCategory getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(CardCategory cardCategory) {
		this.cardCategory = cardCategory;
	}

	public String getCivilizationFriendlyName() {
		return civilizationFriendlyName;
	}

	public void setCivilizationFriendlyName(String civilizationFriendlyName) {
		this.civilizationFriendlyName = civilizationFriendlyName;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardDescription() {
		return cardDescription;
	}

	public void setCardDescription(String cardDescription) {
		this.cardDescription = cardDescription;
	}

	public Optional<String> getGrantsTraitType() {
		return grantsTraitType;
	}

	public void setGrantsTraitType(Optional<String> grantsTraitType) {
		this.grantsTraitType = grantsTraitType;
	}

	public Optional<String> getGrantsFreeUseOfCard() {
		return grantsFreeUseOfCard;
	}

	public void setGrantsFreeUseOfCard(Optional<String> grantsFreeUseOfCard) {
		this.grantsFreeUseOfCard = grantsFreeUseOfCard;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaName() {
		return mediaName;
	}

	public String getRequiredDlc() {
		return requiredDlc;
	}

	public void setRequiredDlc(String requiredDlc) {
		this.requiredDlc = requiredDlc;
	}

	public List<String> getModifierIds() {
		return modifierIds;
	}
}
