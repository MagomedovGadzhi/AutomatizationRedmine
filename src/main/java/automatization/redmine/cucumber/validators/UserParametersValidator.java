package automatization.redmine.cucumber.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class UserParametersValidator {

    public static void validateUserParameters(Set<String> keys) {
        List<String> allowedKeys = Arrays.asList("Администратор", "Статус", "Уведомления о новых событиях", "E-Mail");

        boolean allKeysAreValid = allowedKeys.containsAll(keys);
        if (!allKeysAreValid) {
            throw new IllegalArgumentException("Среди переданных параметров пользователя есть недопустимые параметры");
        }
    }
}
