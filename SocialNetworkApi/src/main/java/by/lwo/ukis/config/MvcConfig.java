package by.lwo.ukis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/loginPage").setViewName("loginPage");
        registry.addViewController("/allUsers").setViewName("allUsers");
        registry.addViewController("/messagesPage").setViewName("messagesPage");
        registry.addViewController("/homePage").setViewName("homePage");
    }
}