package utils;

/**
 * Represents a user.
 */
public class User {
	private long userId;
	private String username;

	/**
	 * Constructs this user with the given id and name.
	 * @param id	-the user id
	 * @param name	-the user name
	 */
	public User(long id, String name) {
		this.userId = id;
		this.username = name;
	}

	/**
	 * Returns the id of this user.
	 * @return	-the user id
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * Returns the name of this user.
	 * @return	-the username
	 */
	public String getUsername() {
		return username;
	}
}