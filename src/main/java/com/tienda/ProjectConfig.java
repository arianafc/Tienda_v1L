package com.tienda;
//imports relacionados al springframework
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
}