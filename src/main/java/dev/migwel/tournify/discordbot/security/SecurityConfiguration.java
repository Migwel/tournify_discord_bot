package dev.migwel.tournify.discordbot.security;

import dev.migwel.tournify.discordbot.properties.NotificationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final static Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    private NotificationProperties notificationProperties;

    public SecurityConfiguration(NotificationProperties notificationProperties) {
        super();
        this.notificationProperties = notificationProperties;
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder authBuilder) {
        try {
            authBuilder.inMemoryAuthentication()
                    .withUser(notificationProperties.getUsername())
                    .password(notificationProperties.getPassword())
                    .roles("REMOTE");
        } catch (Exception e) {
            log.error("Could not configure authentication", e);
        }
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/notification/**").hasRole("REMOTE").anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
