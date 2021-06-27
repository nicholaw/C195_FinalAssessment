package utils;

public class User {
	private long userId;
	private String username;
	
	public User(long id, String name) {
		this.userId = id;
		this.username = name;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
}