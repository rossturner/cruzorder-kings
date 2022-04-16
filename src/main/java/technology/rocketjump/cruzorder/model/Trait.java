package technology.rocketjump.cruzorder.model;

import java.util.*;
import java.util.stream.Collectors;

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
	Charismatic_Negotiator(Education, "education_diplomacy_3", List.of(new SkillModifier(Diplomacy, 6)), List.of("+30% diplomacy lifestyle experience"), 40),
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
	Melancholic(Congenital, "depressed_genetic", List.of(
			new SkillModifier(Diplomacy, -1), new SkillModifier(Martial, -1), new SkillModifier(Stewardship, -1), new SkillModifier(Intrigue, -1)
	), List.of(
			"−10% Fertility", "−0.5 Health", "Unique \"this is you\" description"
	), -20),
	Lunatic(Congenital, "lunatic_genetic", List.of(), List.of(
			"+10% Hostile Scheme Resistance", "+10 Same trait opinion", "−10 Attraction opinion", "−10 Vassal opinion", "−0.25 Health"
	), -15),
	Possessed(Congenital, "possessed_genetic", List.of(), List.of(
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
	Pure_Blooded(Congenital, "pure_blooded", List.of(), List.of(
			"+10% Fertility", "−50% Inbreeding chance", "+0.25 Health"
	), 50),
	Giant(Congenital, "giant", List.of(
			new SkillModifier(Prowess, 6)
	), List.of(
			"+5 Vassal opinion", "+10 Tribal Ruler Opinion", "+20 Same trait opinion", "−5 Attraction opinion", "−0.25 Health"
	), 20),
	Scaly(Congenital, "scaly", List.of(),
			"""
					+10 Natural Dread
					 +10 Same trait opinion
					 −10 Vassal opinion
					 −30 Attraction opinion
					 −20% Fertility
					"""
			, 0),
	Club_footed(Congenital, "clubfooted", List.of(new SkillModifier(Prowess, -2)), """
			+10 Same trait opinion
			 −10 Attraction opinion
			""", 0),
	Dwarf(Congenital, "dwarf", List.of(new SkillModifier(Prowess, -4)), """
			+20 Same trait opinion
			 −20 Attraction opinion
			""", 0),
	Hunchbacked(Congenital, "hunchbacked", List.of(new SkillModifier(Prowess, -2)), """
			+10 Same trait opinion
			 −30 Attraction opinion
			 −10 Vassal opinion
			""", -10),
	Sterile(Congenital, "infertile", List.of(), """
			−50% Fertility
			""", 0),
	Wheezing(Congenital, "wheezing", List.of(), """
			−10 Vassal opinion
			 −0.15 Health
			""", -10),
	Spindly(Congenital, "spindly", List.of(new SkillModifier(Prowess, -1)), """
			−10 Attraction opinion
			 −0.25 Health
			""", -10),
	Bleeder(Congenital, "bleeder", List.of(), """
			−10 Vassal opinion
			 -1.5 Health
			""", -20),
	Beautiful(Congenital, "beauty_good_3", List.of(new SkillModifier(Diplomacy, 3)), """
			 +30 Attraction opinion
			 +30% Fertility
			 Slower Portrait Aging
			""", 120),
	Handsome(Congenital, "beauty_good_2", List.of(new SkillModifier(Diplomacy, 2)), """
			+20 Attraction opinion
			 +20% Fertility
			 Slower Portrait Aging
			""", 80),
	Comely(Congenital, "beauty_good_1", List.of(new SkillModifier(Diplomacy, 1)), """
			+10 Attraction opinion
			 +10% Fertility
			 Slower Portrait Aging
			""", 40),
	Homely(Congenital, "beauty_bad_1", List.of(new SkillModifier(Diplomacy, -1)), """
			 −10 Attraction opinion
			−10% Fertility
			""", -10),
	Ugly(Congenital, "beauty_bad_2", List.of(new SkillModifier(Diplomacy, -2)), """
			−20 Attraction opinion
			 −20% Fertility
			""", -20),
	Hideous(Congenital, "beauty_bad_3", List.of(new SkillModifier(Diplomacy, -3)), """
				−30 Attraction opinion
				−30% Fertility
			""", -30),
	Genius(Congenital, "intellect_good_3", List.of(
			new SkillModifier(Diplomacy, 5),
			new SkillModifier(Martial, 5),
			new SkillModifier(Stewardship, 5),
			new SkillModifier(Intrigue, 5),
			new SkillModifier(Learning, 5)
	), """
			 +30% Monthly Lifestyle Experience
			""", 240),
	Intelligent(Congenital, "intellect_good_2", List.of(
			new SkillModifier(Diplomacy, 3),
			new SkillModifier(Martial, 3),
			new SkillModifier(Stewardship, 3),
			new SkillModifier(Intrigue, 3),
			new SkillModifier(Learning, 3)
	), """
			 +20% Monthly Lifestyle Experience
			""", 160),
	Quick(Congenital, "intellect_good_1", List.of(
			new SkillModifier(Diplomacy, 1),
			new SkillModifier(Martial, 1),
			new SkillModifier(Stewardship, 1),
			new SkillModifier(Intrigue, 1),
			new SkillModifier(Learning, 1)
	), """
			 +10% Monthly Lifestyle Experience
			""", 80),
	Slow(Congenital, "intellect_bad_1", List.of(
			new SkillModifier(Diplomacy, -2),
			new SkillModifier(Martial, -2),
			new SkillModifier(Stewardship, -2),
			new SkillModifier(Intrigue, -2),
			new SkillModifier(Learning, -2)
	), """
			 -10% Monthly Lifestyle Experience
			""", -15),
	Stupid(Congenital, "intellect_bad_2", List.of(
			new SkillModifier(Diplomacy, -4),
			new SkillModifier(Martial, -4),
			new SkillModifier(Stewardship, -4),
			new SkillModifier(Intrigue, -4),
			new SkillModifier(Learning, -4)
	), """
			 -20% Monthly Lifestyle Experience
			""", -30),
	Imbecile(Congenital, "intellect_bad_3", List.of(
			new SkillModifier(Diplomacy, -6),
			new SkillModifier(Martial, -6),
			new SkillModifier(Stewardship, -6),
			new SkillModifier(Intrigue, -6),
			new SkillModifier(Learning, -6)
	), """
			 -30% Monthly Lifestyle Experience
			""", -45),
	Herculean(Congenital, "physique_good_3", List.of(new SkillModifier(Prowess, 8)), """
			 +15 Attraction opinion
			  +1 Health
			""", 180),
	Robust(Congenital, "physique_good_2", List.of(new SkillModifier(Prowess, 4)), """
			 +10 Attraction opinion
			  +0.5 Health
			""", 120),
	Hale(Congenital, "physique_good_1", List.of(new SkillModifier(Prowess, 2)), """
			 +5 Attraction opinion
			  +0.25 Health
			""", 60),
	Delicate(Congenital, "physique_bad_1", List.of(new SkillModifier(Prowess, -2)), """
			  -0.25 Health
			""", -15),
	Frail(Congenital, "physique_bad_2", List.of(new SkillModifier(Prowess, -4)), """
			 -5 Attraction opinion
			  -0.5 Health
			""", -30),
	Feeble(Congenital, "physique_bad_3", List.of(new SkillModifier(Prowess, -6)), """
			 -10 Attraction opinion
			  -1 Health
			""", -45),

	// Physical
	Shrewd(Physical, "shrewd", List.of(
			new SkillModifier(Diplomacy, 2),
			new SkillModifier(Martial, 2),
			new SkillModifier(Stewardship, 2),
			new SkillModifier(Intrigue, 2),
			new SkillModifier(Learning, 2)
	), """
			""", 50),
	Strong(Physical, "strong", List.of(new SkillModifier(Prowess, 4)), """
						+0.5 Health
			""", 50),
	Scarred(Physical, "scarred", List.of(), """
			+0.1 Monthly prestige
			 +5 Attraction opinion
			""", 10),
	One_Eyed(Physical, "one_eyed", List.of(
			new SkillModifier(Learning, 1), new SkillModifier(Prowess, -2)
	), """
				+10 Natural Dread
			    −5 Attraction opinion
			    +100% Likelihood of capture or death in Battle
			""", 10),
	One_Legged(Physical, "one_legged", List.of(
			new SkillModifier(Learning, 1), new SkillModifier(Prowess, -4)
	), """
				-10 Natural Dread
			    −10 Attraction opinion
			    +100% Likelihood of capture or death in Battle
			""", -5),
	Disfigured(Physical, "disfigured", List.of(new SkillModifier(Diplomacy, -4)), """
			−20% Fertility
			 −20 Attraction opinion
			 +100% Likelihood of capture or death in Battle
			""", -10),
	Weak(Physical, "weak", List.of(new SkillModifier(Prowess, -4)), """
			−10 Attraction opinion
			 −10 Vassal opinion
			 −0.5 Health
			""", -10),
	Dull(Physical, "dull", List.of(
			new SkillModifier(Diplomacy, -2),
			new SkillModifier(Martial, -2),
			new SkillModifier(Stewardship, -2),
			new SkillModifier(Intrigue, -2),
			new SkillModifier(Learning, -2)
	), """
			""", -20),

	// 	Lifestyle,
	August(Lifestyle, "august", List.of(
			new SkillModifier(Diplomacy, 2), new SkillModifier(Martial, 1)
	), """
						+1 Monthly Prestige
			""", 50),
	Diplomat(Lifestyle, "diplomat", List.of(new SkillModifier(Diplomacy, 3)), """
			+20 Independent Ruler Opinion
			 +25% Personal Scheme Power
			""", 50),
	Patriarch(Lifestyle, "family_first", List.of(), """
			+10 House Opinion
			 +15 Close Family Opinion
			 +20% Fertility
			 +20% Stress loss
			""", 50),
	Gallant(Lifestyle, "gallant", List.of(
			new SkillModifier(Martial, 2), new SkillModifier(Prowess, 4)
	), """
			+20% Monthly prestige
			 +20 Attraction opinion
			""", 50),
	Overseer(Lifestyle, "overseer", List.of(
			new SkillModifier(Martial, 2), new SkillModifier(Stewardship, 2)
	), """
						+50% Monthly Control Growth
			""", 50),
	Strategist(Lifestyle, "strategist", List.of(
			new SkillModifier(Diplomacy, 1), new SkillModifier(Martial, 3)
	), """
			+25% Enemy Fatal Casualties
			 Crosses Rivers & Straits without Advantage loss
			""", 50),
	Administrator(Lifestyle, "administrator", List.of(
			new SkillModifier(Diplomacy, 1), new SkillModifier(Stewardship, 3)
	), """
						+5 Vassal opinion
			""", 50),
	Architect(Lifestyle, "architect", List.of(new SkillModifier(Stewardship, 2)), """
			−15% Building and Holding Construction Time
			 −10% Building and Holding Gold Cost
			""", 50),
	Avaricious(Lifestyle, "avaricious", List.of(new SkillModifier(Stewardship, 2)), """
						+15% Holding Taxes
			""", 50),
	Schemer(Lifestyle, "schemer", List.of(new SkillModifier(Intrigue, 5)), """
						+25% Hostile Scheme Power
			""", 50),
	Seducer(Lifestyle, "seducer", List.of(new SkillModifier(Intrigue, 3)), """
				+20% Fertility
			    +40 Attraction opinion
			""", 50),
	Torturer(Lifestyle, "torturer", List.of(new SkillModifier(Prowess, 4)), """
			+50% Dread gain
			 +25% Hostile Scheme Resistance
			 +10% Levy Size
			""", 50),
	Scholar(Lifestyle, "scholar", List.of(new SkillModifier(Learning, 5)), """
				+15% Monthly Development Growth
			    +10 Hostile Scheme Success chance
			    +10 Personal Scheme Success chance
			""", 50),
	Theologian(Lifestyle, "theologian", List.of(new SkillModifier(Learning, 3)), """
						+20% Monthly piety
			""", 50),
	Whole_of_Body(Lifestyle, "whole_of_body", List.of(), """
			+25% Fertility
			 −25% Stress gain
			 +0.5 Health
			""", 75),
	Herbalist(Lifestyle, "herbalist", List.of(
			new SkillModifier(Learning, 2), new SkillModifier(Intrigue, 2)
	), """
						+0.5 Disease Resistance Health Boost
			""", 50),
	Gardener(Lifestyle, "gardener", List.of(new SkillModifier(Stewardship, 2)), """
				−20% Stress Gain
			    +10 Courtier and Guest Opinion
			""", 50),
	Aspiring_Blademaster(Lifestyle, "blademaster_1", List.of(new SkillModifier(Prowess, 3)), """
						+0.25 Disease Resistance Health Boost
			""", 20),
	Blademaster(Lifestyle, "blademaster_2", List.of(new SkillModifier(Prowess, 6)), """
						+0.5 Disease Resistance Health Boost
			""", 40),
	Legendary_Blademaster(Lifestyle, "blademaster_3", List.of(new SkillModifier(Prowess, 12)), """
						+1 Disease Resistance Health Boost
			""", 60),

	Novice_Hunter(Lifestyle, "hunter_1", List.of(new SkillModifier(Prowess, 2)), """
					+10% Stress loss
			""", 20),
	Hunter(Lifestyle, "hunter_2", List.of(new SkillModifier(Prowess, 4)), """
				+15% Stress loss
			""", 40),
	Master_Hunter(Lifestyle, "hunter_3", List.of(new SkillModifier(Prowess, 6)), """
				+20% Stress loss
			""", 60),
	Wise_Man(Lifestyle, "mystic_1", List.of(new SkillModifier(Learning, 1)), """
			""", 20),
	Mystic(Lifestyle, "mystic_2", List.of(new SkillModifier(Learning, 2)), """
			""", 40),
	Miracle_Worker(Lifestyle, "mystic_3", List.of(new SkillModifier(Learning, 4)), """
			""", 60),
	Eager_Reveler(Lifestyle, "reveler_1", List.of(new SkillModifier(Diplomacy, 2), new SkillModifier(Intrigue, 1)), """
						+10 Same trait opinion
			""", 25),
	Famous_Reveler(Lifestyle, "reveler_2", List.of(new SkillModifier(Diplomacy, 3), new SkillModifier(Intrigue, 2)), """
						+15 Same trait opinion
			""", 25),
	Legendary_Reveler(Lifestyle, "reveler_3", List.of(new SkillModifier(Diplomacy, 4), new SkillModifier(Intrigue, 3)), """
						+20 Same trait opinion
			""", 25),
	Novice_Physician(Lifestyle, "physician_1", List.of(new SkillModifier(Learning, 1)), """
						+0.25 Disease Resistance Health Boost
			""", 20),
	Physician(Lifestyle, "physician_2", List.of(new SkillModifier(Learning, 2)), """
						+0.5 Disease Resistance Health Boost
			""", 40),
	Renowned_Physician(Lifestyle, "physician_3", List.of(new SkillModifier(Learning, 4)), """
						+1 Disease Resistance Health Boost
			""", 60),

	// 	Commander,
	Aggressive_Attacker(Commander, "aggressive_attacker", List.of(), """
							+25% Enemy Fatal Casualties
			""", 25),
	Flexible_leader(Commander, "flexible_leader", List.of(), """
							−50% Enemy Defensive Advantage
			""", 25),
	Forder(Commander, "forder", List.of(), """
					Crosses Rivers & Straits without Advantage penalties
			""", 25),
	Holy_Warrior(Commander, "holy_warrior", List.of(), """
			+10 Faith Hostility Advantage
			""", 25),
	Logistician(Commander, "logistician", List.of(), """
							+100% Supply Duration
			""", 25),
	Military_Engineer(Commander, "military_engineer", List.of(), """
			−30% Siege Phase Time
			""", 25),
	Organizer(Commander, "organizer", List.of(), """
			+25% Movement Speed
			 −20% Retreat Losses
			""", 25),
	Reaver(Commander, "reaver", List.of(), """
			+100% Raid Speed
			Death natural.png −75% Hostile County Attrition
			""", 25),
	Unyielding_Defender(Commander, "unyielding_defender", List.of(), """
						−25% Friendly Fatal Casualties
			""", 25),
	Cautious_Leader(Commander, "cautious_leader", List.of(), """
			+4 Minimum Battle Roll
			 −2 Maximum Battle Roll
			""", 25),
	Reckless(Commander, "reckless", List.of(), """
			+6 Maximum Battle Roll
			 −4 Minimum Battle Roll
			""", 25),
	Forest_Fighter(Commander, "forest_fighter", List.of(), """
						+5 Advantage in Forest and Taiga terrain
			""", 25),
	Open_Terrain_Expert(Commander, "open_terrain_expert", List.of(), """
			+4 Advantage in Farmlands, Plains and Steppe terrain
			""", 25),
	Rough_Terrain_Expert(Commander, "rough_terrain_expert", List.of(), """
							+4 Advantage in Hills, Mountains and Wetlands terrain
			""", 25),
	Desert_Warrior(Commander, "desert_warrior", List.of(), """
			+5 Advantage in Desert, Desert Mountains, Drylands and Oasis terrain
			 No Desert Supply Limit Penalty
			""", 25),
	Jungle_Stalker(Commander, "jungle_stalker", List.of(), """
			+6 Jungle Advantage
			Death natural.png −50% Jungle Attrition
			 No Jungle Supply Limit Penalty
			""", 25),
	Winter_Soldier(Commander, "winter_soldier", List.of(), """
			+25% Movement Speed in Baronies affected by Winter
			 +4 Advantage in in Baronies affected by Winter
			""", 25),

	// 	Infamous,
	Adulterer(Infamous, "adulterer", List.of(), """
							+10 Same trait opinion
			""", -5),
	Fornicator(Infamous, "fornicator", List.of(), """
			+10 Same trait opinion
			""", -5),
	Deviant(Infamous, "deviant", List.of(), """
			+25% Stress loss
			 +35 Same trait opinion
			""", -5),
	Incestuous(Infamous, "incestuous", List.of(), """
							+20 Same trait opinion
			""", 0),
	Witch(Infamous, "witch", List.of(
			new SkillModifier(Intrigue, 1), new SkillModifier(Learning, 1), new SkillModifier(Diplomacy, -1)
	), """
			+20 Same trait opinion
			""", 10),
	Cannibal(Infamous, "cannibal", List.of(new SkillModifier(Prowess, 2)), """
			+15% Stress loss
			 +20 Natural Dread
			 +35 Same trait opinion
			""", 40),
	Murderer(Infamous, "murderer", List.of(), """
							−15 General opinion
			""", -10),


	// 	CopingMechanism,
	Drunkard(CopingMechanism, "drunkard", List.of(
			new SkillModifier(Stewardship, -2), new SkillModifier(Prowess, 2)
	), """
			+20% Stress loss
			 +10 Same trait opinion
			 −0.15 Health
			""", -10),
	Flagellant(CopingMechanism, "flagellant", List.of(new SkillModifier(Prowess, -2)), """
			+20% Stress loss
			 −0.15 Health
			""", -10),
	Comfort_Eater(CopingMechanism, "comfort_eater", List.of(new SkillModifier(Stewardship, -1)), """
							+20% Stress loss
			""", -5),
	Contrite(CopingMechanism, "contrite", List.of(new SkillModifier(Intrigue, -2)), """
			+20% Stress loss
			""", -5),
	Improvident(CopingMechanism, "improvident", List.of(new SkillModifier(Diplomacy, 1)), """
			+20% Stress loss
			 −15% Monthly income
			""", -5),
	Inappetetic(CopingMechanism, "inappetetic", List.of(
			new SkillModifier(Diplomacy, -1), new SkillModifier(Prowess, -3)
	), """
			+20% Stress loss
			""", -5),
	Reclusive(CopingMechanism, "reclusive", List.of(
			new SkillModifier(Diplomacy, -2), new SkillModifier(Stewardship, -1)
	), """
			+20% Stress loss
			""", -5),
	Irritable(CopingMechanism, "irritable", List.of(
			new SkillModifier(Diplomacy, -2), new SkillModifier(Martial, -1), new SkillModifier(Prowess, 2)
	), """
			+20% Stress loss
			 +10% Dread gain
			 −5 Attraction opinion
			Enables the Release you Anger interaction
			""", 0),
	Rakish(CopingMechanism, "rakish", List.of(
			new SkillModifier(Intrigue, 1), new SkillModifier(Diplomacy, -1)
	), """
			+20% Stress loss
			 +5 Same trait opinion
			 −5 Attraction opinion
			""", 0),
	Hashishiyah(CopingMechanism, "hashishiyah", List.of(
			new SkillModifier(Stewardship, -2), new SkillModifier(Learning, -2)
	), """
			+20% Stress loss
			 +10 Same trait opinion
			""", 5),
	Profligate(CopingMechanism, "profligate", List.of(), """
			+20% Stress loss
			 +0.5 Monthly prestige
			 −10% Monthly income
			""", 10),
	Confider(CopingMechanism, "confider", List.of(new SkillModifier(Diplomacy, 1)), """
			+20% Stress loss
			""", 15),
	Journaller(CopingMechanism, "journaller", List.of(new SkillModifier(Learning, 1)), """
			+20% Stress loss
			""", 15),
	Athletic(CopingMechanism, "athletic", List.of(new SkillModifier(Prowess, 1)), """
			+20% Stress loss
			 +0.25 Health
			""", 40),

	// 	Dynasty,
	Bastard(Dynasty, "bastard", List.of(new SkillModifier(Diplomacy, -1)), """
			+5 Same trait opinion
			 −15 Dynasty Opinion
			 Can not inherit titles
			""", 0),
	Born_in_the_Purple(Dynasty, "born_in_the_purple", List.of(), """
			+0.1 Monthly prestige
			 −25% Short Reign Duration
			""", 40),
	Legitimized_Bastard(Dynasty, "legitimized_bastard", List.of(new SkillModifier(Diplomacy, -1)), """
							−10 Dynasty Opinion
			""", 0),
	Wild_Oat(Dynasty, "wild_oat", List.of(new SkillModifier(Diplomacy, -1)), """
			""", 0),

	// 	Descendant,
	Sayyid(Descendant, "sayyid", List.of(), """
						+5 Same faith opinion
			""", 25),

	// 	Decision,
	Pilgrim(Decision, "pilgrim", List.of(), """
			+10% Monthly piety
			 +5 Same faith opinion
			""", 30),
	Hajji(Decision, "hajjaj", List.of(), """
			+10% Monthly piety
			 +5 Same faith opinion
			""", 30),

	// 	Event
	Adventurer(Event, "adventurer", List.of(), """
			+1 Martial per Level of Fame
			 +1 Prowess per Level of Fame
			 -50% Men-At-Arms Maintenance
			 +20 Same trait opinion
			 -10 General Opinion
			 No different Culture Opinion penalty
			""", 50),
	Berserker(Event, "berserker", List.of(
			new SkillModifier(Martial, 2), new SkillModifier(Prowess, 5), new SkillModifier(Diplomacy, -2)
	), """
			+10 Same trait opinion
			""", 40),
	Heresiarch(Event, "heresiarch", List.of(
			new SkillModifier(Martial, 2), new SkillModifier(Learning, 2), new SkillModifier(Prowess, 2)
	), """
			+5 Advantage against co-religionists
			 +10 Same faith opinion
			""", 50),
	Crusader_King(Event, "crusader_king", List.of(
			new SkillModifier(Martial, 3), new SkillModifier(Prowess, 2)
	), """
			 +5 Faith Hostility Advantage
			 −20% Retreat Losses
			 -15% Friendly Fatal Casualties
			 +50% Monthly Control
			 +35 Popular opinion
			 +15 Same faith opinion
			 +10 Same trait opinion
			Ignore Negative Culture Opinion
			""", 120),
	Peasant_Leader(Event, "peasant_leader", List.of(), """
			-50% Army Gold Maintenance
			 +10 Popular opinion
			 +25 Same trait opinion
			 −10 General opinion
			""", 100),
	Poet(Event, "poet", List.of(), """
			+1 Diplomacy per Level of Fame
			 +10% Stress Loss
			Enables the Send Poem character interaction
			""", 40),
	Raider(Event, "viking", List.of(
			new SkillModifier(Martial, 2), new SkillModifier(Prowess, 3)
	), """
			+0.3 Monthly prestige
			 +5 Same trait opinion
			""", 25),
	Shieldmaiden(Event, "shieldmaiden", List.of(
			new SkillModifier(Martial, 3), new SkillModifier(Prowess, 3)
	), """
			+5 Same trait opinion
			""", 40),
	Varangian(Event, "varangian", List.of(
			new SkillModifier(Diplomacy, 1), new SkillModifier(Martial, 2), new SkillModifier(Prowess, 2)
	), """
			 +10 Same trait opinion
			""", 40),
	Crusader(Event, "crusader", List.of(
			new SkillModifier(Martial, 2), new SkillModifier(Prowess, 3)
	), """
			+15 Clergy opinion
			 +5 Same faith opinion
			""", 50),
	Mujahid(Event, "mujahid", List.of(
			new SkillModifier(Martial, 2), new SkillModifier(Prowess, 3)
	), """
			+15 Clergy opinion
			 +5 Same faith opinion
			""", 50),
	Warrior_of_the_Faith(Event, "faith_warrior", List.of(
			new SkillModifier(Martial, 2), new SkillModifier(Prowess, 3)
	), """
			+15 Clergy opinion
			 +5 Same faith opinion
			""", 50),
	;
	public final TraitType type;
	public final String internalName;
	public final List<SkillModifier> skillModifiers;
	public final List<String> otherModifiers;
	public final int cost;
	public final Set<Trait> exclusiveWith = new LinkedHashSet<>();
	public final static Map<String, Trait> byInternalName = new HashMap<>();

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

		allExclusiveWithEachOther(List.of(Beautiful, Handsome, Comely, Homely, Ugly, Hideous));
		allExclusiveWithEachOther(List.of(Genius, Intelligent, Quick, Slow, Stupid, Imbecile));
		allExclusiveWithEachOther(List.of(Herculean, Robust, Hale, Delicate, Frail, Feeble));

		Strong.addExclusiveWith(Weak);
		Shrewd.addExclusiveWith(Dull);
		Giant.addExclusiveWith(Dwarf);

		allExclusiveWithEachOther(List.of(Aspiring_Blademaster, Blademaster, Legendary_Blademaster));
		allExclusiveWithEachOther(List.of(Novice_Hunter, Hunter, Master_Hunter));
		allExclusiveWithEachOther(List.of(Wise_Man, Mystic, Miracle_Worker));
		allExclusiveWithEachOther(List.of(Eager_Reveler, Famous_Reveler, Legendary_Reveler));
		allExclusiveWithEachOther(List.of(Novice_Physician, Physician, Renowned_Physician));

		Arrays.stream(Trait.values()).forEach(t -> byInternalName.put(t.internalName, t));
	}

	private static void allExclusiveWithEachOther(List<Trait> traits) {
		for (int primaryCursor = 0; primaryCursor < traits.size() - 1; primaryCursor++) {
			for (int secondaryCursor = primaryCursor + 1; secondaryCursor < traits.size(); secondaryCursor++) {
				traits.get(primaryCursor).addExclusiveWith(traits.get(secondaryCursor));
			}
		}
	}

	Trait(TraitType type, String internalName, List<SkillModifier> skillModifiers, List<String> otherModifiers, int cost) {
		this.type = type;
		this.internalName = internalName;
		this.skillModifiers = skillModifiers;
		this.otherModifiers = otherModifiers;
		this.cost = cost;
	}

	Trait(TraitType type, String internalName, List<SkillModifier> skillModifiers, String otherModifierLines, int cost) {
		this.type = type;
		this.internalName = internalName;
		this.skillModifiers = skillModifiers;
		this.otherModifiers = Arrays.stream(otherModifierLines.split("\n"))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.collect(Collectors.toList());
		this.cost = cost;
	}

	public void addExclusiveWith(Trait other) {
		this.exclusiveWith.add(other);
		other.exclusiveWith.add(this);
	}
}
