# Invest Java

Modular, plugin based framework for Spring servers

## Building

You will likely want to build the Invest server having first build the UI and placed the UI artifacts in the correct locations Refer to the `invest` project repository on how to do this.

To build this repository in isolation, run:

`./build`

The output of the of the build will be placed in `build/`.

## Running

Functionality is added by plugins in Invest. If you run the `invest-server-app.jar` on its own you'll have a very empty application.

So instead you need to run point java to the location of your plugin jars.

You might want to put them all under a `/plugins` directory, in which case:

```
java -Dloader.path=plugins/ -jar invest-server/invest-server-app/target/invest-app-SNAPSHOT.jar
```

You can have as many paths on the `loader.path` as you like, separate them `:`. You can also set them on the environment variable LOADER_PATH. See the [Spring Documents](https://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html#executable-jar-property-launcher-features) for more details.

## Licence

THis project is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
