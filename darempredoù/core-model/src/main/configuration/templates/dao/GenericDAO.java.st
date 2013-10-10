package $defaultPackageName$.dao;

import java.util.List;

public interface GenericDAO<PK, E> {

    public E get(PK primaryKey);

    public void save(E entity);

    public void save(final List<E> entities);

    public void delete(E entity);

    public void delete(final List<E> entities);
}