package utils;

/**
 * Represents an employee contact who can be assigned to an appointment.
 */
public class Contact {
	private long id;
	private String name;
	private String email;

	/**
	 * Constructs the contact with the given id, name, and email.
	 * @param id	-the contact's id
	 * @param name	-the contact's name
	 * @param email	-the contact's email address
	 */
	public Contact(long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	/**
	 * Returns this contact's id.
	 * @return	-the contact id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns this contact's name.
	 * @return	-the contact name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns this contact's email address.
	 * @return	-the email address
	 */
	public String getEmail() {
		return email;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Contact))
			return false;
		if(((Contact) obj).getId() == this.getId())
			return true;
		return false;
	}
}//Contact