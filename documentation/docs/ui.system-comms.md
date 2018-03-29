---
id: ui.system-comms
title: "System design: Communications"
date: "2017-10-20"
order: 2140
hide: false
draft: false
---

As the plugin and outer layer are distinct frames, running effectively in two sandboxed environments, they can not save information directly.

Under the browser security model, two frames which are in different *origins* (for securirty) communicate through a message passing interface. The [Window.postMessage function](https://developer.mozilla.org/en-US/docs/Web/API/Window/postMessage) is used to send messages and `window.addEventListener` used to listen for incoming messages.

The message passing system is asynchronous. A message is sent from one iframe to another, where it is received at some point later. There is no link between two messages - no notion of request and response for example. This makes the message system simple but somewhat tedious to work with.

Invest wraps the message passing interface, which a simple RPC layer. This is an implementation of [JSON-RPC 2.0](http://www.jsonrpc.org/specification). It supports notification (one way message, not expecting a response) or request-response. 

Sending either a notification or request returns a promise on the calling side. The promise means slightly different things - for notification it is fulfilled when the message is sent, and for a request is is fulfilled with the response. 

On the recieving side a handler needs to be provided, which will handle messages received. It should return either a value or a promise. The handler will be called with the parameters in order that the caller requested. No checks are made for correctness, etc - this is the responsibility of the handling function.

The message API has some limitations of what can be passed between frames. We very much recommend that only simple Javascript primitives (string, number, array and object) are used and specifically not functions, promises, etc. Beware that some objects have complex and unserialisable types nested in them (for example HTTP responses).

The connection is tested via a ping/pong handshake until it is ready. This usually happens within a 100ms, but until then any messages to be sent are queued so the application and plugins can ignore the delay and make requests immediately. The queued messages are delivered in recieved order.

