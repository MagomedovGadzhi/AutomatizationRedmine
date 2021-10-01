package automatization.redmine.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public abstract class Entity {

    protected Integer id;

}