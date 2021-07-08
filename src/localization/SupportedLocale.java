package localization;

import java.util.Locale;

public enum SupportedLocale {
    LOCALE_ENGLISH(Locale.ENGLISH),
    LOCALE_FRENCH(Locale.FRENCH);

    private final Locale locale;

    SupportedLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return locale.getDisplayLanguage();
    }
}