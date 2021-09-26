package automatization.redmine.model.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimeEntriesVisibility {
    All("Все трудозатраты"),
    OWN("Только собственные трудозатраты");

    String description;
}