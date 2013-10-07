package $defaultPackageName$.dao;

import java.util.List;

public interface GenericDAO<T> {

    public T get(T object);

    public void save(T object);

    public void save(final List<T> objects);

    public void delete(T object);

    public void delete(final List<T> objects);
}