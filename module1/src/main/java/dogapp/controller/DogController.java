package dogapp.controller;

import dogapp.dto.Dog;
import dogapp.service.TransactionalDogService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "dog")
public class DogController {

    private final TransactionalDogService dogService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog createDog(@Valid @RequestBody Dog dog) {
        return dogService.createDog(dog);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dog getDog(@PathVariable UUID id) {
        return dogService.getDog(id);
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dog updateDog(@PathVariable UUID id, @Valid @RequestBody Dog dog) {
        dog.setId(id);
        return dogService.updateDog(dog);
    }

    @DeleteMapping("{id}")
    public void deleteDog(@PathVariable UUID id) {
        dogService.deleteDog(id);
    }
}
