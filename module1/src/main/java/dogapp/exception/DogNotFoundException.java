package dogapp.exception;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DogNotFoundException extends RuntimeException {
    private UUID id;

    @Override
    public String getMessage() {
        return "Dog not found " + id;
    }
}
