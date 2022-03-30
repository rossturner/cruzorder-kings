package technology.rocketjump.civblitz.controllers;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import technology.rocketjump.civblitz.mapgen.MapSettings;
import technology.rocketjump.civblitz.mapgen.MapSettingsGenerator;
import technology.rocketjump.civblitz.model.Card;
import technology.rocketjump.civblitz.model.CardCategory;
import technology.rocketjump.civblitz.model.SourceDataRepo;
import technology.rocketjump.civblitz.modgenerator.CompleteModGenerator;
import technology.rocketjump.civblitz.modgenerator.ModHeaderGenerator;
import technology.rocketjump.civblitz.modgenerator.model.ModdedCivInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/example")
public class ExampleController {

	private final SourceDataRepo sourceDataRepo;
	private final CompleteModGenerator completeModGenerator;
	private final ModHeaderGenerator modHeaderGenerator;
	private final MapSettingsGenerator mapSettingsGenerator;
	private final DSLContext create;

	@Autowired
	public ExampleController(SourceDataRepo sourceDataRepo, CompleteModGenerator completeModGenerator,
							 ModHeaderGenerator modHeaderGenerator, MapSettingsGenerator mapSettingsGenerator, DSLContext create) {
		this.sourceDataRepo = sourceDataRepo;
		this.completeModGenerator = completeModGenerator;
		this.modHeaderGenerator = modHeaderGenerator;
		this.mapSettingsGenerator = mapSettingsGenerator;
		this.create = create;
	}

	@GetMapping("/cards")
	public Collection<Card> getAllCards() {
		return sourceDataRepo.getAll();
	}

	@GetMapping("/sql")
	public List<String> getSqlExecution() {
//		List<Contract> contracts = create.selectFrom(CONTRACT).fetchInto(Contract.class);
//
//		ContractRecord contractRecord = create.newRecord(CONTRACT);
//		contractRecord.setContractId(contracts.size() + 1);
//		contractRecord.store();
//
//		return create.selectFrom(CONTRACT).fetchInto(Contract.class);
		return List.of();
	}

	@GetMapping("/map")
	public MapSettings generateMapSettings(@RequestParam(name="players") int players) {
		return mapSettingsGenerator.generate(players);
	}

	@GetMapping("/user")
	public Principal getUser(Principal principal) {
		// Go to http://localhost:8080/oauth2/authorization/discord to login, redirected to redirect URI
		return principal;
	}

	@GetMapping(value = "/mod", produces = "application/zip")
	@ResponseBody
	public byte[] getSomething(HttpServletResponse response) throws IOException {
		response.setContentType("application/zip");
		response.setStatus(HttpServletResponse.SC_OK);
		Map<CardCategory, Card> selectedCards = new HashMap<>();
		for (Card card : List.of(
				sourceDataRepo.getByTraitType("TRAIT_CIVILIZATION_MAYAB"),
				sourceDataRepo.getByTraitType("TRAIT_LEADER_KUPES_VOYAGE"),
				sourceDataRepo.getByTraitType("TRAIT_CIVILIZATION_DISTRICT_SEOWON"),
				sourceDataRepo.getByTraitType("TRAIT_CIVILIZATION_UNIT_AZTEC_EAGLE_WARRIOR")
		)) {
			selectedCards.put(card.getCardCategory(), card);
		}

		ModdedCivInfo civInfo = new ModdedCivInfo(selectedCards, null);
		String modName = modHeaderGenerator.createFor(civInfo.selectedCards).modName;
		response.addHeader("Content-Disposition", "attachment; filename=\"CivBlitz_"+modName+".zip\"");

		return completeModGenerator.generateMod(civInfo);
	}

}
