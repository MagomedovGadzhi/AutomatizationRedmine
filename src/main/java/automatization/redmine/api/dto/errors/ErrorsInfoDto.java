package automatization.redmine.api.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ErrorsInfoDto {
    List<String> errors;
}