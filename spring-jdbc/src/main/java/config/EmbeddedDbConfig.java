package config;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ComponentScan("dao")
public class EmbeddedDbConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedDbConfig.class);

  @Bean
  public DataSource dataSource() {
    try {
      EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
      return databaseBuilder.setType(EmbeddedDatabaseType.H2)
          .addScripts("classpath:db/ddl.sql", "classpath:db/dml.sql")
          .build();
    } catch (Exception e) {
      LOGGER.error("Embedded data source bean can not be created", e);
      return null;
    }
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    jdbcTemplate.setDataSource(dataSource());
    return jdbcTemplate;
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource());
    return namedParameterJdbcTemplate;
  }
}
