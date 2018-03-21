package io.committed.invest.support.data.jpa;

import java.util.Map;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.util.StringUtils;
import io.committed.invest.extensions.data.providers.AbstractDataProviderFactory;
import io.committed.invest.extensions.data.providers.DataProvider;
import io.committed.invest.extensions.data.providers.DatabaseConstants;

public abstract class AbstractJpaDataProviderFactory<P extends DataProvider>
    extends AbstractDataProviderFactory<P> {

  public static final String PASSWORD = "password";
  public static final String USERNAME = "username";
  public static final String URL = "url";
  public static final String DRIVER_CLASS_NAME = "driverClassName";

  private final EntityManagerFactoryBuilder emfBuilder;

  private final Class<?> entityPackageClass;

  protected AbstractJpaDataProviderFactory(final EntityManagerFactoryBuilder emfBuilder,
      final String id, final Class<P> providerClazz, final Class<?> entityPackageClass) {
    super(id, providerClazz, DatabaseConstants.SQL);
    this.emfBuilder = emfBuilder;
    this.entityPackageClass = entityPackageClass;
  }

  protected JpaRepositoryFactory buildRepositoryFactory(final Map<String, Object> settings) {

    final String driverClassName = (String) settings.get(DRIVER_CLASS_NAME);
    final String url = (String) settings.get(URL);
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


    final LocalContainerEntityManagerFactoryBean emf = emfBuilder.dataSource(dataSource)
        .packages(entityPackageClass).persistenceUnit(getId()).build();

    final EntityManager entityManager = emf.getObject().createEntityManager();
    final JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);
    factory.setBeanClassLoader(emf.getBeanClassLoader());
    return factory;
  }



}

