package dogapp.controller;

import dogapp.dao.DogDao;
import dogapp.dto.Dog;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "dog")
public class DogController {

    private final DogDao dogDao;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog createDog(@Valid @RequestBody Dog dog) {
        return dogDao.create(dog);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog getDog(@PathVariable UUID id) {
        return dogDao.get(id);
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog updateDog(@PathVariable UUID id, @Valid @RequestBody Dog dog) {
        dog.setId(id);
        return dogDao.update(dog);
    }

    @DeleteMapping("{id}")
    public void deleteDog(@PathVariable UUID id) {
        dogDao.delete(id);
    }
}
