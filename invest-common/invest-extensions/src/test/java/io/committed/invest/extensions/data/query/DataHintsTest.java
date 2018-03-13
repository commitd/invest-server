package io.committed.invest.extensions.data.query;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import io.committed.invest.extensions.data.providers.AbstractDataProvider;
import io.committed.invest.extensions.data.providers.DataProvider;
import reactor.core.publisher.Flux;

public class DataHintsTest {

  private static final String DATASET = "dataset";
  private static final String PROVIDER = "provider";
  private static final String DATASOURCE_1 = "ds1";
  private static final String DATASOURCE_2 = "ds2";
  private static final String DATABASE_1 = "db1";
  private static final String DATABASE_2 = "db2";

  private List<DataProvider> dataProviders;
  private MockDataProvider dp11;
  private MockDataProvider dp21;
  private MockDataProvider dp12;
  private MockDataProvider dp22;

  @Before
  public void before() {
    dp11 = new MockDataProvider(DATASET, DATASOURCE_1, DATABASE_1, PROVIDER);
    dp21 = new MockDataProvider(DATASET, DATASOURCE_2, DATABASE_1, PROVIDER);
    dp12 = new MockDataProvider(DATASET, DATASOURCE_1, DATABASE_2, PROVIDER);
    dp22 = new MockDataProvider(DATASET, DATASOURCE_2, DATABASE_2, PROVIDER);

    dataProviders = Arrays.asList(dp11, dp21, dp12, dp22);

  }


  @Test
  public void test() {
    assertFilter(null, null, false, dp11, dp21);
    assertFilter("not", "there", false);

    // Data source
    assertFilter(null, DATASOURCE_1, false, dp11);
    assertFilter(null, DATASOURCE_2, false, dp21);


    assertFilter(DATABASE_1, null, true, dp11, dp21);

    // Database
    assertFilter(DATABASE_1, null, false, dp11, dp21);
    assertFilter(DATABASE_2, null, false, dp12, dp22);


    // Both
    assertFilter(DATABASE_1, DATASOURCE_1, false, dp11);
    assertFilter(DATABASE_2, DATASOURCE_1, false, dp12);

    // Allow duplicates
    assertFilter(null, DATASOURCE_2, true, dp21, dp22);
    assertFilter(DATABASE_1, null, true, dp11, dp21);


  }

  protected void assertFilter(final String db, final String ds, final boolean dup,
      final DataProvider... expected) {
    final DataHints hints = new DataHints(db, ds, dup);

    final List<DataProvider> filtered = hints.filter(Flux.fromIterable(dataProviders)).collectList().block();

    // Not sure we proscribe the ordering
    assertThat(filtered).contains(expected);
  }


  public class MockDataProvider extends AbstractDataProvider {

    private String providerType;
    private String database;

    protected MockDataProvider(final String dataset, final String datasource, final String database,
        final String providerType) {
      super(dataset, datasource);
      this.database = database;
      this.providerType = providerType;
    }

    @Override
    public String getProviderType() {
      return providerType;
    }

    @Override
    public String getDatabase() {
      return database;
    }

    @Override
    public String toString() {
      return database + ":" + getDatasource();
    }

  }
}
