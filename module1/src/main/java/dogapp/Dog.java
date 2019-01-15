package dogapp;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class Dog {
    private UUID id;
    private String name;
    private LocalDate dateOfBirth;
    private Double height;
    private Double weight;
}
