---
id: server.modules
title: "Invest Server Modules"
date: "2017-10-20"
order: 3010
hide: false
draft: false
---

The Invest Java Server is a Maven project, which compromises multiple modules:

* `invest-depenencies` is a BOM (Bill of Materials) which speciifies the depenendencies for the rest of the project, and for all plugins. Where possible you should use this in your plugins to avoid including dependencies with different versions, thereby creating conflicts.
* `invest-common` is a library of code which is common to both the server and plugins.
* `invest-server` is a multi-module, described below, which contains the Invest applications server.
* `invest-plugin` is a library on which all plugins should depend.
* `invest-plugins` contains a set of Invest Plugins which are largely core plugins required to offer standard functionality.

In addition the `invest-archetypes` directory contains Maven archetypes which help developer get started within plugin creation.

The `invest-server` modulde compromises:

* `invest-server-core` a set of code which which is shared by server modules, but not with plugins.
* `invest-server-app` the runnable application and its configuration.
* `invest-server-data` a plugin which provides the data services to the application. TODO: Move to an actual plugin!
* `invest-server-libs` bundles a set of third party library dependencies (so that other plugins don't have too, thus keeping size of individual plugins down)
