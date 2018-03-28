---
id: server.dev-ui-extension
title: "Developing UI extensions with Java"
date: "2017-10-20"
order: 3500
hide: false
draft: false
---

This section discusses the UI from the perspective of it being a Invest Server plugin. The UI compromises a Web UI (JavaScript/TypeScript, HTML, etc) plus the Java code around that as a plugin. This section looks from the perspective of the Java plugin, with the web development angle covered in [UI development](ui.dev-ui-extension.html). We will use a simple `index.html` example here together with the `invest.js` script library. Typically plugins will have more complex UI development behind them, but this guide shows that need not be the case.

First create a maven project called `invest-ui-myplugin` as per the [Maven extension development guide](server.dev-maven.html).

## Creating a basic index.html file

Under `src/main/resources` we will create a directory, called `ui/invest-ui-plugin`. We recommend the convention `ui/` as a directory for all your Web UI files, and further subdirectory named uniquely (after you plugin). Firstly this helps other developers find your UI code and secondly it means that if you package multiple UI plugins together their files will not conflict.

Under the `ui/invest-ui-myplugin` directory we'll create a simple `index.html`.

```html
<html>
<body>
<p>An example of a very simple Invest plugin.</p>
</body>
</html>
```


If you were to run this now in Invest, your UI would **not** be available, because we have yet to make Invest aware of this.

## Creating the plugin definition

We create a new Java class in the package `io.committed.invest.plugins.myplugin` called `MyPluginUiExtension`. You can pick any package and class naming convension you wish (ie does not have to `io.committed` for example).

This class should contain the following:

```java
package io.committed.invest.plugins.myplugin;

import org.springframework.context.annotation.Configuration;

import io.committed.invest.extensions.InvestUiExtension;

@Configuration
public class MyPluginUiExtension implements InvestUiExtension {

  @Override
  public String getId() {
    return "invest-ui-myplugin";
  }

}
```

Points to note are:

* `@Configuration` used to indicate this is a Spring configuration bean
* `Implements` of the `InvestUiExtension`.
* Implementation of the getId of the UI extension. This id must be unique (amoungst all plugins in the Invest system) and it is used in several ways. Firstly to identify the plugin. In the case of UI extensions, it will form part of the URL where the plugin is hosted (eg http://example.com/ui/invest-ui-myplugin) and by default Invest will look for the UI files (eg index.html) under the resource classspath (ie src/main/resources/) `ui/{id}/`.

The final operation to enable Invest, or more correctly Spring, to find this plugin is to add a `spring.factories` file under `src/main/resources/META-INF`:

```ini
org.springframework.boot.autoconfigure.EnableAutoConfiguration=io.committed.invest.plugins.myplugin.MyPluginUiExtension
```

Note that the `io.committed.invest.plugins.myplugin.MyPluginUiExtension` should be replaced with the fully qualified reference to the class above.


## Checking function

If you run your plugin now you will see it displayed as an option in the Invest sidebar. Clicking on it should display the (very basic) html we entered above.

## Additional methods to override on InvestUiExtension

There are several issues with the basic setup so far, one being that it is displayed in a friendly way to the user.

We can change this. So far we have only provided the `getId()` function.

```java

  // A friendlier name to display to the user
  @Override
  public String getName() {
    return "My plugin";
  }

 // Offer a short description
  @Override
  public String getDescription() {
    return "Example plugin based on the guide";
  }

  // Pick a (fontawesome/semantic-ui) icon to display
  @Override
  public String getIcon() {
    return "browser";
  }

 // Explicitly stati where to find out index.html and other HTML resources.
  @Override
  public String getStaticResourcePath() {
    return "/ui/invest-ui-myplugin"
  }
```
Note there are other methods which allow you more advanced options such as providing configuration settings to your UI, choosing with user group can see your plugins, etc. 

## Java...done
 
For many UI's this will be as much Java as is required. The work now is on the Javascript/Web side to achieve the functionality required.

## Offering configuration settings

Many UI plugins will have settings. For example, how many results to display for search results. You could hard code these, but instead you can make use of Spring's YAML (or properties based) configuration.

Creating a `@ConfigurationProperties` bean is covered in the [Extension points](server.extension-points.html) section, and is standard Spring Boot. 

If we create a new configuration class `MyPluginSettings` as below, and add `@EnableConfigurationProperties(MyPluginSettings.class)` to our `MyPluginUiExtension` class above:

```java

@Configuration
@ConfigurationProperties("invest.plugins.ui.myplugins")
public class MyPluginSettings {

    // Default value...
    private int maxResults = 10;

    public int getmaxResults() {
        return maxResults;
    }

    public void setMaxResults(String maxResults) {
        this.maxResults = maxResults;
    }
}
```

We can create an `application.yaml` file (in the same directory as we run the invest-server.jar from) containing:

```yaml
invest:
  plugins:
    ui:
      myplugins:
        # Override default value...
        maxResults: 50
```

In order to automatically offer the the MyPluginSettings over GraphQL we can simply declare it in `MyPluginUiExtension`, as:

```java

  @Override
  public String getSettings() {
    return MyPluginSettings.class;
  }
```

YOu can now query via GraphQL in order to access these configuration settings.

## Roles

If you have authentication enabled, then each user can have one or more roles (such as 'admin'). Some plugins should only be available to some user roles. 

To limit your plugin to be displayed to only those users with the correct role, use:

```java

  @Override
  public Collection<String> getRoles() {
    return Array.asList("ADMIN");
  }
```

## Actions

As discussed in the UI section, UI plugins communicate with one another via Intents (actions and payloads). The actions that a plugin supports are defined in Java.

```java
  @Override
  public Collection<ActionDefinition> getActions() {
    return Arrays.asList(
        SimpleActionDefinition.builder()
            .action("document.search")
            .title("Search")
            .description("Search for documents by content")
            .build()
    );

  }
```

`SimpleActionDefinition` provides a basic implementation and a builder to create actions.
