package dogapp.dao;

import dogapp.dto.Dog;
import dogapp.exception.DogNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;

import java.util.UUID;

@AllArgsConstructor
public class HibernateDogDao implements DogDao {

    private final SessionFactory sessionFactory;

    @Override
    public Dog get(UUID id) {
        Dog dog = sessionFactory.getCurrentSession().get(Dog.class, id);
        if (dog == null) {
            throw new DogNotFoundException(id);
        }
        return dog;
    }

    @Override
    public Dog create(Dog dog) {
        sessionFactory.getCurrentSession().save(dog);
        return dog;
    }

    @Override
    public Dog update(Dog dog) {
        Dog oldDog = sessionFactory.getCurrentSession().get(Dog.class, dog.getId());
        if (oldDog == null) {
            throw new DogNotFoundException(dog.getId());
        }
        sessionFactory.getCurrentSession().detach(oldDog);
        sessionFactory.getCurrentSession().update(dog);
        return dog;
    }

    @Override
    public void delete(UUID id) {
        Dog dog = sessionFactory.getCurrentSession().get(Dog.class, id);
        if (dog == null) {
            throw new DogNotFoundException(id);
        }
        sessionFactory.getCurrentSession().delete(dog);
    }
}
