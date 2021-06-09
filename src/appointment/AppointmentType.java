package appointment;

public enum AppointmentType {
    CONSULTATION("Consultaiton"),
	BRIEFING("Briefing"),
	ORIENTATION("Orientation"),
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