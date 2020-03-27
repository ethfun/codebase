package com.inglc.codebase.config.mybatis;

import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author inglc
 * @date 2020/3/25
 */
@Configuration
@ConfigurationProperties("spring.mybatis.write")
@MapperScan(basePackages = "com.inglc.codebase.repository.write", sqlSessionFactoryRef = "sqlSessionFactoryWrite")
public class MyBatisConfigWrite extends MybatisProperties {

	@Resource(name = "dataSourceWrite")
	private DataSource dataSourceWrite;

	@Bean(value = "sqlSessionFactoryWrite")
	public SqlSessionFactory sqlSessionFactoryWrite() throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSourceWrite);
		sessionFactory.setMapperLocations(resolveMapperLocations());
		sessionFactory.setTypeAliasesPackage(getTypeAliasesPackage());
		return sessionFactory.getObject();
	}
}
