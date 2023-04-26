package com.licenta2023.backend.repository.RepositoryImplementations;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryClass {
    private static SessionFactory sessionFactory;

    public SessionFactoryClass() {}

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    public static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory==null){
            initialize();
        }
        return sessionFactory;
    }
}
