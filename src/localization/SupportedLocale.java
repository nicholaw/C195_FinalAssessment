package localization;

import java.util.Locale;

public enum SupportedLocale {
    LOCALE_ENGLISH(Locale.ENGLISH, "en"),
    LOCALE_FRENCH(Locale.FRENCH, "fr");

    private final Locale locale;
    private final String languageCode;

    SupportedLocale(Locale locale, String code) {
        this.locale = locale;
        this.languageCode = code;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public String toString() {
        return languageCode;
    }
}