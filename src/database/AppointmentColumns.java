package database;

/**
 *  Names of each of the columns in the Appointments table
 */
public enum AppointmentColumns {
    APPOINTMENT_ID("Appointment_ID"),
    APPOINTMENT_TITLE("Title"),
    APPOINTMENT_DESCRIPTION("Description"),
    APPOINTMENT_LOCATION("Location"),
    APPOINTMENT_TYPE("Type"),
    APPOINTMENT_START("Start"),
    APPOINTMENT_END("End"),
    APPOINTMENT_CREATE_DATE("Create_Date"),
    APPOINTMENT_CREATED_BY("Created_By"),
    APPOINTMENT_UPDATE_DATE("Last_Update"),
    APPOINTMENT_UPDATED_BY("Last_Updated_By"),
    APPOINTMENT_CUSTOMER_ID("Customer_ID"),
    APPOINTMENT_USER_ID("User_ID"),
    APPOINTMENT_CONTACT_ID("Contact_ID");

    private final String colName;

    AppointmentColumns(String colName) {
        this.colName = colName;
    }

    public String getColName() {
        return this.colName;
    }

    @Override
    public String toString() {
        return this.colName;
    }
}