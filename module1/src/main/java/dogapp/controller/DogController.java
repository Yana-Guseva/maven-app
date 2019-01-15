package dogapp.controller;

import dogapp.Dog;
import dogapp.exception.DogNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
//@RequestMapping(value = "dog")
public class DogController {

    private static final Map<UUID, Dog> dogs = new ConcurrentHashMap<>();

    private static final UUID ID = UUID.fromString("8535af21-545c-4a5e-a78a-60c07b5a399a");
    private static final Dog DOG = Dog.builder().id(ID)
            .name("Rema")
            .dateOfBirth(LocalDate.of(2018, 9, 7))
            .height(60.)
            .weight(17.)
            .build();

    static {
        dogs.put(ID, DOG);
    }

//    @PostMapping
//    public Dog createDog(@RequestBody Dog dog) {
//        dog.setId(UUID.randomUUID());
//        return dogs.put(dog.getId(), dog);
//    }

    @GetMapping("/dog/{id}")
    public Dog getDog(@PathVariable String id) {
        if (!dogs.containsKey(UUID.fromString(id))) {
            throw new DogNotFoundException();
        }
        return dogs.get(UUID.fromString(id));
    }

//    @PutMapping("{id}")
//    public Dog updateDog(@PathVariable UUID id, @RequestBody Dog dog) {
//        if (!dogs.containsKey(id)) {
//            throw new DogNotFoundException();
//        }
//        return dogs.put(id, dog);
//    }
//
//    @DeleteMapping("{id}")
//    public void deleteDog(@PathVariable UUID id) {
//        if (!dogs.containsKey(id)) {
//            throw new DogNotFoundException();
//        }
//        dogs.remove(id);
//    }
}
