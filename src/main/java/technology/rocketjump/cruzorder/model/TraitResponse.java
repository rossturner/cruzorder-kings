package technology.rocketjump.cruzorder.model;

import java.util.List;
import java.util.Set;

public class TraitResponse {

	public final String internalName;
	public final String name;
	public final Set<Trait> exclusiveWith;
	public final List<SkillModifier> skillModifiers;
	public final List<String> otherModifiers;
	public final int cost;

	public TraitResponse(Trait trait) {
		this.internalName = trait.internalName;
		this.name = trait.name().replace("_", " ");
		this.skillModifiers = trait.skillModifiers;
		this.otherModifiers = trait.otherModifiers;
		this.cost = trait.cost;
		this.exclusiveWith = trait.exclusiveWith;
	}

}
