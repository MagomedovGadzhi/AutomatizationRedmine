package automatization.redmine.model.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimeEntriesVisibility {
    ALL("Все трудозатраты", "all"),
    OWN("Только собственные трудозатраты", "own");

    private final String description;
    public final String timeEntriesVisibilityCode;

}