package org.bruno.mySimpleORM.services;

import org.bruno.mySimpleORM.entities.Address;
import org.bruno.mySimpleORM.entities.ItMan;
import org.bruno.mySimpleORM.entities.Phone;
import org.bruno.mySimpleORM.interfaces.DBService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.function.Function;

public class HibernateDBServiceImpl implements DBService {

    private final SessionFactory sessionFactory;

    public HibernateDBServiceImpl() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(ItMan.class);
        configuration.addAnnotatedClass(Phone.class);
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql:myDB");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "argon");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public void save(Object object) {
        /*try (Session session = sessionFactory.openSession()) {
            session.save(object);
        }*/
        runInSession(session -> {
            session.save(object);
            return true;
        });
    }

    @Override
    public Object read(Class clazz, String condition) {
        long a = Long.getLong(condition);
        return runInSession(session -> {
            return session.load(clazz, a);
        });
    }

    @Override
    public List<Object> readAll(Class clazz) {
        /*return runInSession(session -> {
            return session.
        })*/
        return null;
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

}
