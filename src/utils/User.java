package utils;

public class User {
	private int userId;
	private String username;
	
	public User(int id, String name) {
		this.userId = id;
		this.username = name;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
}