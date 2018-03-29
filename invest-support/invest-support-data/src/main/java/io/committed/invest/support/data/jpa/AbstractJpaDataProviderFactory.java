package io.committed.invest.support.data.jpa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.util.StringUtils;

import io.committed.invest.extensions.data.providers.AbstractDataProviderFactory;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

/**
 * A factory for creating JPA DataProvider, with common settings.
 *
 * <p>This will set up a Spring Data JPA Repository for the provided entityClass.
 *
 * <p>An implement o
 *
 * <pre>
 *
 * MyJpaRepository repo = buildRepositoryFactory(settings).getRepository(MyJpaRepository.class)
 * return Mono.just(new MyJpaDataProvider(myJpaRepository));
 *
 *
 * </pre>
 *
 * where MyJpaRepository is type Spring Data JPA repository ANNOTED WITH NoRepository:
 *
 * <pre>
 * &#64;NoRespoitoryBean
 * public interface MyJpaRepository extends JpaRepository&gt;...$lt: {
 *   ... additional mentions here
 * }
 * </pre>
 *
 * Note that {@link NoRepositoryBean} is required because we want this factory to create the bean,
 * not Spring.
 *
 * <p>In the above the MyJpaDataProvider is an implementation of your DataProvider interface, but it
 * likely to be a pass through simply converting between your DB entity and your DTO.
 *
 * @param <P> the generic type
 */
public abstract class AbstractJpaDataProviderFactory<P extends DataProvider>
    extends AbstractDataProviderFactory<P> {

  // Suppress this obviously password setting key (its not a password)!
  @SuppressWarnings("squid:S2068")
  public static final String PASSWORD = "password";

  public static final String USERNAME = "username";
  public static final String URL = "url";
  public static final String DRIVER_CLASS_NAME = "driverClassName";

  private final EntityManagerFactoryBuilder emfBuilder;

  private final Class<?> entityPackageClass;

  protected AbstractJpaDataProviderFactory(
      final EntityManagerFactoryBuilder emfBuilder,
      final String id,
      final Class<P> providerClazz,
      final Class<?> entityPackageClass) {
    super(id, providerClazz, DatabaseConstants.SQL);
    this.emfBuilder = emfBuilder;
    this.entityPackageClass = entityPackageClass;
  }

  protected JpaRepositoryFactory buildRepositoryFactory(final Map<String, Object> settings) {

    final String driverClassName =
        (String) settings.getOrDefault(DRIVER_CLASS_NAME, "org.h2.Driver");
    final String url = (String) settings.getOrDefault(URL, "dbc:h2:mem:invest");
    final String username = (String) settings.get(USERNAME);
    final String password = (String) settings.get(PASSWORD);

    final DataSourceBuilder<?> dataSourceBuilder =
        DataSourceBuilder.create().driverClassName(driverClassName).url(url);

    if (!StringUtils.isEmpty(username)) {
      dataSourceBuilder.username(username);
    }
    if (!StringUtils.isEmpty(password)) {
      dataSourceBuilder.password(password);
    }

    return createFromBuilder(dataSourceBuilder);
  }

  protected JpaRepositoryFactory createFromBuilder(final DataSourceBuilder<?> dataSourceBuilder) {
    final DataSource dataSource = dataSourceBuilder.build();

    final LocalContainerEntityManagerFactoryBean emf =
        emfBuilder
            .dataSource(dataSource)
            .packages(entityPackageClass)
            .persistenceUnit(getId())
            .build();

    final EntityManager entityManager = emf.getObject().createEntityManager();
    final JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);
    factory.setBeanClassLoader(emf.getBeanClassLoader());
    return factory;
  }
}
