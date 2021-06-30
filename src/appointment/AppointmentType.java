package appointment;

public enum AppointmentType {
	BRIEFING("Briefing"),
    CONSULTATION("Consultation"),
	DEBRIEFING("Debriefing"),
	ORIENTATION("Orientation"),
	PLANNING("Planning"),
	UPDATE("Update"),
    OTHER("Other");
	
	private final String type;
	
	AppointmentType(String type) {
		this.type = type;
	}//constructor
	
	public String getType() {
		return this.type;
	}//getType
	
	@Override
	public String toString() {
		return this.type;
	}//toString
}