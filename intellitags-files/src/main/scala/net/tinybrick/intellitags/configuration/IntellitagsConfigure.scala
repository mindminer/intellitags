package net.tinybrick.intellitags.configuration

import net.tinybrick.intellitags.logger.Loggable
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.web.servlet.config.annotation.{ WebMvcConfigurerAdapter}
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

/**
  * Created by wangji on 2016/6/13.
  */

@EnableAutoConfiguration
@Configuration
@PropertySource (value = Array ("classpath:config/intellitags.properties") )
class IntellitagsConfigure extends WebMvcConfigurerAdapter with Loggable{
    @Bean def propertySourcesPlaceholderConfigurer: PropertySourcesPlaceholderConfigurer = {
        return new PropertySourcesPlaceholderConfigurer
    }

    /*@Bean def requestMappingHandlerMapping: RequestMappingHandlerMapping = {
        val requestMappingHandlerMapping: RequestMappingHandlerMapping = new RequestMappingHandlerMapping
        requestMappingHandlerMapping.setDefaultHandler(new MyAnnotationResolver)
        return requestMappingHandlerMapping
    }*/
}
