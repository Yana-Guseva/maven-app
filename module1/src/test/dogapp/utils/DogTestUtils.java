package dogapp.utils;

import dogapp.dto.Dog;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DogTestUtils {
    public static Dog generateDog() {
        long minDate = LocalDate.MIN.getLong(ChronoField.EPOCH_DAY);
        long maxDate = LocalDate.now().getLong(ChronoField.EPOCH_DAY);
        long randomDay = ThreadLocalRandom.current().nextLong(minDate, maxDate);

        return Dog.builder().id(UUID.randomUUID()).name("name" + ThreadLocalRandom.current().nextInt())
                .dateOfBirth(LocalDate.ofEpochDay(randomDay)).height(55 + ThreadLocalRandom.current().nextDouble())
                .weight(23 + ThreadLocalRandom.current().nextDouble()).build();
    }
}
