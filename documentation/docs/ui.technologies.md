---
id: ui.technologies
title: "Invest UI Technologies"
date: "2017-10-20"
order: 2201
hide: false
draft: false
---

## Technologies 

We have selected a set of technologies to build the core of the application, but you can use any technology to build new plugins. 

The core of Invest is written in [Typescript](http://typescript.io). We prefered Typesscript over Javascript+Flow since 

The main UI and the components library is [React](http://react.io).

Invest server and UI make heavy use of GraphQL to provide a single query interface for system and data access. On the UI side we use the [Apollo React framework](https://www.apollographql.com/) and the [GraphQL reference implementation](http://graphql.github.io/graphql-js). 

We use [Redux](https://redux.js.org) for state management inside the application. Alongside Redux we use [Redux Actions](https://github.com/reduxactions/redux-actions) and [Redux Sagas](https://redux-saga.js.org/). We use [React Router](https://reacttraining.com/react-router/) for URL routing.

We use [Create React App](https://github.com/facebookincubator/create-react-app) together with the [Typescript React Script](https://github.com/Microsoft/TypeScript-React-Starter) in order to create applications.

We use the [Semantic UI](https://react.semantic-ui.com/introduction) framework throughout for components and styling.

**As a plugin developers, you are free to pick a completely different set of technologies to the above.**