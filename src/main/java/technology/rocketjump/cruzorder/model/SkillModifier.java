package technology.rocketjump.cruzorder.model;

public class SkillModifier {

	public final Skill skill;
	public final int modifierAmount;

	public SkillModifier(Skill skill, int modifierAmount) {
		this.skill = skill;
		this.modifierAmount = modifierAmount;
	}
}
