package sceneUtils;

public enum ErrorMessage {
    LOGIN_CREDENTIAL_ERROR("credential_error"),
    LOGIN_PASSWORD_ERROR("password_error"),
    LOGIN_USERNAME_ERROR("username_error");

    private final String localeKey;

    ErrorMessage(String localeKey) {
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
