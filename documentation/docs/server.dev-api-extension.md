---
id: server.dev-api-extension
title: "Developing API extension"
date: "2017-10-20"
order: 3500
hide: false
draft: false
---

An API extension provides a HTTP endpoint for the UI or other applications to access. 

The recommendation for Invest plugins is that APIs are avoided in preference for GraphQL which provides a more standardised and flexible approach to composition. However if you do wish to create an API when you can still do so. 

There are no restrictions or controls on the API creation. Simply [create a plugin](server.extension-points.html) using [Maven](server.dev-maven.html) and then use the standard Spring Boot approach to create your API. We suggest you place your API under `/api/{unique-plugin-id}` however this is not mandated.

Note that Invest uses [Spring WebFlux](https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html).

*When implementing your API you are responsible for all security concerns.*