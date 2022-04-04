package technology.rocketjump.cruzorder.model;

import org.h2.command.dml.Call;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static technology.rocketjump.cruzorder.model.Skill.*;
import static technology.rocketjump.cruzorder.model.TraitType.*;

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
	Diligent(Personality, "diligent", List.of(
			new SkillModifier(Diplomacy, 2), new SkillModifier(Stewardship, 3), new SkillModifier(Learning, 3)
	), List.of(
			"-50% Stress Loss", "+10 Same Trait Opinion", "-10 Opposite Trait Opinion", "Enables the Develop Capital decision", "Stress loss through Hunting"
	), 40),
	Lazy(Personality, "lazy", List.of(
			new SkillModifier(Diplomacy, -1), new SkillModifier(Intrigue, -1), new SkillModifier(Stewardship, -1), new SkillModifier(Martial, -1), new SkillModifier(Learning, -1)
	), List.of(
			"+50% Stress Loss", "No Stress loss through Hunting"
	), -10),
	Fickle(Personality, "fickle", List.of(
			new SkillModifier(Diplomacy, 2), new SkillModifier(Intrigue, 1), new SkillModifier(Stewardship, -2)
	), List.of(
			"+20% Hostile Scheme Resistance"
	), 25),
	Stubborn(Personality, "stubborn", List.of(
			new SkillModifier(Stewardship, 3)
	), List.of(
			"-5 Liege Opinion", "-5 Vassal Opinion", "+0.25 Disease Resistance Health Boost"
	), 30),
	Forgiving(Personality, "forgiving", List.of(
			new SkillModifier(Diplomacy, 2), new SkillModifier(Learning, 1), new SkillModifier(Intrigue, -2)
	), List.of(
			"+15 Prisoner Opinion", "+10 Same Trait Opinion", "-10 Opposite Trait Opinion", "Enables the Abandon Hook interaction", "Stress gain through Blackmail, Murder, Torture, Imprisonment and Title Revocation"
	), 25),
	Vengeful(Personality, "vengeful", List.of(
			new SkillModifier(Intrigue, 2), new SkillModifier(Prowess, 2), new SkillModifier(Diplomacy, -2)
	), List.of(
			"+15 Hostile Scheme Success Chance against Rivals", "Enables the Fabricate Hook scheme against Rivals", "Stress loss through Execution and Murder of Rivals"
	), 30),
	Generous(Personality, "generous", List.of(
			new SkillModifier(Diplomacy, 3)
	), List.of(
			"-10% Monthly Income", "-15 Opposite Trait Opinion", "Stress gain from demanding money", "Stress loss from gifting money"
	), 20),
	Greedy(Personality, "greedy", List.of(
			new SkillModifier(Diplomacy, 2)
	), List.of(
			"+5% Monthly Income", "+10% Monthly Income per Stress level", "Stress loss through Extra Taxes", "Stress gain through Gifting",
					"Stress gain through Granting Vassal", "Stress gain through Granting Title, if under Domain Limit"
	), 30),
	Gregarious(Personality, "gregarious", List.of(
			new SkillModifier(Diplomacy, 2)
	), List.of(
			"+5 Attraction Opinion", "+15% Personal Scheme Power", "+10 Same Trait Opinion", "Stress loss through Feasting", "Stress gain from failure during Sway schemes"
	), 30),
	Shy(Personality, "shy", List.of(
			new SkillModifier(Learning, 1), new SkillModifier(Diplomacy, -2)
	), List.of(
			"+20% Hostile Scheme Resistance", "+10 Same Trait Opinion", "-5 Attraction Opinion", "-20% Personal Scheme Power", "Stress gain through inviting characters to court and plots"
	), -10),
	Honest(Personality, "honest", List.of(
			new SkillModifier(Diplomacy, 2), new SkillModifier(Intrigue, -4)
	), List.of(
			"+10 Same Trait Opinion", "-10 Opposite Trait Opinion", "Stress gain through Blackmail", "Stress loss from exposing secrets"
	), 20),
	Deceitful(Personality, "deceitful", List.of(
			new SkillModifier(Intrigue, 4), new SkillModifier(Diplomacy, -2)
	), List.of(
			"-10 Opposite Trait Opinion", "Enables the Study the Art of Scheming decision\n"
	), 30),
	Humble(Personality, "humble", List.of(), List.of(
			"+0.5 Monthly Piety", "+10 Opinion of Liege", "+10 Opinion of Vassals", "+10 Clergy Opinion", "-15 Opposite Trait Opinion"
	), 20),
	Arrogant(Personality, "arrogant", List.of(), List.of(
			"+1 Monthly Prestige", "-10 Scheme Secrecy", "-5 Opinion of Vassals", "-5 Opinion of Liege", "-15 Opposite Trait Opinion", "Stress gain through granting Vassals independence, legitimizing bastard or signing white peace in offensive wars"
	), 20),
	Just(Personality, "just", List.of(
			new SkillModifier(Stewardship, 2), new SkillModifier(Learning, 1), new SkillModifier(Intrigue, -3)
	), List.of(
			"+5 Vassal Opinion", "+10 Same Trait Opinion", "-10 Opposite Trait Opinion", "Stress gain through Blackmail and execution of prisoners", "Stress loss from exposing secrets"
	), 40),
	Arbitrary(Personality, "arbitrary", List.of(
			new SkillModifier(Intrigue, 3), new SkillModifier(Stewardship, -1), new SkillModifier(Learning, -1)
	), List.of(
			"+15 Natural Dread", "-50% Stress Gain", "-5 Vassal Opinion", "Enables the Dismiss Hook interaction"
	), 30),
	Patient(Personality, "patient", List.of(
			new SkillModifier(Learning, 2)
	), List.of(
			"+10 Hostile Scheme Resistance", "+5 Vassal Opinion", "+5 Liege Opinion", "-15 Opposite Trait Opinion"
	), 30),
	Impatient(Personality, "impatient", List.of(
			new SkillModifier(Learning, -2)
	), List.of(
			"+20% Monthly Prestige", "+15% Hostile Scheme Power", "-5 Vassal Opinion", "-5 Opinion of Liege", "-15 Opposite Trait Opinion", "Enables the Expedite Schemes decision"
	), 25),
	Temperate(Personality, "temperate", List.of(
			new SkillModifier(Stewardship, 2)
	), List.of(
			"+10 Same Trait Opinion", "-10 Opposite Trait Opinion", "+0.25 Health Boost"
	), 40),
	Gluttonous(Personality, "gluttonous", List.of(
			new SkillModifier(Stewardship, -2)
	), List.of(
			"+10% Stress Loss", "-5 Attraction Opinion", "-10 Opposite Trait Opition", "+10 Same Trait Opinion", "Less likely to die from poisoning during Murder schemes"
	), 20),
	Trusting(Personality, "trusting", List.of(
			new SkillModifier(Diplomacy, 2), new SkillModifier(Intrigue, -2)
	), List.of(
			"+15 Opinion of Liege", "+15 Opinion of Vassals", "+15 Enemy Hostile Scheme Success Chance", "Stress gain through Blackmail and execution of non-criminal prisoners"
	), 10),
	Paranoid(Personality, "paranoid", List.of(
			new SkillModifier(Intrigue, 3), new SkillModifier(Diplomacy, -1)
	), List.of(
			"+25% Dread Gain", "+10% Scheme Discovery Chance", "-25 Enemy Personal Scheme Success Chance", "+100% Stress gain", "-10 Opinion of Vassals", "Stress gain through inviting characters to court and plots"
	), -10),
	Zealous(Personality, "zealous", List.of(
			new SkillModifier(Martial, 2)
	), List.of(
			"+20% Monthly Piety", "+15 Same Trait Opinion", "-10 Opposite Trait Opinion", "-35 Different Faith Opinion", "+20% Faith Conversion Cost", "Stress loss through Execution (different faith)"
	), 30),
	Cynical(Personality, "cynical", List.of(
			new SkillModifier(Learning, 2), new SkillModifier(Intrigue, 2)
	), List.of(
			"-20% Faith Conversion Cost", "+10 Same Trait Opinion", "-10 Opposite Trait Opinion", "-20% Monthly Piety"
	), 30),
	Compassionate(Personality, "compassionate", List.of(
			new SkillModifier(Diplomacy, 2), new SkillModifier(Intrigue, -2)
	), List.of(
			"+5 Attraction Opinion", "-15 Opposite Trait Opinion", "-15 Natural Dread", "+100% Dread Decay", "Stress gain through kick from court, disinherit, denounce, break up with lover, dismiss concubine, fabricate hook, blackmail, imprison, move to dungeon, execution, start murder, start abduct, torture and title revocation"
	), 10),
	Callous(Personality, "callous", List.of(
			new SkillModifier(Intrigue, 2), new SkillModifier(Diplomacy, 2)
	), List.of(
			"-20% Tyranny Gain", "+25% Dread Gain", "-25% Dread Decay", "-5 Attraction Opinion", "-5 Close Family Opinion", "Stress gain from releasing prisoner (w/out demands)"
	), 40),
	Sadistic(Personality, "sadistic", List.of(
			new SkillModifier(Intrigue, 2), new SkillModifier(Prowess, 4)
	), List.of(
			"+35 Natural Dread", "-10 General Opinion", "Can use Hostile Schemes against own children", "Stress loss through Execution and Murder"
	), 40),

	// Education traits
	Naive_Appeaser(Education, "education_diplomacy_1", List.of(new SkillModifier(Diplomacy, 2)), List.of("+10% diplomacy lifestyle experience"), 0),
	Adequate_Bargainer(Education, "education_diplomacy_2", List.of(new SkillModifier(Diplomacy, 4)), List.of("+20% diplomacy lifestyle experience"), 20),
	Charismatic_Negotiator(Education, "education_diplomacy_2", List.of(new SkillModifier(Diplomacy, 6)), List.of("+30% diplomacy lifestyle experience"), 40),
	Grey_Eminence(Education, "education_diplomacy_4", List.of(new SkillModifier(Diplomacy, 8)), List.of("+40% diplomacy lifestyle experience"), 80),

	Misguided_Warrior(Education, "education_martial_1", List.of(new SkillModifier(Martial, 2)), List.of("+10% martial lifestyle experience"), 0),
	Tough_Soldier(Education, "education_martial_2", List.of(new SkillModifier(Martial, 4)), List.of("+20% martial lifestyle experience"), 20),
	Skilled_Tactician(Education, "education_martial_3", List.of(new SkillModifier(Martial, 6)), List.of("+30% martial lifestyle experience"), 40),
	Brilliant_Strategist(Education, "education_martial_4", List.of(new SkillModifier(Martial, 8)), List.of("+40% martial lifestyle experience"), 80),

	Indulgent_Wastrel(Education, "education_stewardship_1", List.of(new SkillModifier(Stewardship, 2)), List.of("+10% stewardship lifestyle experience"), 0),
	Thrifty_Clerk(Education, "education_stewardship_2", List.of(new SkillModifier(Stewardship, 4)), List.of("+20% stewardship lifestyle experience"), 20),
	Fortune_Builder(Education, "education_stewardship_3", List.of(new SkillModifier(Stewardship, 6)), List.of("+30% stewardship lifestyle experience"), 40),
	Midas_Touched(Education, "education_stewardship_4", List.of(new SkillModifier(Stewardship, 8)), List.of("+40% stewardship lifestyle experience"), 80),

	Amateurish_Plotter(Education, "education_intrigue_1", List.of(new SkillModifier(Intrigue, 2)), List.of("+10% intrigue lifestyle experience"), 0),
	Flamboyant_Trickster(Education, "education_intrigue_2", List.of(new SkillModifier(Intrigue, 4)), List.of("+20% intrigue lifestyle experience"), 20),
	Intricate_Webweaver(Education, "education_intrigue_3", List.of(new SkillModifier(Intrigue, 6)), List.of("+30% intrigue lifestyle experience"), 40),
	Elusive_Shadow(Education, "education_intrigue_4", List.of(new SkillModifier(Intrigue, 8)), List.of("+40% intrigue lifestyle experience"), 80),

	Conscientious_Scribe(Education, "education_learning_1", List.of(new SkillModifier(Learning, 2)), List.of("+10% learning lifestyle experience"), 0),
	Insightful_Thinker(Education, "education_learning_2", List.of(new SkillModifier(Learning, 4)), List.of("+20% learning lifestyle experience"), 20),
	Astute_Intellectual(Education, "education_learning_3", List.of(new SkillModifier(Learning, 6)), List.of("+30% learning lifestyle experience"), 40),
	Mastermind_Philosopher(Education, "education_learning_4", List.of(new SkillModifier(Learning, 8)), List.of("+40% learning lifestyle experience"), 80),

	//	Congenital,
	Melancholic(Congenital, "depressed", List.of(
			new SkillModifier(Diplomacy, -1), new SkillModifier(Martial, -1), new SkillModifier(Stewardship, -1), new SkillModifier(Intrigue, -1)
	), List.of(
			"−10% Fertility", "−0.5 Health", "Unique \"this is you\" description"
	), -20),
	Lunatic(Congenital, "lunatic", List.of(), List.of(
			"+10% Hostile Scheme Resistance", "+10 Same trait opinion", "−10 Attraction opinion", "−10 Vassal opinion", "−0.25 Health"
	), -15),
	Possessed(Congenital, "possessed", List.of(), List.of(
			"+10% Monthly Learning lifestyle Experience", "+15 Same trait opinion", "−10 Attraction opinion", "−0.5 Health"
	), -20),
	Fecund(Congenital, "fecund", List.of(), List.of(
			"+50% Fertility", "+5 Years Life Expectancy"
	), 50),
	Albino(Congenital, "albino", List.of(), List.of(
			"+15 Natural Dread", "+10 Same trait opinion", "−10 General opinion"
	), 0),
	Lisping(Congenital, "lisping", List.of(
			new SkillModifier(Diplomacy, -2)
	), List.of(
			"+10 Same trait opinion", "−5 Attraction opinion"
	), -5),
	Stuttering(Congenital, "stuttering", List.of(new SkillModifier(Diplomacy, -2)), List.of("+10 Same trait opinion"), -5),


	Example(Congenital, "example", List.of(), List.of(), 7777),
	// Physical,
	// 	Lifestyle,
	// 	Commander,
	// 	Infamous,
	// 	CopingMechanism,
	// 	Health, SKIP
	// 	Disease, SKIP
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
		Diligent.addExclusiveWith(Lazy);
		Fickle.addExclusiveWith(Stubborn);
		Forgiving.addExclusiveWith(Vengeful);
		Generous.addExclusiveWith(Greedy);
		Gregarious.addExclusiveWith(Shy);
		Honest.addExclusiveWith(Deceitful);
		Humble.addExclusiveWith(Arrogant);
		Just.addExclusiveWith(Arbitrary);
		Patient.addExclusiveWith(Impatient);
		Temperate.addExclusiveWith(Gluttonous);
		Trusting.addExclusiveWith(Paranoid);
		Zealous.addExclusiveWith(Cynical);
		Compassionate.addExclusiveWith(Callous);
		Compassionate.addExclusiveWith(Sadistic);
		Callous.addExclusiveWith(Sadistic);
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
