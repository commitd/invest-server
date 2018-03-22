---
id: ui.live-development-plugin
title: "Live development plugin"
date: "2017-10-20"
order: 2300
hide: false
draft: false
---

The Live Development Plugin is a pair of Invest plugins which helps developers develop and test their UI plugins.

The Live Development Plugin works in a very simple manner. Its UI Plugin provides an empty entry with no actions. It points the Outer Frame to display a specific URL. That URL is managed by the Live Development API plugin. It redirects requests from that URL (and certain others) to Port 3001.

## Setup

If you are using `create-react-app` to develop your plugins, it will, by default, host its hot reloading, development version of the site on port 3000. In order to change this to 3001 add `PORT=3001` to the `start` script in `package.json`. For a create react typescript app the relevant line will now look like: 

```json
    "start": "PORT=3001 react-scripts-ts start",
```

Running the plugin with `yarn start` will run a the development version on 3001. Reloading the browser and selecting the Live Development Plugin will display the plugin's UI within the app. This should be the same view as [http://localhost:3001](http://localhost:3001), though of course the later does not have the Outer Frame around it.

## Why?

The reason the Live Development Plugin is useful is that it allows the developer to exercise the Invest Plugin API. When running outside of the Outer Frame (on http://localhost:3001) the Invest Plugin library attempts to mimick some of the Outer Frame's responsibilities but certain functions it can not - for example navigation. 

On a more basic note the Live Development Plugin allows the developer to see the UI in place, checking it displays correctly and as expected within the frame.

## Limitations

There are of course limitations to this. The most specific issue is that the UI Plugin does not offer any actions. Thus the actions of the plugin will not be seen in other plugins.

Another issue is that that Live Development Plugin proxies only certain paths and HTTP mechanisms, etc to port 3001. It does not support WebSockets for example. Some plugins or some development environments (eg WebPack) use this to gather data or for notification of live reloading / hot deployment.

As with any iframe development, you will find that your browser development support may be weaker than for the outer frame. For example, you may not be able to inspect the React components in the Live Development Plugin. 