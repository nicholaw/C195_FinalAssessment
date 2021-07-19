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
     * Returns the SupportedLocale matching the provided language code if such a
     * SupportedLocale exists. Otherwise returns Locale for English.
     * @param code -the language code to match
     * @return -the matching locale
     */
    public static SupportedLocale contains(String code) {
        for(SupportedLocale sl : SupportedLocale.values()) {
            if(sl.languageCode.equals(code))
                return sl;
        }
        return SupportedLocale.LOCALE_ENGLISH;
    }//contains

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