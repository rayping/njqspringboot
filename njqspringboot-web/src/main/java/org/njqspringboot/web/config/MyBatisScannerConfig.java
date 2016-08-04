package org.njqspringboot.web.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.njqspringboot.common.MyBatisRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisScannerConfig {
	
	@Bean
    public MapperScannerConfigurer MapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("org.njqspringboot.dao");
        mapperScannerConfigurer.setAnnotationClass(MyBatisRepository.class);
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

}
