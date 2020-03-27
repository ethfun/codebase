package com.inglc.codebase.config.database;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @author inglc
 * @date 2020/3/25
 */
@Configuration
public class H2DataSourceConfig {

	@Bean(name = "dataSourceRead")
	public DataSource dataSourceRead() {
		return new EmbeddedDatabaseBuilder()
				.generateUniqueName(true)
				.setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8")
				.ignoreFailedDrops(true)
				.addScripts("schema.sql", "data.sql")
				.build();
	}

	@Bean(name = "dataSourceWrite")
	public DataSource dataSourceWrite() {
		return new EmbeddedDatabaseBuilder()
				.generateUniqueName(true)
				.setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8")
				.ignoreFailedDrops(true)
				.addScripts("schema_w.sql", "data_w.sql")
				.build();
	}


}


