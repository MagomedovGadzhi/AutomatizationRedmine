package automatization.redmine.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public abstract class CreatableEntity extends Entity {

    protected LocalDateTime createdOn = LocalDateTime.now();
    protected LocalDateTime updatedOn = LocalDateTime.now();

}