package ru.cwl.otus.hw10.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.cwl.otus.hw10.model.AddressDataSet;
import ru.cwl.otus.hw10.model.PhoneDataSet;
import ru.cwl.otus.hw10.model.UserDataSet;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 12:06.
 */
public class SF {
    static SessionFactory getSessionFactory() {


        Configuration configuration = new Configuration();


        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:~/db_hw10;AUTO_SERVER=TRUE");
        configuration.setProperty("hibernate.connection.username", "sa");
        // configuration.setProperty("hibernate.show_sql", "true");

        //configuration.setProperty("hibernate.use_sql_comments","true");
        //configuration.setProperty("hibernate.format_sql","true");

        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        //configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

}
