package automatization.redmine.cucumber.validators;

import java.util.Arrays;
import java.util.List;

public class TokenParametersValidator {
    public static void validateTokenParameters(List<String> keys) {
        List<String> allowedKeys = Arrays.asList("Тип", "Значение");

        boolean allKeysAreValid = allowedKeys.containsAll(keys);
        if (!allKeysAreValid) {
            throw new IllegalArgumentException("Среди переданных параметров пользователя есть недопустимые параметры");
        }
    }
}