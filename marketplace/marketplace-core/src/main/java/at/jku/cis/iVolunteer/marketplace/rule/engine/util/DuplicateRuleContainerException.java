package at.jku.cis.iVolunteer.marketplace.rule.engine.util;

public class DuplicateRuleContainerException extends RuntimeException {

    public DuplicateRuleContainerException(String message) {
        super(message);
    }

    public String getErrorCode(){
        return "RuleEngine - rule already exists in container";
    }
}
