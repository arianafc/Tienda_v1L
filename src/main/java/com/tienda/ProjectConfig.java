//informacion de la internacionalizacion
package com.tienda;
//imports relacionados al springframework
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration //se usa porque esto es una configuracio, aqui se modificara cierta info de nuestro proyecto
public class ProjectConfig implements WebMvcConfigurer {
    /* Los siguientes métodos son para incorporar el tema de internacionalización en el proyecto */
    
    /* localeResolver se utiliza para crear una sesión de cambio de idioma*/
    //de aqui para abajo son para metodos para la internacionalizacion
    @Bean //permite extraer el objeto para poder modificandolo
    public LocaleResolver localeResolver() { //se usa para crear una sesion de cambio de idioma
        var slr = new SessionLocaleResolver(); //slr> session local resolver
        slr.setDefaultLocale(Locale.getDefault()); //busca la localizacion del usuario
        slr.setLocaleAttributeName("session.current.locale"); //se extrae la localizacion actual
        slr.setTimeZoneAttributeName("session.current.timezone"); //se extrae la zona horaria
        return slr;
    }

    /* localeChangeInterceptor se utiliza para crear un interceptor de cambio de idioma*/
    @Bean //se trae el interceptor, donde se obtiene la informacion para cambiar el idioma
    public LocaleChangeInterceptor localeChangeInterceptor() {
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    //si queremos agregar mas interceptors, es decir, mas informacion, hacer registro del de arriba
    @Override
    public void addInterceptors(InterceptorRegistry registro) {
        registro.addInterceptor(localeChangeInterceptor());
    }

    //Bean para poder acceder a los Messages.properties en código...
    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource= new ResourceBundleMessageSource(); //se extrae cierta logica de la libreria
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8"); //formato de caracteres
        return messageSource; //retorna las letras dependiendo del timezone de la persona
    }
    
    /* Los siguiente métodos son para implementar el tema de seguridad dentro del proyecto */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registro/nuevo").setViewName("/registro/nuevo");
 }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                .requestMatchers("/","/index","/errores/**",
                        "/carrito/**","/pruebas/**","/reportes/**",
                        "/registro/**","/js/**","/webjars/**")
                        .permitAll()
                .requestMatchers(
                        "/producto/nuevo","/producto/guardar",
                        "/producto/modificar/**","/producto/eliminar/**",
                        "/categoria/nuevo","/categoria/guardar",
                        "/categoria/modificar/**","/categoria/eliminar/**",
                        "/usuario/nuevo","/usuario/guardar",
                        "/usuario/modificar/**","/usuario/eliminar/**",
                        "/reportes/**"
                ).hasRole("ADMIN")
                .requestMatchers(
                        "/producto/listado",
                        "/categoria/listado",
                        "/usuario/listado"
                ).hasAnyRole("ADMIN", "VENDEDOR")
                .requestMatchers("/facturar/carrito")
                .hasRole("USER")
                )
                .formLogin((form) -> form
                .loginPage("/login").permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

/* El siguiente método se utiliza para completar la clase no es 
    realmente funcional, la próxima semana se reemplaza con usuarios de BD */    
    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.builder()
                .username("juan")
                .password("{noop}123")
                .roles("USER", "VENDEDOR", "ADMIN")
                .build();
        UserDetails sales = User.builder()
                .username("rebeca")
                .password("{noop}456")
                .roles("USER", "VENDEDOR")
                .build();
        UserDetails user = User.builder()
                .username("pedro")
                .password("{noop}789")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user, sales, admin);
    }
    
    
    
    
    
    
}