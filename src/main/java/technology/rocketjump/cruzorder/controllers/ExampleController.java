package technology.rocketjump.cruzorder.controllers;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/example")
public class ExampleController {

	private final DSLContext create;

	@Autowired
	public ExampleController(DSLContext create) {
		this.create = create;
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

	@GetMapping("/user")
	public Principal getUser(Principal principal) {
		// Go to http://localhost:8080/oauth2/authorization/discord to login, redirected to redirect URI
		return principal;
	}

}
