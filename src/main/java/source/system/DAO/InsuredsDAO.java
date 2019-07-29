package source.system.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import source.system.model.Insureds;
import source.system.utils.HibernateSession;

import java.util.List;



@Repository
public class InsuredsDAO {

    public List<Insureds> findAll() {
        String hql = "FROM source.system.model.Insureds";
        return (List<Insureds>) HibernateSession.getSessionFactory().openSession().createQuery(hql).list();
    }

    public List<Insureds> findInsureds(String firstName, String lastName, String middleName) {
        Session session = HibernateSession.getSessionFactory().openSession();
        String q= "FROM source.system.model.Insureds  ins where ins.firsName like concat('%', :firstName,'%') and ins.lastName like concat('%', :lastName,'%') and ins.middleName like concat('%', :middleName,'%')";
        Query query=session.createQuery(q);
        query.setParameter("firstName",firstName);
        query.setParameter("lastName",lastName);
        query.setParameter("middleName",middleName);
        return query.list();

    }


}
