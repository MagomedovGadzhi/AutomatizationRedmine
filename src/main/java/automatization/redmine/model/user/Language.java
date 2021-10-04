package automatization.redmine.model.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Language {
    RUSSIAN("ru"),
    ENGLISH("en");

    public final String languageCode;

    public static Language getLanguageFromCode(String languageCode) {
        switch (languageCode) {
            case "ru":
                return RUSSIAN;
            case "en":
                return ENGLISH;
        }
        throw new IllegalArgumentException("Указанного кода статуса не существует.");
    }
}