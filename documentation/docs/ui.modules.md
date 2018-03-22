---
id: ui.modules
title: "Invest UI Modules"
date: "2017-10-20"
order: 2200
hide: false
draft: false
---


## Modules

A module is a code project (application or library) within the Invest UI. 

We make the distinction between a core module and a plugin module:

* An application module, which forms the application, runs in the same Javascript enviroment as the other core modules.
* A plugin runs in a separate iframe, thus is isolated from the core modules and other plugins. A plugin contributes a view. Each plugin is a self contained web app in its' own right.
* A common library being a lower level library which could be used by either or both applications or plugins

The invest-ui project is a [Yarn workspace](http://yarn.io/workspaces), collecting the modules into a single repository. This has the benefit of sharing a single node modules directory (thus saving disk space) and also allowing automaticing linking between each module and its module dependencies.

## Organisation

The common modules are:

* `invest-common`: Contains basic shared code between the plugin and core
* `invest-components`: Contains a set of reusable React components
* `invest-graphql`: Wraps GraphQL for use within Invest, via the RPC layer.
* `invest-redux`: Helper functions for working with redux.
* `invest-rpc`: The remote procedure call framework used to communicate between core and plugin.
* `invest-types`: Shared types between the UI and server.
* `invest-utils`: Provides some utility functions.

The application specific modules are:

* `invest-framework`: Contains code which is specific to the application, and is not required by plugins
* `invest-app`: Is the embodiment of the outer application.

The plugins specific modules are:

* `invest-plugin` module on which a plugin module can depend in order to integrate with the outer framework.

As an example of a plugin module, we have the toy `example-ui` module.
