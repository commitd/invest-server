---
id: server.extension-points
title: "Invest Server Extension Points"
date: "2017-10-20"
order: 3020
hide: false
draft: false
---

The Invest server offers several extension points for plugins, which are defined through Java interfaces. A plugin implementation needs to inherit one of those interfaces. 

The parent, and most generic, extension interface is `InvestExtension`. From this, you have several specialised classes:

* `InvestUiExtension` hosts a UI plugin which will be available through the web browser
* `InvestGraphQlExtension` provides a GraphQL query or mutation endpoint
* `InvestApiExtension` provides a generic API endpoint (for example, providing an REST endpoint)
* `InvestDataExtension` provides data to the system (for example, connecting to a specific database to get specific data out)
* `InvestServiceExtension` provides a back end service (for example, provides an algorithm which another API or GraphQL extension might utilises)

These specialisation may not contain more methods than the `InvestExtension` however it in important to extend the correct class as the different types are used by Invest server to wire the application and plugins together.

Spring does not know where to look for plugins. One way to find extensions would be through classpath scanning, but this is timeconsuming as the number of dependencies grow. Inside we use Spring autoconfiguration mechanism, much like Spring's starter kits too.

## Implementing a plugin

In order to implement a plugin you should create a class which implements one of the above. 

The interfaces have default implementation for many of their methods, leaving the developer able to postpone some aspects of customisations until later if they follow the conventions. We recommend that plugin developers override methods which provide support to the user (eg the description of what the plugin does). 

The plugin class inmplementation should be annotated with @Configuration, as it is a configuration bean via auto configuration.

```java
package com.example.myplugin;

@Configuration
public class MyPlugin implements InvestServiceExtension {

}
```

In order to make use of autoconfiguration the Spring needs to know where to look for this bean. It does this via the `spring.factories` resource. Create a text files in `src/main/resources/META-INF/spring.factories` which containst he following:

```ini
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.myplugin.MyPlugin
```

If you have multiple plugins, one UI and one GraphQL) in the same JAR they can be included in as follows:

```ini
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.myplugin.MyPlugin,\
com.example.another.MyOtherPlugin
```

Including this project in your classpath should now enable the plugin.

## Typical plugin implementation

Whilst the above produces a plugin, it does very little. The way to implement functionality in a plugin is dependent on the type of extension, but we just note a few simple examples here.

If your plugin is very simple (which is good), then you can expose a Spring bean directly

```java
@Configuration
public class MyPlugin implements InvestServiceExtension {

    ...

    @Bean
    public MyPluginService myPluginService() {
        return new MyPluginService();
    }
}

```

If you have multiple components you need to hook up, or you want some more complex profile/configuration decisions, you could put them in their own configuration class and `@Import` it:

```java
@Configuration
@Import(MyPluginConfig.class)
public class MyPlugin implements InvestServiceExtension {

    ...

}

@Configuration
// Eg: @Profile('myplugin-enabled)
// Eg: @ConditionalOnClass(MongoDatabaseService.class)
public class MyPluginConfig() {

    @Bean
    public MyPluginService myPluginService() {
        return new MyPluginService();
    }
}
```

Or, more likely, you can also use `@ComponentScan` in order to pull in services:

```java
@Configuration
// Use ourselves as the base package...
@ComponentScan(MyPlugin.class)
public class MyPlugin implements InvestServiceExtension {

    ...

}
```

Finally you probably want to offer settings (ConfigurationPropertoes) which a user can set via the YAML or properties files, and the plugin will pick up:

```java
@Configuration
@EnableConfigurationProperties(MyPluginSettings.class)
public class MyPlugin implements InvestServiceExtension {

    ...

}

// You don't need to use Lombok but...
@Data
@ConfigurationProperties("myplugin.settings")
public class MyPluginSettings {


    // You can set this in your application.properties using 'myplugins.settings.title=Hello World'
    private String title;
}

```

## Points to note

* You can have as many plugins in a single JAR as you like. 
* You can have plugins within say the `invest-server-app.jar` (if you want to create a single JAR file)
* There is no isolation between extensions, thus any extension can `@Autowired` any other extension. In future this might change, for example such that Service Extensions can access Data Extensions but not Api Extensions, however this feels like an unnecessary constraint.
 