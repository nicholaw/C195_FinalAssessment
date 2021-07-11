package utils;

public enum Type {
	BRIEFING("Briefing"),
    CONSULTATION("Consultation"),
	DEBRIEFING("Debriefing"),
	ORIENTATION("Orientation"),
	PLANNING("Planning"),
	UPDATE("Update"),
    OTHER("Other");
	
	private final String type;
	
	Type(String type) {
		this.type = type;
	}//constructor
	
	public String getType() {
		return this.type;
	}//getType

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