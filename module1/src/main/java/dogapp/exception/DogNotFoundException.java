package dogapp.exception;

public class DogNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Dog not found";
    }
}
