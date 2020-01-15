package at.jku.cis.iVolunteer.marketplace.reset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.rule.DerivationRuleRepository;

@Service
public class ResetService {

	@Autowired private DerivationRuleRepository derivationRuleRepository;

	public void reset() {

		resetFahrtenspangeFake();

//		Reset Sunburst Fake
			//Reset ClassDefinitions
//			//Reset Flag for retrieval
		
//		Reset Sybos Fake
		
//		Reset
	}

	private void resetFahrtenspangeFake() {
		// Reset Fahrtenspange Derivation-Rule
		derivationRuleRepository.deleteAll();

		// Reset Fahrtenspange Task

		// Reset Fahrtenspange Badge
	}
}
