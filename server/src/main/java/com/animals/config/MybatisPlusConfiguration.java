package com.animals.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MybatisPlusConfiguration
{
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        // 💡 重点：手动实例化，强行关联数据源
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean;
    }
}


