package localization;

import java.util.Locale;

/**
 * Represents a Locale supported by this application.
 */
public enum SupportedLocale {
    LOCALE_ENGLISH(Locale.ENGLISH, "en"),
    LOCALE_FRENCH(Locale.FRENCH, "fr");

    private final Locale locale;
    private final String languageCode;

    /**
     * Constructs a locale by setting its locale and the String
     * representation of its language code.
     * @param locale
     * @param code
     */
    SupportedLocale(Locale locale, String code) {
        this.locale = locale;
        this.languageCode = code;
    }

    /**
     * Returns the supported locale.
     * @return  -the supported locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Returns the String language code of this supported locale.
     * @return -the language code
     */
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public String toString() {
        return languageCode;
    }
}