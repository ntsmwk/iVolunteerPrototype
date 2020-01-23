package at.jku.cis.iVolunteer.trustifier.blockchain;

public class BcClassInstances {

	private BcClassInstance[] classInstances;

	public BcClassInstances() {
	}

	public BcClassInstances(BcClassInstance[] classInstances) {
		super();
		this.classInstances = classInstances;
	}

	public BcClassInstance[] getClassInstances() {
		return classInstances;
	}

	public void setClassInstances(BcClassInstance[] classInstances) {
		this.classInstances = classInstances;
	}

}
