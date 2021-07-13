package utils;

/**
 * Codes for types of appointments offered by the company.
 */
public enum Type {
	BRIEFING("Briefing"),
    CONSULTATION("Consultation"),
	DEBRIEFING("Debriefing"),
	ORIENTATION("Orientation"),
	PLANNING("Planning"),
	UPDATE("Update"),
    OTHER("Other");
	
	private final String type;

	/**
	 * Constructs this Type.
	 * @param type	-the type of appointment
	 */
	Type(String type) {
		this.type = type;
	}//constructor

	/**
	 * Returns the type of this appointment.
	 * @return	-the type of appointment
	 */
	public String getType() {
		return this.type;
	}//getType

	/**
	 * Returns the Type whose name matches the provided String. Return null if no
	 * such type exists.
	 * @param str	-the String to match
	 * @return	-the matching Type
	 */
	public static Type getType(String str) {
		for(Type t : Type.values()) {
			if(str.equals(t.getType()))
				return t;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.type;
	}//toString
}