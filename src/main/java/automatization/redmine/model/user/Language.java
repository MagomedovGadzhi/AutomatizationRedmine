package automatization.redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum Language {
    RUSSIAN("ru"),
    ENGLISH("en");

    public final String languageCode;

    public static Language getLanguageByCode(String code) {
        return Stream.of(values())
                .filter(language -> language.languageCode.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден объект enum Language"));
    }
}