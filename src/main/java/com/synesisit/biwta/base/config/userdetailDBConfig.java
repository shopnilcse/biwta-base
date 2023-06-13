package com.synesisit.biwta.base.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", basePackages = {"com.synesisit.biwta.base.repository"}, transactionManagerRef = "transactionManager")
public class userdetailDBConfig {

    @Autowired
    private Environment env;

//    @Primary
//    @Bean(name = "datasource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource(){
//        DataSource ds = DataSourceBuilder.create().build();
//        if (ds instanceof org.apache.tomcat.jdbc.pool.DataSource) {
//            ((org.apache.tomcat.jdbc.pool.DataSource) ds).setInitialSize(1);
//            ((org.apache.tomcat.jdbc.pool.DataSource) ds).setMaxActive(2);
//            ((org.apache.tomcat.jdbc.pool.DataSource) ds).setMaxAge(1000);
//            ((org.apache.tomcat.jdbc.pool.DataSource) ds).setMinIdle(0);
//            ((org.apache.tomcat.jdbc.pool.DataSource) ds).setMinEvictableIdleTimeMillis(60000);
//        } else {
//            // warnings or exceptions, whatever you prefer
//        }
//        return ds;
//    }


    @Primary
    @Bean(name = "datasource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setInitialSize(5);

        String initSize = env.getProperty("spring.datasource.dbcp2.initial-size");
        if(initSize != null) dataSource.setInitialSize(Integer.parseInt(initSize)); // else initSize = "0"; // default value

        String maxTotal = env.getProperty("spring.datasource.dbcp2.max-total");
        if(maxTotal != null)dataSource.setMaxTotal(Integer.parseInt(maxTotal)); // else  maxTotal = "8"; // default value

        String minIdle = env.getProperty("spring.datasource.dbcp2.min-idle");
        if(minIdle != null)dataSource.setMinIdle(Integer.parseInt(minIdle));// else minIdle = "0"; // default value

        String maxIdle = env.getProperty("spring.datasource.dbcp2.max-idle");
        if(maxIdle != null)dataSource.setMaxIdle(Integer.parseInt(maxIdle));// else maxIdle = "8"; // default value

        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("select 1");
        dataSource.setValidationQueryTimeout(10); //The value is in seconds

        dataSource.setTimeBetweenEvictionRunsMillis(10*60*1000); // 10 minutes wait to run evictor process
        dataSource.setSoftMinEvictableIdleTimeMillis(10*60*1000); // 10 minutes wait to run evictor process
        dataSource.setMinEvictableIdleTimeMillis(60*60*1000);   // 1 Hour to wait before idle connection is evicted
        dataSource.setSoftMinEvictableIdleTimeMillis(10*60*1000); // 10 minutes is max life time

        return dataSource;

    }




    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("datasource") DataSource dataSource){
        Map<String, Object> properties= new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
        return builder.dataSource(dataSource).properties(properties).packages("com.synesisit.commonmodule.acl.model").persistenceUnit("userdetails").build();
    }


    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
