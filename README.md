# Invest Java

Modular, plugin based framework for Spring servers

## Building

First build the `invest-js` project.

`./build-with-ui ../invest-js` assuming `../invest-js` has invest-js repo, with built ui.

The output of the of the build will be placed in `build/`.

This will also install the various artifacts to your local Maven repository. 

## Running

Functionality is added by plugins in Invest. If you run the `invest-server-app.jar` on its own you'll have a very empty application.

So instead you need to run point java to the location of your plugin jars. 

You might want to put them all under a `/plugins` directory, in which case:

```
java -Dloader.path=plugins/ -jar invest-server/invest-server-app/target/invest-app-SNAPSHOT.jar
```

You can have as many paths on the `loader.path` as you like, separate them `:`.  You can also set them on the environment variable LOADER_PATH. See the [Spring Documents](https://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html#executable-jar-property-launcher-features) for more details.


## Licence

THis project is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).