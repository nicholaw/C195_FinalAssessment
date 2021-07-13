package sceneUtils;

/**
 * Codes for each error which can be encountered when validating forms. Each code
 * also specifies the locale key for the error message to be displayed when that
 * error is encountered.
 */
public enum ErrorCode {
    APPOINTMENT_TITLE_REQUIRED_ERROR("title_error"),
    APPOINTMENT_DESC_REQUIRED_ERROR("description_error"),
    APPOINTMENT_BUSINESS_HOURS_ERROR("hours_error"),
    APPOINTMENT_START_END_ERROR("start_end_error"),
    APPOINTMENT_OVERLAPS_EXISTING_ERROR("overlaps_error"),
    CUSTOMER_NAME_REQUIRED_ERROR("name_error"),
    CUSTOMER_PHONE_REQUIRED_ERROR("phone_error1"),
    CUSTOMER_PHONE_DIGITS_ERROR("phone_error2"),
    CUSTOMER_ADDRESS_REQUIRED_ERROR("address_error"),
    CUSTOMER_POSTCODE_REQUIRED_ERROR("post_code_error1"),
    CUSTOMER_POSTCODE_DIGITS_ERROR("post_code_error2"),
    LOGIN_INVALID_CREDENTIAL_ERROR("credential_error"),
    LOGIN_PASSWORD_REQUIRED_ERROR("password_error"),
    LOGIN_USERNAME_REQUIRED_ERROR("username_error");

    private final String localeKey;

    /**
     * Constructor for this object
     * @param localeKey -the error code's locale key
     */
    ErrorCode(String localeKey) {
        this.localeKey = localeKey;
    }

    /**
     * Returns the locale key for this error code.
     * @return -the locale key
     */
    public String getLocaleKey() {
        return localeKey;
    }

    @Override
    public String toString() {
        return localeKey;
    }
}
