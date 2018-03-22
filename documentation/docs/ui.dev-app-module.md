---
id: ui.dev-app-module
title: "Advanced: Developing a new main application frontend"
date: "2017-10-20"
order: 2800
hide: false
draft: false
---

The `invest-app` is the main 'outer' application, but its functionality in really is held in the common and core modules. Thus you can think of the `invest-app` as an example of using these modules to build a front end.

If you don't like the `invest-app` user experience, for example you prefer to have plugin navigation at the top, you can create your own `invest-myapp` and create a new layout.

Building your own application is a good way of introducing very specific functionality, such as social authentication. 

You can start a new app module either by copying the existing invest-app and modifying, or by running a new `create-react-app` and then reintroducting the invest specific dependencies and functionality. Note if you do the later you may end up with different versions of React, or other dependencies. You should standardise on one set (either by upgrading invest modules or more simplify by downgrading the create-react-app versions). 

In either case you should add your new module to the workspace `package.json`, and then run `yarn install` from the workspace directory to set up links (and install any new packages your module is dependant on)