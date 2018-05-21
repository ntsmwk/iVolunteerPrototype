package at.jku.cis.iVolunteer.trustifier.model.task;

import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.trustifier.hash.IHashObject;
import at.jku.cis.iVolunteer.trustifier.model.competence.Competence;

public class Task  implements IHashObject{
	private String id;
	private String name;
	private String description;
	private Address address;
	private Material material;
	private Task parent;
	private Date startDate;
	private Date endDate;
	private List<Competence> acquirableCompetences;
	private List<Competence> requiredCompetences;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Task getParent() {
		return parent;
	}

	public void setParent(Task parent) {
		this.parent = parent;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Competence> getAcquirableCompetences() {
		return acquirableCompetences;
	}

	public void setAcquirableCompetences(List<Competence> acquirableCompetences) {
		this.acquirableCompetences = acquirableCompetences;
	}

	public List<Competence> getRequiredCompetences() {
		return requiredCompetences;
	}

	public void setRequiredCompetences(List<Competence> requiredCompetences) {
		this.requiredCompetences = requiredCompetences;
	}
	
	@Override
	public String toHashObject() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("name", name);
		json.addProperty("description", description);
		json.addProperty("address", address.toString());
		json.addProperty("material", material.toString());
		json.addProperty("parent", parent.getId());
		json.addProperty("startDate", startDate.toString());
		json.addProperty("endDate", endDate.toString());		
		json.addProperty("acquirableCompetences", acquirableCompetences.toString());
		json.addProperty("requiredCompetences", requiredCompetences.toString());	
		return json.toString();
	}	

	private class Address {
		private String latitude;
		private String langitude;

		private String street;
		private String streetNumber;
		private String city;
		private String region;
		private String country;

		public Address() {
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getLangitude() {
			return langitude;
		}

		public void setLangitude(String langitude) {
			this.langitude = langitude;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getStreetNumber() {
			return streetNumber;
		}

		public void setStreetNumber(String streetNumber) {
			this.streetNumber = streetNumber;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

	}

	private class Material {
		private String name;
		private String description;

		private Material() {
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
}
