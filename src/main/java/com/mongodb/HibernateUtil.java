package com.mongodb;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;

public final class HibernateUtil {
  private static final SessionFactory SESSION_FACTORY;

  static {
    Properties p = new Properties();
    p.put("hibernate.dialect", "com.mongodb.hibernate.dialect.MongoDialect");
    p.put("hibernate.connection.provider_class", "com.mongodb.hibernate.jdbc.MongoConnectionProvider");
    p.put("jakarta.persistence.jdbc.url", "mongodb://localhost:27017/mydb?replicaSet=rs0");
    p.put("hibernate.hbm2ddl.auto", "none");
    p.put("hibernate.show_sql", "true");

    StandardServiceRegistry registry =
        new StandardServiceRegistryBuilder().applySettings(p).build();
    Metadata metadata = new MetadataSources(registry)
        .addAnnotatedClass(com.mongodb.Book.class)
        .buildMetadata();
    SESSION_FACTORY = metadata.buildSessionFactory();
  }

  private HibernateUtil() {}

  public static SessionFactory getSessionFactory() {
    return SESSION_FACTORY;
  }
}
