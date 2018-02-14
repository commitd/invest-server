---
id: server.data
title: "Data"
date: "2017-10-20"
order: 3000
hide: false
draft: false
heading: false
---

Access to data in Invest is a departure from the standard Spring Data approach. The reason for this is because we want to support dynamic, configuration driven dataset taht are not known at compile time. 

We want to support the following:

* A developer creates a connector which can access data in a database (or other). The developer is responsible for the database connection but not any of the settings
* At runtime (via configuration) a user can configure one or more instance of that connector.
* Another developer building a service or API can request all the connectors which provide specific data access.

As we see it current Spring Data allows you to have multiple data sources, but these are distinguished by hard coded `@Qualifier`s (ie controlled at development time by the developer).

Note: you can still use pure Spring Data but you will be limited in whatever you code.

## Data access in Invest

In order to explain data in Invest we will use an example. Let us suppose are building an application to view tweets. The UI would be very simple:

* View all the tweets for a user, ordered by time
* View all tweets containing a hashtag

Working from the API back to the data, we want to provide an interface which defines how we can interact with database.


In Invest data items are POJOs (Plain Old Java Objects).  

```
public class Tweet {
    private Long id;
    private String user;
    private String text;
    private Date timestamp;

    ...
}
```


In Invest this interface is called a **DataProvider** and inherits from the interface `DataProvider`.  For those familiar with Spring Data, a DataProvider looks a bit like a SPring Data `Repository` but it doesn't have any default methods (like `findAll()`)

```
public interface TweetDataProvider extends DataProvider {

    List<Tweet> findByUser(String user);

    List<Tweet> findByHashtag(String tag);

    void deleteTweet(Long id)

    ... other methods ...
} 
```

At this stage we've not specified how the data is retrieved or from where. A DataProvider has two methods `getDatabase` and `getDatasource`. These return a unique idnetifier for the database type (something like "mongo", "file", "mysql") and the original source of the data ("twitter", "mastadon"). These values can be anything, but they allow Invest to pick the best providers to use:

* It may be that for a particular query we want to use a specific database as we know its optimal.
* If we have two database for the same source (hence containing the same information), we only want to use one as otherwise we'll get duplicate data.

In order to create the `TweetDataProvider` instance we create one or more `DataProviderFactory`. This will be database specific.

Lets say we have a list of tweets in memory (hardcoded):

public class MemoryDataProviderFactory extends DataProviderFactory<TweetDataProvider> {

    public build(String dataset, String datasource, Map<String,Object> settings) {
        return new MemoryDataProvider();
    }
}

public class MemoryDataProvider extends TweetDataProvider {

    private static final List<Tweet> tweets = Arrays.asList(
        new Tweet(1L, 'User1', 'The first tweet', new Date()),
        ...
    )
    public List<Tweet> findByUser(String user) {
        return tweets.stream()
            .filter(t -> user.equals(t.getUser()))
            .collect(Collectors.toList())
    }

    public List<Tweet> findByHashtag(String tag) {
        return ...
    }

}


You might also have a Mongo implementation:

```

public class MongoDataProviderFactory extends DataProviderFactory<TweetDataProvider> {

    public build(String dataset, String datasource, Map<String,Object> settings) {
        String mongoServerHost = settings.gettOrDefault("host", "localhost")
        String mongoDatabaseName = settings.getOrDefault("db", "tweets")
        TweetDataProvider tdp = MongoTweetDataProvider(mongoServer, mongoDatabaseName);
        return tdp;
    }
}

public class MongoTweetDataProvider extends TweetDataProvider {

    public MongoTweetDataProvider(String host, String databaseName) {
        // create mongo client etc
    }

    public List<Tweet> findByUser(String user) {
        return mongo.query(....)
    }

    public List<Tweet> findByHashtag(String tag) {
        return mongo.query(....)
    }

}

```

Note: We've left out the Mongo aspects above, but they could be anything. In fact you can create and use a Spring Data Mongo repository configured to the settings. You can see examples of that and helper code in the Invest codebase.

In order to see your factories you need to declare them as Spring beans in your `InvestDataExtension`. You could do this with `@Bean` or via `@ComponentScan` as above. If you use `@ComponentScan` annotate you `DataProviderFactory` with `@Service`.

Now we have a way of providing an implementation of `TweetDataProcessor` we need to allow the user a mechanism to define these. A **Dataset** is a simply a collection of **DataProviders** which can come from any database or datasource combination. It is defined which is defined by a set of  **DatasetSpecification** and can be set in the `application.yaml`.

```
invest:
  core:
    datasets:
    - id: exampledataset
      name: Tweets
      description: A collection of tweetes 
      providers:
      - datasource: twitter
        factory: tweet-mongo
      - datasource: twitter
        factory: tweet-mem
````









