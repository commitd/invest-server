---
id: ui.faq
title: "FAQ"
date: "2017-10-20"
order: 2900
hide: false
draft: false
---


## Why iframes and not just sub components? The RPC layer adds overhead, and I need to reimport libraries, etc...

The iframe model provides isolation between the environments which offers a degree of security and stability between the core applcation and its plugins. 

Its also a simpler model for plugin developers because it is a blank canvas - there are no existing libraries or function which could clash, no CSS, etc. If the plugin developer wants a standard look and feel they can achieve that by reusing the same libraries as the core application.

## I have a UI application I'm trying to wrap in a plugin, but it doesn't like iframes.

Some UI's do have iframe detection and will refuse to run in an iframe if that is the case. This is usually for security reasons to prevent 'hijacking'.

There's nothing we can do above that (as far as we know), so your options are:

* Discuss with the owner of that application if they have an option to disable the check.
* Create a plugin which is a link to the other tool and opens it in another window. You lose the benefits of being inside Invest, but at least your users have a single start point to work from.

## Why GraphQL within the application? 

We use GraphQL for the invest-server to invest-ui communication as it was devised. Obviously it's very powerful from a flexibility and efficiency perspective, but it has two nice engineering properties as well:

* It offers a single endpoint which means it can be treated as a 'single special case'. We just need to write the routing for a single call in the framework.
* The content of that endpoint is a opaque to the framework, we can leave the plugins to require whatever they need from the endpoint. We don't know or care what that is. 

We needed an API on the core application which the plugins could call upon. We had the same set of issues as with the server, and so GraphQL was an obvious choice.

## If GraphQL is so good, why not have an GraphQL interface on each plugin that the core application can call on? 

The core application is in effect a 'GraphQL server' which the plugin can use GraphQL to get data or make requests.

Going the other way we could have elected to use GraphQL again, but then the core application has little reason to query the plugin (and certainly not in as flexibly a manner as GraphQL would offer).

The largest drawback is that the plugin implemention would require a graphql backend. We felt this was asking too much of plugins developers - they should focus on building their own plugin and the framework should be transparent to them.

## Why to I need to use 'fetch' from the invest API (over RPC) rather than calling it myself? 

You can call it yourself, but it depends on a range of factors and you probably don't want to call it yourself. 

Firstly due to the sandboxing model of iframe your plugin is not in the same origin at the core application. Thus it can't communicate with the server (depending on your server security setting around same-origin, CORS, etc).

Secondly, the plugin is unaware of the details of authentication, which is managed by the core application. The `invest fetch` passes the fetch command over RPC, where the core applicaiton can add that information.



