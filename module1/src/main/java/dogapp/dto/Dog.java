package dogapp.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Dog {
    private UUID id;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 100, message = "size must be between 1 and 100")
    private String name;

    @Past(message = "must be a past date")
    private LocalDate dateOfBirth;

    @NotNull(message = "must not be null")
    @Positive(message = "must be greater than 0")
    private Double height;

    @NotNull(message = "must not be null")
    @Positive(message = "must be greater than 0")
    private Double weight;
}
