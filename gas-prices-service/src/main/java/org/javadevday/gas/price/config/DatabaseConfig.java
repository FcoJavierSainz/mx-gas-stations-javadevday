package org.javadevday.gas.price.config;

import com.ibm.reactive.jpa.Database;
import com.ibm.reactive.jpa.PoolConfiguration;
import com.zaxxer.hikari.HikariDataSource;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jpa.HibernateMetrics;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

  @Value("${db.uri}")
  private String dbUri;

  @Autowired
  MeterRegistry registry;

  @Bean
  Database database() {
    HashMap<String, String> settings = new HashMap<>();
    settings.put("hibernate.connection.url", dbUri);
    //settings.put("hibernate.show_sql", "true");

    List<String> packages = Collections.singletonList("org.javadevday.gas.price.model.station");
    PoolConfiguration configuration = PoolConfiguration.builder().maxPoolSize(10).build();
    return new Database(settings, packages, configuration);
  }

  @Bean
  DataSource dataSource(Database database) {
    HikariCPConnectionProvider cp = (HikariCPConnectionProvider) database.getSessionFactory()
        .getSessionFactoryOptions().getServiceRegistry().
            getService(ConnectionProvider.class);
    return cp.unwrap(HikariDataSource.class);
  }

  @Bean
  HibernateMetrics metrics(Database database) {
    return new HibernateMetrics(database.getSessionFactory(), "mysql", Tags.empty());
  }

  @PostConstruct
  public void configurer() {
    registry.config()
        .commonTags("region", "mexico")
        .commonTags("name", "prices");
  }


  @Bean
  CircuitBreaker circuitBreaker() {
    CircuitBreakerConfig config = CircuitBreakerConfig.custom()
        .failureRateThreshold(20)
        .ringBufferSizeInClosedState(5)
        .build();

    CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
    return registry.circuitBreaker("dbCircuitBreaker");
  }
}
