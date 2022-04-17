package technology.rocketjump.cruzorder.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import technology.rocketjump.cruzorder.mod.ModGenerator;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/mod")
public class ModController {

	private final ModGenerator modGenerator;

	@Autowired
	public ModController(ModGenerator modGenerator) {
		this.modGenerator = modGenerator;
	}

	@GetMapping(produces = "application/zip")
	@ResponseBody
	public byte[] getModZip(HttpServletResponse response) throws IOException {
		response.setContentType("application/zip");
		response.setStatus(HttpServletResponse.SC_OK);
		response.addHeader("Content-Disposition", "attachment; filename=\"cruzorder_viking_invasion.zip\"");

		return modGenerator.generateMod();
	}
}
