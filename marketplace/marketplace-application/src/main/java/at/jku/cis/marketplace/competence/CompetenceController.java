package at.jku.cis.marketplace.competence;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompetenceController {

	@GetMapping("/competence")
	public List<Competence> findAll() {
		Competence competence1 = new Competence("Planning");
		Competence competence2 = new Competence("Motivation");
		Competence competence3 = new Competence("Leadership");
		return Arrays.asList(competence1, competence2, competence3);
	}

}
