package com.labi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.labi.config.properties.LabiProperties;

/**
 * SpringBoot方式启动类
 *
 * @author lyr
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication
public class LabiApplication extends WebMvcConfigurerAdapter{

    protected final static Logger logger = LoggerFactory.getLogger(LabiApplication.class);

    @Autowired
    LabiProperties labiProperties;

    /**
     * 增加swagger的支持
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if(labiProperties.getSwaggerOpen()){
//            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        }
//    }

    public static void main(String[] args) {
        SpringApplication.run(LabiApplication.class, args);
        logger.info("LabiApplication is success!");
    }
}
