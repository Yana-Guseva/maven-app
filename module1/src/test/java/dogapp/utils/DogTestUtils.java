package dogapp.utils;

import dogapp.dto.Dog;

import java.util.UUID;

import static io.qala.datagen.RandomDate.beforeNow;
import static io.qala.datagen.RandomShortApi.*;

public class DogTestUtils {

    public static Dog generateDog() {
        return Dog.builder().id(UUID.randomUUID()).name(alphanumeric(1, 100)).dateOfBirth(nullOr(beforeNow().localDate()))
                .height(positiveDouble()).weight(positiveDouble()).build();
    }
}
