package at.jku.cis.iVolunteer.model.meta.core.property.definition;

import java.util.Date;

import at.jku.cis.iVolunteer.model.meta.core.property.LevelListPropertyEntry;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

public class PropertyDefinitionTypes {

	public static class TextPropertyDefinition extends PropertyDefinition<String> {

		public TextPropertyDefinition() {
			this.type = PropertyType.TEXT;
		}
	}

	public static class LongTextPropertyDefinition extends PropertyDefinition<String> {
		public LongTextPropertyDefinition() {
			this.type = PropertyType.LONG_TEXT;
		}
	}

	public static class LongPropertyDefinition extends PropertyDefinition<Long> {
		public LongPropertyDefinition() {
			this.type = PropertyType.WHOLE_NUMBER;
		}
	}

	public static class DoublePropertyDefinition extends PropertyDefinition<Double> {
		public DoublePropertyDefinition() {
			this.type = PropertyType.FLOAT_NUMBER;
		}
	}

	public static class BooleanPropertyDefinition extends PropertyDefinition<Boolean> {

		public BooleanPropertyDefinition() {
			this.type = PropertyType.BOOL;
		}
	}

	public static class DatePropertyDefinition extends PropertyDefinition<Date> {
		public DatePropertyDefinition() {
			this.type = PropertyType.DATE;
		}
	}
	
	public static class LevelListPropertyDefinition extends PropertyDefinition<LevelListPropertyEntry> {
		public LevelListPropertyDefinition() {
			this.type = PropertyType.LEVEL_LIST;
		}
	}
}
