package at.jku.cis.iVolunteer.marketplace._mapper.clazz;

import java.util.LinkedList;
import java.util.List;

import at.jku.cis.iVolunteer.marketplace._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.user.User;

public class UserToClassDefinitionMapper implements OneWayMapper<User, ClassDefinition> {

	@Override
	public ClassDefinition toTarget(User source) {
		if (source == null) {return null;}
		
		ClassDefinition cd = new ClassDefinition();
		cd.setId(source.getId());
		cd.setName(source.getFirstname() + " " + source.getLastname());
		
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

	@Override
	public List<ClassDefinition> toTargets(List<User> sources) {
		if (sources == null) {return null;}
		
		List<ClassDefinition> classDefinitions = new LinkedList<ClassDefinition>();
		for (User user : sources) {
			classDefinitions.add(toTarget(user));
		}

		return classDefinitions;
	}


}
