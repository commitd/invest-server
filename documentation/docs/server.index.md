---
id: "server.index"
title: "Invest Server"
date: "2017-10-20"
order: 3000
hide: false
draft: false
heading: true
---

The Invest Server is a Java application, built using Spring Boot 2.

## Output

The Invest Server builds produces an executable JAR which is the Invest Server Application (`invest-server-app`) which you can run.

## Plugins

The Invest concept centred around a modular system, where the majority of functionality is a plugin. A small core of functionality, under `invest-server`, exists as an application runner which loads other plugins.

Plugins can be included in one of two ways:

* They can be bundled inside the `invest-server-app` at compile time as part of a 'fat JAR'. 
* They can be included at runtime through addition to the classpath.

Invest is not a generic plugin system, like say OSGI. It's supports the purpose of including plugins in order to create an extensible server and user interface.

# Spring

In reality the extension system of Invest is thin layer over Spring Boot's existing systems, and Invest Server is a Spring Boot application.

We don't hide or abstract this from the developer, and teh developer is free (and has to) to use Spring constructs.


