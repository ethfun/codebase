package com.inglc.codebase.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author inglc
 * @date 2020/3/20
 */
@Configuration
@ConfigurationProperties("spring.mybatis.read")
@MapperScan(basePackages = "com.inglc.codebase.repository.read", sqlSessionFactoryRef = "sqlSessionFactoryRead")
public class DataSourceConfigRead extends MybatisProperties {


	@Bean(value = "dataSourceRead", initMethod = "init")
	@ConfigurationProperties("spring.datasource.druid.read")
	public DruidDataSource dataSourceRead(){
		return DruidDataSourceBuilder.create().build();
	}


	@Bean(value = "sqlSessionFactoryRead")
	public SqlSessionFactory sqlSessionFactoryRead() throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSourceRead());
		sessionFactory.setMapperLocations(resolveMapperLocations());
		sessionFactory.setTypeAliasesPackage(getTypeAliasesPackage());
		return sessionFactory.getObject();
	}

}


