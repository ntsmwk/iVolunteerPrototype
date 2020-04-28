//package at.jku.cis.iVolunteer.model.meta.form;
//
//import com.fasterxml.jackson.annotation.JsonCreator;
//
//public enum FormConfigurationRequestType {
//	DATABASE("DATABASE"), ATTACHED("ATTACHED");
//	
//	
//	private final String type;
//
//	private FormConfigurationRequestType(String type) {
//		this.type = type;
//	}
//	
//	public String getType() {
//		return this.type;
//	}
//	
//	@JsonCreator
//	public static FormConfigurationRequestType getFormConfigurationRequestType(String type) {
//		for(FormConfigurationRequestType t : FormConfigurationRequestType.values()){
//            if(t.getType().equals(type)){
//                return t;
//            }
//        }
//        throw new IllegalArgumentException();
//	}
//
//	@Override
//	public String toString() {
//		return type;
//	}
//}
