package technology.rocketjump.cruzorder.controllers;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technology.rocketjump.cruzorder.model.Trait;
import technology.rocketjump.cruzorder.model.rest.TraitResponse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/traits")
public class TraitsController {

	private final DSLContext create;
	@Autowired
	public TraitsController(DSLContext create) {
		this.create = create;
	}

	@GetMapping()
	public List<TraitResponse> getAllTraits() {
		return Stream.of(Trait.values()).map(TraitResponse::new).collect(Collectors.toList());
	}

}
