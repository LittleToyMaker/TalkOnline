package zwh.talkonline_web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import zwh.talkonline_web.filter.DecodeFilter;
import zwh.talkonline_web.interceptor.SessionChecker;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
public class ConfigurationApp extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionChecker())
                .addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList("/account/signIn", "/account/signUp", "/index", "/html/*", "/js/*", "/css/*"));
    }
}
