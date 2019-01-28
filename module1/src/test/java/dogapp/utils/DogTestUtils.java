package dogapp.utils;

import dogapp.dto.Dog;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DogTestUtils {

    public static final Dog DOG = Dog.builder().id(UUID.randomUUID()).name("Belka").dateOfBirth(LocalDate.of(1955, 7, 7))
            .height(47.).weight(13.).build();

    public static Dog generateDog() {
        long minDate = LocalDate.MIN.getLong(ChronoField.EPOCH_DAY);
        long maxDate = LocalDate.now().getLong(ChronoField.EPOCH_DAY);
        long randomDay = ThreadLocalRandom.current().nextLong(minDate, maxDate);

        return Dog.builder().id(UUID.randomUUID()).name("name" + ThreadLocalRandom.current().nextInt())
                .dateOfBirth(LocalDate.ofEpochDay(randomDay)).height(ThreadLocalRandom.current().nextDouble(20., 60.))
                .weight(ThreadLocalRandom.current().nextDouble(5., 60.)).build();
    }
}
