package source.system.DAO;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import source.system.model.Property;
import source.system.utils.HibernateSession;
import java.util.List;


@Repository
public class PropertyDAO {

    public List<Property> findAll() {
        String hql = "FROM source.system.model.Property";
        return (List<Property>) HibernateSession.getSessionFactory().openSession().createQuery(hql).list();
    }

    public Property findId(int id) {
        Session session = HibernateSession.getSessionFactory().openSession();
        return session.get(Property.class,id);

    }

}
