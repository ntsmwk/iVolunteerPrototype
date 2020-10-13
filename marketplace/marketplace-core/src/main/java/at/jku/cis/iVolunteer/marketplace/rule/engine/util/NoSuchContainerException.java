package at.jku.cis.iVolunteer.marketplace.rule.engine.util;

/**
 * Created by barry.wong
 */
public class NoSuchContainerException extends RuntimeException {

    public NoSuchContainerException(String message) {
        super(message);
    }

    public String getErrorCode(){
        return "RuleEngine - No such container";
    }
}
