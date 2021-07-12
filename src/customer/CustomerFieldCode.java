package customer;

/**
 * Identifies input fields on the form for adding or editing customers. Used for
 * determining which fields have errors or have been changed by the user.
 */
public enum CustomerFieldCode {
    NAME_FIELD,
    PHONE_FIELD,
    ADDRESS_FIELD,
    CITY_FIELD,
    POST_CODE_FIELD,
	COUNTRY_BOX,
	DIVISION_BOX;
}