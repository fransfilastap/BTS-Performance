/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Configuration
@ComponentScan({})
@PropertySource(value={"classpath:application.properties"})
public class DatasourceConfiguration {
    
    @Autowired
    private Environment env;
    
    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName( env.getRequiredProperty("jdbc.driverClassName") );
        dataSource.setUrl( env.getRequiredProperty("jdbc.url") );
        dataSource.setUsername( env.getRequiredProperty("jdbc.username") );
        dataSource.setPassword( env.getRequiredProperty("jdbc.password") );
        
        return dataSource;
    }
    
}
