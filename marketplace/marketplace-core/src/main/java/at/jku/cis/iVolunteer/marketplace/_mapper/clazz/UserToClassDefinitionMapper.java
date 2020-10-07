package at.jku.cis.iVolunteer.marketplace._mapper.clazz;

import java.util.LinkedList;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;

@Component
public class UserToClassDefinitionMapper {

	public ClassDefinition toTarget() {
		ClassDefinition cd = new ClassDefinition();
		cd.setId("user");
		cd.setName("Benutzer");
		cd.setProperties(new LinkedList<>());
		
		cd.getProperties().add(new ClassProperty<Object>("id", "ID", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("username", "Username", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("loginEmail", "Login E-Mail", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("formOfAddress", "Anrede", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("titleBefore", "Vorgestellter Titel", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("firstname", "Vorname", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("lastname", "Nachname", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("titleAfter", "Nachgestellter Titel", PropertyType.TEXT));

		cd.getProperties().add(new ClassProperty<Object>("nickname", "Spitzname", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("organizationPosition", "Organisation Position", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("organizationName", "Organisationsname", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("birthday", "Geburtstag", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("locations", "Orte", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("about", "Über mich", PropertyType.TEXT));

		cd.getProperties().add(new ClassProperty<Object>("address", "Adresse", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("street", "Straßenname", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("housenumber", "Hausnummer", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("postcode", "PLZ", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("city", "Stadt", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("country", "Land", PropertyType.TEXT));

		cd.getProperties().add(new ClassProperty<Object>("timeslots", "Timeslots", PropertyType.TEXT));

		cd.getProperties().add(new ClassProperty<Object>("phonenumbers", "Telefonnummern", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("websites", "Webseiten", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("emails", "E-Mails", PropertyType.TEXT));
		cd.getProperties().add(new ClassProperty<Object>("imageId", "Profilbild", PropertyType.TEXT));

		cd.getProperties().add(new ClassProperty<Object>("subscribedTenants", "Abonnements", PropertyType.TEXT));

		cd.getProperties().add(new ClassProperty<Object>("localRepositoryLocation", "Local Repository Referenz", PropertyType.TEXT));

		return cd;
	}

}
