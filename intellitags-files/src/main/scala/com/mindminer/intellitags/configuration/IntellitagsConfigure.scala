package com.mindminer.intellitags.configuration

import com.mindminer.intellitags.logger.Loggable
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

/**
  * Created by wangji on 2016/6/13.
  */
@EnableAutoConfiguration
@Configuration
@PropertySource (value = Array ("classpath:config/intellitags.properties") )
class IntellitagsConfigure extends Loggable{
    @Bean def propertySourcesPlaceholderConfigurer: PropertySourcesPlaceholderConfigurer = {
        return new PropertySourcesPlaceholderConfigurer
    }
}
