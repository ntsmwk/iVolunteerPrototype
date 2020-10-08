package at.jku.cis.iVolunteer.model.user;

public class Address {
	// address: {
	// street: string,
	// plz: string
	// houseNumber: string,
	// country: number (ID) --> Bitte Länderliste
	// }

	String street;
	String houseNumber;
	String postcode;
	String city;
	int countryCode;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCountry() {
		return countryCode;
	}

	public void setCountry(int country) {
		this.countryCode = country;
	}

}
