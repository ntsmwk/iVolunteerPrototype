//package at.jku.cis.iVolunteer.model.meta.core.property.instance.old;
//
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
//
//@Document
//public class DoubleProperty extends SingleProperty<Double> {
//	
//	public DoubleProperty() {
//		super();
//		type = PropertyType.FLOAT_NUMBER;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (!(obj instanceof DoubleProperty)) {
//			return false;
//		}
//		return ((DoubleProperty) obj).id.equals(id);
//	}
//
//}
