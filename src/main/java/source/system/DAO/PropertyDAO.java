package source.system.DAO;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import source.system.model.Property;
import source.system.utils.HibernateSession;

import java.util.List;
import java.util.Queue;

@Repository
public class PropertyDAO {

    public List<Property> findAll() {
        String hql = "FROM source.system.model.Property";
        return (List<Property>) HibernateSession.getSessionFactory().openSession().createQuery(hql).list();
    }

}
