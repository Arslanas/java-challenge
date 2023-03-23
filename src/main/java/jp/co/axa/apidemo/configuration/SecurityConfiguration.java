package jp.co.axa.apidemo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Profile("!test")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(User.withUsername("demo")
                        .password(passwordEncoder().encode("123"))
                        .roles("ADMIN"));
    }

    @Override
    public void configure(HttpSecurity httpSecurity)
            throws Exception {
        String[] publicPaths = new String[]{
                "/h2-console/**",
                "/swagger-ui.html"
        };

        // force authentication for all endpoints except public
        httpSecurity.authorizeRequests()
                .antMatchers(publicPaths)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

        httpSecurity.csrf().disable(); // disable for demo purpose

        httpSecurity.headers()
                .frameOptions()
                .sameOrigin();

        // configure basic authentication
        httpSecurity.httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
