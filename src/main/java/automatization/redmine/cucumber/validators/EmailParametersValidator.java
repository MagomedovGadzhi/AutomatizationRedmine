package automatization.redmine.cucumber.validators;

import java.util.Arrays;
import java.util.List;

public class EmailParametersValidator {
    public static void validateEmailParameters(List<String> keys) {
        List<String> allowedKeys = Arrays.asList("Адрес", "По умолчанию", "Уведомления");

        boolean allKeysAreValid = allowedKeys.containsAll(keys);
        if (!allKeysAreValid) {
            throw new IllegalArgumentException("Среди переданных параметров пользователя есть недопустимые параметры");
        }
    }
}