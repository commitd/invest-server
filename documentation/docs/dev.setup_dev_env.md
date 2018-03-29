---
id: "dev.setup-dev-env"
title: "Setup a dev environment"
date: "2017-10-20"
order: 1010
hide: false
draft: false
---

We'll first setup a development environment for Invest.

**Note that you don't need this is you just want to develop plugins or build an application from Invest. You only require this setup if you want to develop the Invest codebase itself.**

## Dependencies

```bash
mkdir invest
cd invest
```

Clone the Git repositories. One contains the server side Java code and the other is for the UI elements:

```bash
git clone git@bitbucket.org:committed/invest-java.git
git clone git@bitbucket.org:committed/invest-js.git
```

This will create two sub directories, `invest-java` and `invest-js`.

Install the UI dependencies, create the inter dependencies between the projects using Lerna:

```bash
cd invest-js
lerna bootstrap
yarn build
```

## Building a complete Invest application with plugins on the command line

We recommend that you test your build environment from the commandline first. This will also download all the maven dependencies and some other files from the Internet (thus you need to be online).

To compile the complete Invest code, the invest-java repository has scripts to help:

```bash
cd invest-js
yarn build
cd ../invest-java
./build-with-ui.sh ../invest-js
```

The `../invest-js` is the path to the JS elements. So if you have these in a different directory then amend this command appropriately.

This will build the UI, copy it into the Java UI plugins, then build the Java plugins and applications, finally placing all the resulting artifacts into the `build/` directory.

You can build the UI alone with:

```bash
cd invest-js
yarn build
```

To build the Java component alone run:

```bash
cd invest-java
mvn clean package install
```

## Developing the Java server with an IDE

When developing you will want to use an IDE. We use Eclipse as an example of the intended process.

First import all the Maven projects by File > Import > Existing Maven Projects and selecting the `invest-java` directory as the Root Directory. We'd suggest adding all these projects to a Eclipse Working Set (called `Invest`).

Once imported, you can run the project from the IDE by running the `invest-server-app` project, specifically the `io.committed.invest.server.app.Invest` main class.

This should run successfully but it will be a empty server. In fact it will contain virtually no functionality, as the `invest-server-app` does not contain any of the plugins by default. With Eclipse you can add the additional plugins projects by:

* Stopping the application (if running)
* Goto Debug Configurations (or Run Configuration equivalently) located under the Run menu (or as a drop down on the Debug/Run buttons on the toolbar).
* Select the `invest-server-app` run, then the ClassPath tab.
* Click on `User Entries` and then `Add Projects..`
* Select all the plugins you want for example all the projects prefixed `invest-plugin-`. If you are developing custom plugins you should add those too. Click `Apply`.
* If you want to debug these plugins then click on the the `Source` tab, click `Add` and select the same projects. This tells Eclipse where to find the source code when you hit a breakpoint.

Note that when developing changes to the UI will not be reflected unless you do a complete `build-with-ui.sh`. This will copy over the UI code into the Java projects. (Depending on your Eclipse settings you may need to run 'Refresh' against the projects / working set so that changes are found by Eclipse).

## Developing the Invest UI (application or core library) with an IDE

As with Java, start by opening the `invest-js` directory as a project. We will use Visual Studio Code for this guide. Use File > Open... then select the `invest-js` directory.

You can think of the Invest JS code as being three separate areas. One is a set of libraries, another the application and then a set of UI plugins. The application and the plugins depend on the libraries.

When you do any development on the UI you'll probably want to run the application:

```bash
yarn dev:app
```

Visiting http://localhost:3000 will present you with a live version of your in-development Invest.

You may also want to have any changes in the libraries reflected in the application and any plugins you are developing.

```bash
yarn dev:libs
```

Making (and saving) changes to the libs or the main app will cause an update/refresh in the browser.

**Gotcha:** If you change the types, e.g. classes, interfaces or exports, of a project you might find that this is not reflected properly. That is, you encounter errors in cases where the new changes are not seen. If this is the case stop (`ctrl+c`) and restart with the above commands. This seems to be a limitation in the type processing in the build system.

The above commands will not run any UI plugins in development mode, see [UI development](invest/ui) for more information. In short, to develop the Invest UI plugins in `invest-js/plugins` you should first run the above commands then:

```bash
cd invest-js/plugins/[plugin-name]
yarn develop
```

This will run a plugin which you can access from http://localhost:3001 or via the [Live Development Plugin](invest/ui/live-development-plugin). Note that you can only have one live development plugin at once (unless you start modifying the port on which the development versions are published).

## Developing UI plugins without running Invest in development mode

You can develop UI plugins without running the live development version of Invest (as detailed above). When you run the `bulid-with-ui.sh` command above the UI components are build and copied into the various Java plugins. For example, the `invest-plugin-ui-app` Java project contains the build Invest application, and the `invest-plugin-ui-devaction` Java project will contain the `invest-js/plugin/invest-ui-devaction` UI plugin.

By running `build-with-ui` and then adding these plugins to the classpath when you run `invest-server-app` as above you will have a complete Invest application, with the UI modules already built inside your IDE. You don't then need to run `yarn dev:app`.

You can run just the `invest-server-app` form your java IDE and then develop your UI plugin, visit http://localhost:8080 to see the Invest UI.

## Advanced: Developing your own UI plugins linked to live Invest

If you are developing a third party Invest UI plugin you likely will not be placing it inside the `invest-js` repository. However you might want to use the development version of `invest-js` (as you did with when you ran `yarn dev:libs` above).

Yarn can link your plugin directly to the development version of `invest-js` on your system. First we tell yarn about our projects:

```bash
cd invest-js
yarn link:libs
```

Now goto you plugin directory, where your `package.json` will declare a dependency on say `invest-plugin`.

```bash
cd my-new-plugin
yarn link
```

Yarn will report that it is using `invest-plugin` locally. In your `node_modules` you'll see that the `invest-plugin` is now a symlink to your development `invest-js/invest-plugin` directory.

Finally run `yarn dev:libs` from the `invest-js` as below

## Next steps

You are now ready to build either a UI or Server plugin...
