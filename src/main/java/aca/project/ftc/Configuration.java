package aca.project.ftc;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;

public class Configuration {
    EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        return entityManagerFactoryBean.getNativeEntityManagerFactory();
    }


}

