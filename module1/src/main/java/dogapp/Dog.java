package dogapp;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class Dog {
    private UUID id;
    private String name;
    private Date dateOfBirth;
    private Double height;
    private Double weight;
}
