package at.jku.cis.iVolunteer.model.meta.core.property.definition;

import java.util.Date;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

public class PropertyDefinitionTypes {

	static public class TextPropertyDefinition extends PropertyDefinition<String> {

		public TextPropertyDefinition() {
			this.type = PropertyType.TEXT;
		}

	}

	static public class LongTextPropertyDefinition extends PropertyDefinition<String> {
		public LongTextPropertyDefinition() {
			this.type = PropertyType.LONG_TEXT;
		}

	}

	static public class LongPropertyDefinition extends PropertyDefinition<Long> {
		public LongPropertyDefinition() {
			this.type = PropertyType.WHOLE_NUMBER;
		}
	}

	static public class DoublePropertyDefinition extends PropertyDefinition<Double> {
		public DoublePropertyDefinition() {
			this.type = PropertyType.FLOAT_NUMBER;
		}

	}

	static public class BooleanPropertyDefinition extends PropertyDefinition<Boolean> {

		public BooleanPropertyDefinition() {
			this.type = PropertyType.BOOL;
		}
	}

	static public class DatePropertyDefinition extends PropertyDefinition<Date> {
		public DatePropertyDefinition() {
			this.type = PropertyType.DATE;
		}
	}
}
