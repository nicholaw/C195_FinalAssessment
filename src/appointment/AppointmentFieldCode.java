package appointment;

/**
 * Identifies input fields on the form for adding or editing appointments. Used for
 * determining which fields have errors and which fields have been changed by the
 * user.
 */
public enum AppointmentFieldCode {
	ID_FIELD,
	TITLE_FIELD,
	TYPE_COMBO,
	START_TIME,
	END_TIME,
	DESC_AREA,
	LOCATION_COMBO,
	CONTACT_COMBO
}
