package ee.example.configuration;

import ee.example.converter.ServiceInfoConverter;
import ee.example.converter.rules.*;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author Vahur Kaar
 * @since 25.03.2015.
 */
@Configuration
@PropertySource(value = "classpath:application.properties")
@ComponentScan(basePackages = {
        "ee.example.controller",
        "ee.example.service",
        "ee.example.converter"
})
@EnableWebMvc
@EnableScheduling
public class AppConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.forLanguageTag("et"));

        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");

        return localeChangeInterceptor;
    }

    @Bean
    public MappingJackson2JsonView viewResolver() {
        MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
        mappingJackson2JsonView.setPrettyPrint(true);

        return mappingJackson2JsonView;
    }

    @Bean
    public ServiceInfoConverter serviceInfoConverter() {
        ServiceInfoConverter converter = new ServiceInfoConverter();
        converter.setConvertionRules(Arrays.asList(
                new ServiceInfoStatusConvertionRule(0, 1),
                new ServiceInfoPhoneNumberConvertionRule(1, 20),
                new ServiceInfoActiveXlServiceConvertionRule(21, 1),
                new ServiceInfoLanguageConvertionRule(22, 1),
                new ServiceInfoXlServiceLanguageConvertionRule(23, 1),
                new ServiceInfoServiceEndDateConvertionRule(24, 12),
                new ServiceInfoXlServiceStartTimeConvertionRule(36, 4),
                new ServiceInfoXlServiceEndTimeConvertionRule(40, 4),
                new ServiceInfoOverrideListUsedConvertionRule(44, 1),
                new ServiceInfoOverrideListConvertionRule(45, 20, 15, 8)
        ));

        return converter;
    }

    @Bean
    public Cache serviceInfoCache() {
        return new ConcurrentMapCache("ServiceInfoCache");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("/index.html").addResourceLocations("/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
