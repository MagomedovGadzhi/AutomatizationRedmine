package automatization.redmine.model.project;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProjectStatus {
    CREATED(1),
    ARCH(9);

    Integer statusCode;
}