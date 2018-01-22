package fr.yacini.exceltosql.springframework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import fr.yacini.exceltosql.builder.QueryBuilder;

/**
 * @author Samir
 *
 */
@Configuration
@ComponentScan(basePackages = { "fr.yacini.exceltosql" })
public class SpringConfiguration {

	@Bean
	public QueryBuilder getQueryBuilder() {
		return new QueryBuilder();
	}
}
