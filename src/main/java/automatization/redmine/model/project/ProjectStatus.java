package automatization.redmine.model.project;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum ProjectStatus {
    CREATED(1),
    ARCH(9);

    public final int statusCode;

    public static ProjectStatus getProjectStatusByCode(int code) {
        return Stream.of(values())
                .filter(projectStatus -> projectStatus.statusCode == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден объект enum ProjectStatus"));
    }
}