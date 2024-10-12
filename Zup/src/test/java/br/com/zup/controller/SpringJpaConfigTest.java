package br.com.zup.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import br.com.zup.config.SpringJpaConfig;

public class SpringJpaConfigTest {

    private SpringJpaConfig springJpaConfig;

    @BeforeEach
    public void setUp() {
        springJpaConfig = new SpringJpaConfig();
    }

    @Test
    public void testTransactionManager() {
        // Arrange
        EntityManagerFactory mockEntityManagerFactory = mock(EntityManagerFactory.class);

        // Act
        JpaTransactionManager transactionManager = springJpaConfig.transactionManager(mockEntityManagerFactory);

        // Assert
        assertNotNull(transactionManager);
        assertEquals(mockEntityManagerFactory, transactionManager.getEntityManagerFactory());
    }

    @Test
    public void testJpaProperties() {
        // Act
        Properties jpaProperties = springJpaConfig.jpaProperties();

        // Assert
        assertNotNull(jpaProperties);
        assertEquals("false", jpaProperties.getProperty("hibernate.show_sql"));
        assertEquals("false", jpaProperties.getProperty("hibernate.format_sql"));
        assertEquals("update", jpaProperties.getProperty("hibernate.hbm2ddl.auto"));
        assertEquals("org.hibernate.dialect.MySQL5Dialect", jpaProperties.getProperty("hibernate.dialect"));
    }
}
