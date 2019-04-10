package dogapp.utils;

import dogapp.dto.Dog;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static io.qala.datagen.RandomDate.between;
import static io.qala.datagen.RandomShortApi.alphanumeric;
import static io.qala.datagen.RandomShortApi.positiveDouble;

public class DogTestUtils {

    public static Dog generateDog() {
        return Dog.builder().id(UUID.randomUUID()).name(alphanumeric(1, 100))
                .dateOfBirth(between(LocalDate.of(1970, 1, 1), LocalDate.now()).localDate())
                .height(positiveDouble()).weight(positiveDouble()).dogDetails(Collections.emptySet()).build();
    }
}
