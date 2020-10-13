package at.jku.cis.iVolunteer.trustifier.blockchain;

public class BcClassInstances {

	private BcClassInstance[] verificationObjects;

	public BcClassInstances() {
	}

	public BcClassInstances(BcClassInstance[] verificationObjects) {
		super();
		this.setVerificationObjects(verificationObjects);
	}

	public BcClassInstance[] getVerificationObjects() {
		return verificationObjects;
	}

	public void setVerificationObjects(BcClassInstance[] verificationObjects) {
		this.verificationObjects = verificationObjects;
	}

}