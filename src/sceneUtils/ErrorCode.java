package sceneUtils;

public enum ErrorCode {
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

    ErrorCode(String localeKey) {
        this.localeKey = localeKey;
    }

    public String getLocaleKey() {
        return localeKey;
    }

    @Override
    public String toString() {
        return localeKey;
    }
}
