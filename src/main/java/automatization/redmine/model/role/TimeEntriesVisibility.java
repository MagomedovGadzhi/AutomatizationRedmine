package automatization.redmine.model.role;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum TimeEntriesVisibility {
    ALL("Все трудозатраты", "all"),
    OWN("Только собственные трудозатраты", "own");

    private final String description;
    public final String timeEntriesVisibilityCode;

    public static TimeEntriesVisibility getTimeEntriesVisibilityByCode(String code) {
        return Stream.of(values())
                .filter(timeEntriesVisibility -> timeEntriesVisibility.timeEntriesVisibilityCode.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден объект enum TimeEntriesVisibility"));
    }
}