package at.jku.cis.iVolunteer.model.meta.matching;

public enum AggregationFunction {
	NONE(""), TEST1("1"), TEST2("2"), TEST3("3"), TEST4("4");
	
	private final String aggregationFunction;
	
	private AggregationFunction (String aggregationFunction) {
		this.aggregationFunction = aggregationFunction;
	}

	@Override
	public String toString() {
		return aggregationFunction;
	}
	
	public String getAssociationParameter() {
		return this.aggregationFunction;
	}
	
	public static AggregationFunction getFromAggregationFunction(String type){
        for(AggregationFunction t : AggregationFunction.values()){
            if(t.getAssociationParameter().equals(type)){
                return t;
            }
        }
        throw new IllegalArgumentException();
    }	
}
