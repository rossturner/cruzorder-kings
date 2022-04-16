package technology.rocketjump.cruzorder.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterRequest {

	private String dynastyPrefix;
	private String dynastyName;
	private String dynastyMotto;
	private String dynastyCoa;
	private boolean copyCoaToTitle;

	private String primaryCharacterName;
	private boolean isFemale;
	private SexualOrientation sexualOrientation;
	private String cultureGroup;
	private String culture;
	private String primaryCharacterDna;

	private int primaryCharacterAge;
	private List<String> traits;

	private int baseDiplomacy;
	private int baseIntrigue;
	private int baseMartial;
	private int baseLearning;
	private int baseStewardship;
	private int baseProwess;

	private boolean hasSpouse;
	private ChildAgeSetting childrenAge;
	private List<ChildRequest> children;

	public String getDynastyPrefix() {
		return dynastyPrefix;
	}

	public void setDynastyPrefix(String dynastyPrefix) {
		this.dynastyPrefix = dynastyPrefix;
	}

	public String getDynastyName() {
		return dynastyName;
	}

	public void setDynastyName(String dynastyName) {
		this.dynastyName = dynastyName;
	}

	public String getDynastyMotto() {
		return dynastyMotto;
	}

	public void setDynastyMotto(String dynastyMotto) {
		this.dynastyMotto = dynastyMotto;
	}

	public String getDynastyCoa() {
		return dynastyCoa;
	}

	public void setDynastyCoa(String dynastyCoa) {
		this.dynastyCoa = dynastyCoa;
	}

	public boolean isCopyCoaToTitle() {
		return copyCoaToTitle;
	}

	public void setCopyCoaToTitle(boolean copyCoaToTitle) {
		this.copyCoaToTitle = copyCoaToTitle;
	}

	public String getPrimaryCharacterName() {
		return primaryCharacterName;
	}

	public void setPrimaryCharacterName(String primaryCharacterName) {
		this.primaryCharacterName = primaryCharacterName;
	}

	public boolean isFemale() {
		return isFemale;
	}

	public void setFemale(boolean female) {
		isFemale = female;
	}

	public SexualOrientation getSexualOrientation() {
		return sexualOrientation;
	}

	public void setSexualOrientation(SexualOrientation sexualOrientation) {
		this.sexualOrientation = sexualOrientation;
	}

	public String getCultureGroup() {
		return cultureGroup;
	}

	public void setCultureGroup(String cultureGroup) {
		this.cultureGroup = cultureGroup;
	}

	public String getCulture() {
		return culture;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getPrimaryCharacterDna() {
		return primaryCharacterDna;
	}

	public void setPrimaryCharacterDna(String primaryCharacterDna) {
		this.primaryCharacterDna = primaryCharacterDna;
	}

	public int getPrimaryCharacterAge() {
		return primaryCharacterAge;
	}

	public void setPrimaryCharacterAge(int primaryCharacterAge) {
		this.primaryCharacterAge = primaryCharacterAge;
	}

	public List<String> getTraits() {
		return traits;
	}

	public void setTraits(List<String> traits) {
		this.traits = traits;
	}

	public int getBaseDiplomacy() {
		return baseDiplomacy;
	}

	public void setBaseDiplomacy(int baseDiplomacy) {
		this.baseDiplomacy = baseDiplomacy;
	}

	public int getBaseIntrigue() {
		return baseIntrigue;
	}

	public void setBaseIntrigue(int baseIntrigue) {
		this.baseIntrigue = baseIntrigue;
	}

	public int getBaseMartial() {
		return baseMartial;
	}

	public void setBaseMartial(int baseMartial) {
		this.baseMartial = baseMartial;
	}

	public int getBaseLearning() {
		return baseLearning;
	}

	public void setBaseLearning(int baseLearning) {
		this.baseLearning = baseLearning;
	}

	public int getBaseStewardship() {
		return baseStewardship;
	}

	public void setBaseStewardship(int baseStewardship) {
		this.baseStewardship = baseStewardship;
	}

	public int getBaseProwess() {
		return baseProwess;
	}

	public void setBaseProwess(int baseProwess) {
		this.baseProwess = baseProwess;
	}

	public boolean isHasSpouse() {
		return hasSpouse;
	}

	public void setHasSpouse(boolean hasSpouse) {
		this.hasSpouse = hasSpouse;
	}

	public ChildAgeSetting getChildrenAge() {
		return childrenAge;
	}

	public void setChildrenAge(ChildAgeSetting childrenAge) {
		this.childrenAge = childrenAge;
	}

	public List<ChildRequest> getChildren() {
		return children;
	}

	public void setChildren(List<ChildRequest> children) {
		this.children = children;
	}
}
