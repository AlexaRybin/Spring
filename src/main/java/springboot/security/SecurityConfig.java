package springboot.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final SuccessUserHandler successUserHandler;



    public SecurityConfig(@Qualifier("userServiceImp") UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {//?!
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); //посмотри про passwordEncoder, возможно не взлетит
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("ADMIN").password("ADMIN").roles("ADMIN");
//    }

    //завтра начать от сюда
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.formLogin()
//                // указываем страницу с формой логина
//                .loginPage("/login")
//                //указываем логику обработки при логине
//                .successHandler(new LoginSuccessHandler())
//                // указываем action с формы логина
//                .loginProcessingUrl("/login")
//                // Указываем параметры логина и пароля с формы логина
//                .usernameParameter("j_username")
//                .passwordParameter("j_password")
//                // даем доступ к форме логина всем
//                .permitAll();
//
//        http.logout()
//                // разрешаем делать логаут всем
//                .permitAll()
//                // указываем URL логаута
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                // указываем URL при удачном логауте
//                .logoutSuccessUrl("/login?logout")
//                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
//                .and().csrf().disable();

        http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
//                .antMatchers("/registration").fullyAuthenticated()
//                .antMatchers("/").permitAll()

                //страницы аутентификаци доступна всем
                .antMatchers("/login").anonymous()

                // защищенные URL
                .antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/user/**").hasAnyRole("USER")

//                .anyRequest().authenticated();
                .and().formLogin()
                .successHandler(successUserHandler)
                .and().logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }
}
