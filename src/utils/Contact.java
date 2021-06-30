package utils;

public class Contact {
	private long id;
	private String name;
	private String email;
	
	public Contact(long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
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
}