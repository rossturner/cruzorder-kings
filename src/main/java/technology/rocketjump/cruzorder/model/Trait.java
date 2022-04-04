package technology.rocketjump.cruzorder.model;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static technology.rocketjump.cruzorder.model.Skill.*;
import static technology.rocketjump.cruzorder.model.TraitType.Education;
import static technology.rocketjump.cruzorder.model.TraitType.Personality;

public enum Trait {

	// Personality traits
	Brave(Personality, "brave", List.of(
			new SkillModifier(Martial, 2), new SkillModifier(Prowess, 3)
	), List.of(
			"+5 Vassal Opinion", "+10 Attraction Opinion", "+10 Same Trait Opinion", "-10 Opposite Trait Opinion",
			"+100% Likelihood of capture or death in Battle", "Stress loss through Hunting"
	), 40),
	Craven(Personality, "craven", List.of(
			new SkillModifier(Intrigue, 2), new SkillModifier(Martial, -2), new SkillModifier(Prowess, -3)
	), List.of(
			"+10 Hostile Scheme Resistance", "+10% Scheme Secrecy", "-50% Likelihood of capture or death in Battle",
			"+20 Same Trait Opinion", "-5 Vassal Opinion", "-10 Attraction Opinion", "Stress gain through Torture"
	), -10),
	Calm(Personality, "calm", List.of(
			new SkillModifier(Diplomacy, 1), new SkillModifier(Intrigue, 1)
	), List.of(
			"+10% Stress Loss", "+10% Scheme Discovery Chance", "+50% Dread Decay", "+10 Same Trait Opinion", "-10 Opposite Trait Opinion"
	), 25),
	Wrathful(Personality, "wrathful", List.of(
			new SkillModifier(Martial, 3), new SkillModifier(Diplomacy, -1), new SkillModifier(Intrigue, -1)
	), List.of(
			"+20 Natural Dread", "Enables the Punish Criminal interaction"
	), 30),
	Chaste(Personality, "chaste", List.of(
			new SkillModifier(Learning, 2)
	), List.of(
			"-25% Fertility", "-50% Seduce Scheme Power", "+10 Same Trait Opinion", "-10 Opposite Trait Opinion"
	), 20),
	Lustful(Personality, "lustful", List.of(
			new SkillModifier(Intrigue, 2)
	), List.of(
			"+25% Fertility", "+10% Seduction Scheme Power", "+10 Same Trait Opinion", "-10 Opposite Trait Opinion", "-50% Asexuality Chance"
	), 25),
	Content(Personality, "content", List.of(
			new SkillModifier(Learning, 2), new SkillModifier(Intrigue, -1)
	), List.of(
			"+10% Stress Loss", "+10 Opinion of Vassals", "+20 Opinion of Liege", "+20 Same Trait Opinion", "Stress gain through execution of non-criminal prisoners, claim throne, or force onto council"
	), 20),
	Ambitious(Personality, "ambitious", List.of(
			new SkillModifier(Diplomacy, 1), new SkillModifier(Martial, 1), new SkillModifier(Stewardship, 1), new SkillModifier(Intrigue, 1), new SkillModifier(Learning, 1), new SkillModifier(Prowess, 1)
	), List.of(
			"+25% Stress Gain", "-15 Liege Opinion", "-15 Same Trait Opinion", "Stress gain through gifting titles (if under Domain Limit), granting Vassals independence, or signing white peace in offensive wars"
	), 40),


	Example(Personality, "example", List.of(), List.of(), 7777),

	// Education traits
	Naive_Appeaser(Education, "education_diplomacy_1", List.of(new SkillModifier(Diplomacy, 2)), List.of("+10% diplomacy lifestyle experience"), 0),
	Adequate_Bargainer(Education, "education_diplomacy_2", List.of(new SkillModifier(Diplomacy, 4)), List.of("+20% diplomacy lifestyle experience"), 20),
	Charismatic_Negotiator(Education, "education_diplomacy_2", List.of(new SkillModifier(Diplomacy, 6)), List.of("+30% diplomacy lifestyle experience"), 40),
	Grey_Eminence(Education, "education_diplomacy_4", List.of(new SkillModifier(Diplomacy, 8)), List.of("+40% diplomacy lifestyle experience"), 80),

	//	Congenital,
	// Physical,
	// 	Lifestyle,
	// 	Commander,
	// 	Infamous,
	// 	CopingMechanism,
	// 	Health,
	// 	Disease,
	// 	Dynasty,
	// 	Descendant,
	// 	Decision,
	// 	Event

	;
	public final TraitType type;
	public final String internalName;
	public final List<SkillModifier> skillModifiers;
	public final List<String> otherModifiers;
	public final int cost;
	public final Set<Trait> exclusiveWith = new LinkedHashSet<>();

	static {
		Brave.addExclusiveWith(Craven);
		Calm.addExclusiveWith(Wrathful);
		Chaste.addExclusiveWith(Lustful);
		Content.addExclusiveWith(Ambitious);
	}

	Trait(TraitType type, String internalName, List<SkillModifier> skillModifiers, List<String> otherModifiers, int cost) {
		this.type = type;
		this.internalName = internalName;
		this.skillModifiers = skillModifiers;
		this.otherModifiers = otherModifiers;
		this.cost = cost;
	}

	public void addExclusiveWith(Trait other) {
		this.exclusiveWith.add(other);
		other.exclusiveWith.add(this);
	}
}
