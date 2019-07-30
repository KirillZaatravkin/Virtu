package source.system.DAO;


import org.springframework.stereotype.Repository;
import source.system.model.Contract;
import source.system.utils.HibernateSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
@Repository
public class ContractDAO {

    public void save(Contract contract){
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(contract);
        tx1.commit();
        session.close();
    }

    public void update(Contract contract) {
        Session session = HibernateSession.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(contract);
        tx1.commit();
        session.close();
    }

    public List<Contract> findAll() {
        String hql = "FROM source.system.model.Contract";
        return (List<Contract>) HibernateSession.getSessionFactory().openSession().createQuery(hql).list();
    }

    public Contract findId(int id) {
        Session session = HibernateSession.getSessionFactory().openSession();
        return session.get(Contract.class,id);
    }
}
