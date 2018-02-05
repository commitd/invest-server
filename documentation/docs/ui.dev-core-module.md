---
id: ui.dev-core-module
title: "Advanced: Developing a core module"
date: "2017-10-20"
order: 2800
hide: false
draft: false
---

If you want to add a new core module to you should probably do so within a fork of the invest-ui workspace itself. 

We'd suggest that you copy an existing core module, delete its `src/` folder and edit its `package.json`. That way you have the various `build`, `watch`, ... scripts already and the versions will match.

You should add your new module to the workspace `package.json` and 

Obviously you'll want to use your new core module in your application, so you should update your `invest-app` or other plugin.


