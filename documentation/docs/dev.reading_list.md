---
id: dev.reading-list
title: "Reading List"
date: "2017-10-20"
order: 20
hide: false
draft: false
---

Invest uses a lot of standard technologies and practises. We recommend you familiarise yourself with the following before starting:

* [GraphQL](http://graphql.org/) is a query language which provides the API between the UI and the server.

If you want to work on the server side:

* [Java 8](https://www.oracle.com/uk/java/index.html) is the language used on the server side.
* [Spring Boot](https://projects.spring.io/spring-boot/), is the underlying framework and specifically we use Webflux and Spring Data.
* [GraphQL SQPR](https://github.com/leangen/graphql-spqr) which is an annotation based approach to creation of GraphQL services in Java.
* [Project Reactor](https://projectreactor.io/) and in particular the use of `Flux` and `Mono`.
* [Maven](https://maven.apache.org/) for server side dependency management and building plugins.

If you want to work on the UI, we'd suggest reading about:

* [Yarn](https://yarnpkg.com/lang/en/) is used for dependency packaging our UI 
* [React](https://reactjs.org/) is a library for building user interfaces for the Web.
* [Typescript](https://www.typescriptlang.org/) is an Javascript like language adding classes and interfaces.
* [React Semantic UI](https://react.semantic-ui.com) is a library of a styled UI components for React.
* [Apollo and React-Apollo](https://www.apollographql.com) is a Javascript client for working with GraphQL.

On the UI side you don't have to use these, but we do! Note that UI plugins also have a small Java and Maven component.

## Developing

In order to develop you require the following pre-requisites to be installed:

* [Java 8](https://www.oracle.com/uk/java/index.html)
* [Maven](https://maven.apache.org/)
* [Yarn](https://yarnpkg.com/lang/en/), though it is worth also installing [NodeJS and NPM](https://nodejs.org/en/)
* [Lerna](https://lernajs.io/)
* [Git](https://git-scm.com/) 

You are likely to want to access existing data in a supported database, as such you should also install and setup the appropriate database for example:

* [Mongo 3.4.4 or above](https://www.mongodb.com/)
* [Elasticsearch 5](https://www.elastic.co)

Of course you can use whatever deployment approach you want such as local installation, Docker constainers, or cloud services.

You'll probably want to use an IDE, we recommend:

* [Visual Studio Code](https://code.visualstudio.com/) or [Atom](https://atom.io/) for the UI (with appropriate Typescript, etc plugins)
* [Intellij IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/) for server development. We'd recommend the Eclipse based [Spring Tool Suite](https://spring.io/tools) instead of plain Eclipse since it has the additional components needed.
* A Git GUI such as [Sourcetree](https://www.sourcetreeapp.com/), [Github Desktop](https://desktop.github.com/), [etc](https://git-scm.com/downloads/guis/).