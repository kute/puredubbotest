package com.kute.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * created by kute on 2018/09/21 11:43
 */
@Configuration
@PropertySource("classpath:dubbo.properties")
public class DubboConfig {

    @Autowired
    private Environment env;

    // application
    @Value("${dubbo.application.name:}")
    private String name;
    @Value("${dubbo.application.version:}")
    private String version;
    @Value("${dubbo.application.owner:}")
    private String owner;
    @Value("${dubbo.application.organization:}")
    private String organization;
    @Value("${dubbo.application.architecture:}")
    private String architecture;
    @Value("${dubbo.application.environment:test}")
    private String environment;

    @Value("${dubbo.registry.address}")
    private String address;
    @Value("${dubbo.registry.protocol:dubbo}")
    private String protocol;
    @Value("${dubbo.registry.username}")
    private String username;
    @Value("${dubbo.registry.password}")
    private String password;
    @Value("${dubbo.registry.group}")
    private String group;
    @Value("${dubbo.registry.version}")
    private String rversion;
    @Value("${dubbo.registry.timeout:3000}")
    private Integer timeout;
    @Value("${dubbo.registry.file}")
    private String file;

    @Bean("dubboApplicationConfig")
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig(name);
        applicationConfig.setVersion(version);
        applicationConfig.setOwner(owner);
        applicationConfig.setOrganization(organization);
        applicationConfig.setArchitecture(architecture);
        applicationConfig.setEnvironment(environment);
        return applicationConfig;
    }

    @Bean("dubboRegistryConfig")
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig(address);
        registryConfig.setProtocol(protocol);
        registryConfig.setUsername(username);
        registryConfig.setPassword(password);
        registryConfig.setGroup(group);
        registryConfig.setVersion(rversion);
        registryConfig.setTimeout(timeout);
        registryConfig.setFile(file);
        return registryConfig;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
