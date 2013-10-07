package $defaultPackageName$.dao;

import java.util.List;
import org.hibernate.criterion.*;
import org.hibernate.*;

public abstract class GenericDAOImpl<T> {

    public T get(T object) {

        List<T> list = find(object);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public void save(T object) {
        getCurrentSession().saveOrUpdate(object);
    }

    public void save(final List<T> objects) {
        for (T object : objects) {
            getCurrentSession().saveOrUpdate(object);
        }
    }

    public void delete(T object) {
        getCurrentSession().delete(object);
    }

    public void delete(final List<T> objects) {
        for (T object : objects) {
            getCurrentSession().delete(object);
        }
    }

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}