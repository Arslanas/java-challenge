package jp.co.axa.apidemo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Mock security configuration to disable it during API tests
 */
@Configuration
@Profile("test")
public class SecurityConfigurationTest extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(HttpSecurity httpSecurity)
            throws Exception {
        // force authentication for all endpoints except public
        httpSecurity.authorizeRequests().antMatchers("/**").permitAll();
        httpSecurity.csrf().disable();
    }
}
