package at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty;

import java.util.Date;

import at.jku.cis.iVolunteer.model.meta.core.property.Location;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.Tuple;
//import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;

public class FlatPropertyDefinitionTypes {

	public static class TextPropertyDefinition extends FlatPropertyDefinition<String> {

		public TextPropertyDefinition() {
			this.type = PropertyType.TEXT;
		}
	}

	public static class LongTextPropertyDefinition extends FlatPropertyDefinition<String> {
		public LongTextPropertyDefinition() {
			this.type = PropertyType.LONG_TEXT;
		}
	}

	public static class LongPropertyDefinition extends FlatPropertyDefinition<Long> {
		public LongPropertyDefinition() {
			this.type = PropertyType.WHOLE_NUMBER;
		}
	}

	public static class DoublePropertyDefinition extends FlatPropertyDefinition<Double> {
		public DoublePropertyDefinition() {
			this.type = PropertyType.FLOAT_NUMBER;
		}
	}

	public static class BooleanPropertyDefinition extends FlatPropertyDefinition<Boolean> {

		public BooleanPropertyDefinition() {
			this.type = PropertyType.BOOL;
		}
	}

	public static class DatePropertyDefinition extends FlatPropertyDefinition<Date> {
		public DatePropertyDefinition() {
			this.type = PropertyType.DATE;
		}
	}
	
//	public static class EnumPropertyDefinition extends PropertyDefinition<EnumEntry> {
//		public EnumPropertyDefinition() {
//			this.type = PropertyType.ENUM;
//		}
//	}
	
	public static class TuplePropertyDefinition<X, Y> extends FlatPropertyDefinition<Tuple<X, Y>> {
		public TuplePropertyDefinition() {
			this.type = PropertyType.TUPLE;
		}
	}
	
	public static class LocationPropertyDefinition extends FlatPropertyDefinition<Location> {

		public LocationPropertyDefinition() {
			this.type = PropertyType.LOCATION;

		}
	}
}
