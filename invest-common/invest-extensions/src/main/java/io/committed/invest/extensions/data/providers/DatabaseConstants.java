package io.committed.invest.extensions.data.providers;

/** Constants for the names of Databases (for use with DataProviderFactory) */
public final class DatabaseConstants {

  private DatabaseConstants() {
    // Singleton
  }

  public static final String MONGO = "Mongo";

  public static final String SQL = "Sql";

  public static final String ELASTICSEARCH = "Elasticsearch";

  public static final String NEO4J = "Neo4j";

  public static final String CASSANDRA = "Cassandra";

  public static final String REDIS = "Redis";

  public static final String MEMORY = "Memory";

  public static final String FILE = "File";
}
