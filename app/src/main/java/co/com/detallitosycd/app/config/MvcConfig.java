package co.com.detallitosycd.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/image_products/**").addResourceLocations(
                "file:"+System.getProperty("user.home")+ File.separator + "Documents" +
                        File.separator + "image_products" + File.separator);
    }
}
