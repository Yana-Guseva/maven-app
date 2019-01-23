package dogapp.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class Dog {
    private UUID id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Past
    private LocalDate dateOfBirth;

    @NotNull
    @Positive
    private Double height;

    @NotNull
    @Positive
    private Double weight;
}
