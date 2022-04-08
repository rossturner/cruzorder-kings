package technology.rocketjump.cruzorder.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TraitResponse {

	public final String internalName;
	public final String name;
	public final String type;
	public final Set<String> exclusiveWith;
	public final List<SkillModifier> skillModifiers;
	public final List<String> otherModifiers;
	public final int cost;

	public TraitResponse(Trait trait) {
		this.internalName = trait.internalName;
		this.name = trait.name().replace("_", " ");
		this.type = trait.type.name();
		this.skillModifiers = trait.skillModifiers;
		this.otherModifiers = trait.otherModifiers;
		this.cost = trait.cost;
		this.exclusiveWith = trait.exclusiveWith.stream().map(t -> t.internalName).collect(Collectors.toSet());
	}

}
