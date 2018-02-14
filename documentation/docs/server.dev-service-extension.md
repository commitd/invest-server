---
id: server.dev-service-extension
title: "Developing Service extension"
date: "2017-10-20"
order: 3500
hide: false
draft: false
---

A service extension is the most generic in Invest. It is effectively a one or more Spring @Service / @Component which provide reusable functionality to the other plugins.

It is probably unusual to implement a service plugin, as most plugins will be either to add data, ui or graphql. In particular most services are likely offered to the UI via a GraphQL endpoint. 

Thus if you are implementing a service it is likely because you want to offer multiple implementation of that service. As such we recommend you create two Maven projects.

* A Service interface (as a Java interface) which other projects can dependend upon.
* THe default service implementation and invest plugin which implements the Service.

We'll use a typically `toUpperCase` example in the below. Obviously this functionality would not be sufficiently complex to warrant building a service around. The types of functionality might be offered as a service are would be algorithims (data transformation, data clustering), or access to a third party API such as translation.

## The Service Interface

Create a Maven project called `invest-service-myserviceinterface`. 

Within this lets create a simple 'toUpperCase' service, 

```java
public interface CaseChanger {

    String changeCase(String text);
}
```

That's it. 

## Implementing the Service plugin

Create a second Maven project, this time [as a Invest plugin](./dev-maven), called `invest-service-uppercasechanger`. This should include the `invest-service-casechanger-interface`.

We create a class`UpperCaseChangerExtension` which is the extension definition under `io.committed.invest.plugins.casechanger`:

```java
package io.committed.invest.plugins.casechanger;

import org.springframework.context.annotation.Configuration;

import io.committed.invest.extensions.InvestUiExtension;

@Configuration
@ComponentScan
public class UpperCaseChangerExtension implements InvestServiceExtension {

  @Override
  public String getId() {
    return "invest-service-casechanger";
  }

}
```

As with all plugins we create the `spring.factories` file under `src/main/reources/META-INF/` so that Spring can find out extension:

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=io.committed.invest.plugins.casechanger.UpperCaseChangerExtension
```

And alongside that our implementation. Note our implementation will be found automatically by Spring via the `@ComponentScan` functionality.

```java
@Service
public class UpperCaseChangerExtension implements CaseChanger {
    public String changeCase(String text) {
        return text == null ? null : text.toUpperCase(); 
    }
}
```

This will now build into a Invest JAR plugin to be used in other applications.

## Accessing the Service API in another plugin

In another plugin (eg a GraphQL plugin) you will want to access this `CaseChanger` functionality. In that plugin, you should include the `invest-service-myserviceinterface` as a Maven dependency. Then, as in standard Speing, you cshould autowire this interface.

```java
@InvestGraphQlService 
public class ExampleGraphQL {

    private final CaseChanger caseChanger;

    @Autowired
    public ExampleGraphQL(CaseChanger caseChanger) {
        this.caseChanger = caseChanger;
    }

    ...


    public hello(String name) {
        return "Hello " + caseChanger.changeCase(name);
    }

}
```

Though this is as you would normally write a Spring Boot application, it worth noting some additional consideration due to the plugin nature of Invest. 

* What happens if no implementaiton is provided? Spring will currently exit, saying that an implementaiton is required.
* What happens if multiple implemntation are provided? Spring will exist say it is not sure which to CaseChanger to auto wire.

The correct solution to this behaviour will depend on your service. For example, it may be correct that one and only one implementaiton is available - thus it is up to the person running Invest to correctly specify a single plugin which offers the `CaseChanger`.

If not then you have serveral options, depending on where you'd like to fix this problem. Spring's traditional approaches to this are the `@Primary` to a specify a specific implementaiton, `@Qualifier` to name a bean specifically, or use `@ConditionalOnMissingBean` to ensure there is a single bean of a single type. In some sense these work well when you have a fixed set of known plugins.


As such as we'd recommend an approach of either using a non-required autowired list which you can pick the best from:

```java
@InvestGraphQlService 
public class ExampleGraphQLWithList {

    private final CaseChanger caseChanger;

    @Autowired
    public ExampleGraphQLWithList(@Autowired(required=false) List<CaseChanger> caseChangers) {
        // If we don't have caseChangers when we set to null
        // A nicer approach would be to have a default implementation here so caseChangers is never null
        // If we have one we can pick the best. We can use Spring @Ordered to define the best, or we could have 
        // other functions on the CaseChanger interface to help us decided (perhaps one is performance for
        // long strings)
        this.caseChanger = caseChangers == null || caseChangers.isEmpty() ? null : caseChangers.get(0);
    }

       public hello(String name) {
        String changed = caseChanger == null ? name : caseChanger.changeCase(name);
        return "Hello " + changed;
    }
```

Or we can use another configuration bean to help simplify the configuration. This can be used with the previous `ExampleGraphQL` unchanged:

```java
    // TODO: Does this work??? So its there another Spring way of doing this...

@Configuration
public class CaseChangerHelperConfig {

    @Bean
    // Annotate a primary so it know
    @Primary
    public CaseChanger bestCaseChanger(List<CaseChanger> caseChangers) {
        // Get the first... or use a smart approach
        // Note we know there's one... beacuse it's defined below
        return caseChangers.get(0);
    }

    // Ensure one caseChanger exists
    @Bean
    @ConditonalOnMissingBean(CaseChanger.class)
    public CaseChanger doNothingCaseChanger() {
        return text -> text;
    }
}
```

Though additonal classes to manage, the nice aspect of the `CaseChangerHelperConfig` is that is allows a specific resolution logic for your plugin, as awell as a sensible default implementation.



