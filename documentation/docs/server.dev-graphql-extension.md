---
id: server.dev-graphql-extension
title: "Developing GraphQL extension"
date: "2017-10-20"
order: 3500
hide: false
draft: false
---

Under Invest, GraphQL is intended as primary interaction mechanism between the UI (or other API consumer) and the server. 

Assuming you have the plugins availble, then Invest will provide the `/graphql` endpoint preconfigured along with the [GraphiQL](https://github.com/graphql/graphiql) client as a Invest plugin.


Invest Server uses the [GraphQL-SPQR](https://github.com/leangen/graphql-spqr) as its implementation.

## Create a project

Create a  Maven project, [as a Invest plugin](./dev-maven), called `invest-gql-hello`.

We create a class `HelloGraphQlExtension` which is the extension definition under `io.committed.invest.plugins.hellogql`:

```java
package io.committed.invest.plugins.hellogql;

import org.springframework.context.annotation.Configuration;

import io.committed.invest.extensions.InvestUiExtension;

@Configuration
@ComponentScan
public class HelloGraphQlExtension implements InvestGraphQlExtension {

  @Override
  public String getId() {
    return "invest-gql-hello";
  }

}
```

As with all plugins we create the `spring.factories` file under `src/main/reources/META-INF/` so that Spring can find out extension:

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=io.committed.invest.plugins.hellogql.HelloGraphQlExtension
```

This is not a GraphQl plugin but it does not offer any GraphQL endpoints.

## Creating GraphQL queries and mutations

In Invest as GraphQL endpoint is declared through by an `@InvestGraphQlService`, which is a Spring component stereotype like `@Service`. As a result you can, and likely will need to, autowire any Spring beans into your GraphQL Service. 

GraphQL-SPQR uses Java Annotations to create the GraphQL schema, a

```java

@InvestGraphQlService
public class GqlHelloService {

    @GraphQLQuery(name = "search",  description = "Find a document by its content")
    public List<Document> searchByQuery(@GraphQLArgument(name = "query") String query) {
      ...
    }

} 

// where

public class Document {

    private String title;
    private String author;
    private String content;

    ... 
}

```

This will result in a GraphQL schema such as: 

```

query {
  search(query: String!) {
      author: String
      content: String
      title: String
  }

}

```

Refer to SPQR for more information.

## Nesting GraphQL 

The powerful aspect of SPQR and GraphQL is addition of function into the Schema. Support we want to add a document's size as an output on the schema? With GrahQL we can extend the Document type to add that function. 

```java
@InvestGraphQlService
public class GqlExtraFunctionsOnDocumentService {

    @GraphQLQuery(name = "size",  description = "Size of the document")
    public int getDocumentSize(@GraphQLContext Document document) {
      return document.getContent().length();
    }

} 
```

The key function here is the `@GraphQLContext` which SQPR uses indicate that this method should be added to whenever `Document` is seen in the schema. 

Now the schema will become:

```

query {
  search(query: String!) {
      author: String
      content: String
      title: String
      size: Int
  }

}

```

From the caller perspective the document looks like it has a size, whereas this is purely calcualted. Whilst this is a trival example, the size function could easily be something more complex, jsut a generating a short summary, or list of keywords, etc.

## Accessing contextual information

You can use `@GraphQLRootContext` to get the 'root context' in which the query is running. That is provided by Invest at the time the particular GraphQL operation is executed and is of type `io.committed.invest.core.graphql.Context`. It contains for example the Spring `WebSession` and the `Authentication`.

Due to the way  GraphQL is executed (running in an multi threaded manner) the Spring Reactive annotation do not work on `@InvestGraphQlService` methods (ie you can't use `@PreAuthorize`). Thus the root context provides access to those functions.

*This may change in future when the API for SPQR and Spring Boot are stabilised*

## Thinking in GraphQL

Here are some pointers for working with GraphQL:

* Where possibel avoid adding operations at the top (root query) unless they really belong there as `search` does above. For example, we could have created `getDocumentSize` alongside `search` but its far more powerful to have it placed onto Document.
* When implementing a operation consider where else it could be used for standardisation or terms For example should `search` have a `size` like document does?
* GraphQL supports mandatory and optional arguments, `search` could have pagination (page / size) support here.
* Often reather than returning something directly (eg `search` directly returns an array of `Documents`) it is better to return an intermediate object such as `SearchResults`. SearchResults can then offer additional mentionds itself, but it can be extended by others. 
* Create a Maven project which has all you basic results from GraphQL operrations, for example `Document` in above. These can be classes or interfaces. By moving them into a common project others can extend them easily.