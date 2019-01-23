package dogapp.controller;

import dogapp.dto.Dog;
import dogapp.exception.DogNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "dog")
public class DogController {

    private static final Map<UUID, Dog> dogs = new ConcurrentHashMap<>();

    private static final UUID ID = UUID.fromString("8535af21-545c-4a5e-a78a-60c07b5a399a");
    private static final Dog DOG = Dog.builder().id(ID).name("Rema")
            .dateOfBirth(LocalDate.of(2018, 9, 7)).height(60.).weight(17.).build();

    static {
        dogs.put(ID, DOG);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog createDog(@Valid @RequestBody Dog dog) {
        dog.setId(UUID.randomUUID());
        dogs.put(dog.getId(), dog);
        return dog;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog getDog(@PathVariable UUID id) {
        if (!dogs.containsKey(id)) {
            throw new DogNotFoundException();
        }
        return dogs.get(id);
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog updateDog(@PathVariable UUID id, @Valid @RequestBody Dog dog) {
        if (!dogs.containsKey(id)) {
            throw new DogNotFoundException();
        }
        dog.setId(id);
        dogs.put(dog.getId(), dog);
        return dog;
    }

    @DeleteMapping("{id}")
    public void deleteDog(@PathVariable UUID id) {
        if (!dogs.containsKey(id)) {
            throw new DogNotFoundException();
        }
        dogs.remove(id);
    }
}
