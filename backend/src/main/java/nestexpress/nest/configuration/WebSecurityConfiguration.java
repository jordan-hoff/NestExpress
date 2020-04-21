package nestexpress.nest.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
            // No HttpSession is ever created.
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .httpBasic()
        .and()
            .authorizeRequests()
                // Sets permissions for each web page.
                .antMatchers("/signup/*")    .permitAll()
                .antMatchers("/signin/*")    .permitAll()
                .antMatchers("/home/*")      .permitAll()
                .antMatchers("/cart/*")      .permitAll()
                .antMatchers("/checkout/*")  .permitAll()
                .antMatchers("/confirmation").permitAll()
                .antMatchers("/error")       .permitAll()
                .antMatchers("/product")     .permitAll()
                .antMatchers("/orders")      .permitAll()
                .antMatchers("/results")     .permitAll()
                .antMatchers("/userAccount") .permitAll()
                .antMatchers("/wishilist")   .permitAll()
        .and()
            // Cross Site Request Forgery is disabled.
            .csrf().disable();
    }
}
