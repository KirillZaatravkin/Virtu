package source.system.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import source.system.model.Contract;
import source.system.model.Insureds;
import source.system.model.Property;


public class HibernateSession {
    private static SessionFactory sessionFactory;
    private HibernateSession() {

    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Contract.class);
                configuration.addAnnotatedClass(Insureds.class);
                configuration.addAnnotatedClass(Property.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                //sessionFactory = configuration.buildSessionFactory(builder.build());
                sessionFactory = configuration.configure("hibernate.cfg.xml").buildSessionFactory();
            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}
