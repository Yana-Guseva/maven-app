package dogapp.dao;

import java.util.UUID;

public interface Dao<T> {
    T get(UUID id);

    T create(T t);

    T update(UUID id, T t);

    void delete(UUID id);
}
