# Vessel Java

Modular, plugin based framework for Spring servers

## Running

Functionality is added by plugins in Vessel. If you run the vessel-app.jar on its own you'll have a very empty application.

So instead you need to run point java to the location of your plugin jars. 

You might want to put them all under a `/plugins` directory, in which case:

```
java -Dloader.path=plugins/ -jar vessel-server/vessel-app/target/vessel-app-1.0-SNAPSHOT.jar
```

You can have as many paths on the `loader.path` as you like, separate them `:`.  You can also set them on the environment variable LOADER_PATH. See the [Spring Documents](https://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html#executable-jar-property-launcher-features) for more details.


