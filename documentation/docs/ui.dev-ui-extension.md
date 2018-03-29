---
id: ui.dev-ui-extension
title: "Developing UI modules"
date: "2017-10-20"
order: 2500
hide: false
draft: false
---



This section discusses the UI as being hosted served directly from the file system by Invest, specifically the Invest Ui Host plugin.  Here we will discuss the process from the Web perspective in detail, assuming a modern web development stack based on create-react-app. 

In its simplest form an UI extension need  meet only two conditions. It must have:

* An `index.html` which will be the initial file displayed when the plugin is viewed
* An `invest.json` which defines some metadata about the plugin.

Rather than use the file system hosting, you may also host the UI through its own Java module which is covered [elsewhere](server.dev-ui-extension.html). That example uses a very simple file based `index.html` with `script` tags to illustrate the process. If you are developing UI component you should read both, but perhaps start with the one that corresponds to your web development approach.

## Options for create a plugin

You can create the `index.html` in any way. It has no prescribed format or content. 

We'd recommend using `create-react-app` and specifically the Typescript variant [create-react-ts](https://github.com/Microsoft/TypeScript-React-Starter). Using this approach you can take advantages of modern Javascript/Typescript approach such as yarn package management, typing, etc. You can use variant `invest-*` libraries in your applications. Typically you wish to include `invest-components`, `invest-types` and `invest-plugins`.  We'd recommend you read the Ketos documentation for more example of building plugins in this manner.

Alternatively you can craft the `index.html` as a traditional web page. In this case you will want to to include the `invest.js` file which is hosted on the server and gives you access to some of the functions that the various `invest-*` libraries have. You can include these in your index.html via the `script`/`link` tags:

```html
<html>
    <head>
        <!-- ALl of these are automatically hosted for you by the server -->
        <link rel="stylesheet" type="text/css" href="/ui/libs/semantic-2.2.css">
        <script src="/ui/libs/react-16.0.js"></script>
        <script src="/ui/libs/react-dom-16.0.js"></script>
        <script src="/ui/libs/semantic-2.2.js"></script>
        <script src="/ui/libs/jquery-3.2.js"></script>
        <script src="/ui/libs/invest.js"></script>

        <!-- You have have other scripts/css you can just place them alongside your index.html -->
    </head>
    <body>

        <h1>My plugin</h1>

        <script>
            // Your javascript code could go here... 
        </script>

    </body>
</html>
```

## Which web development approach?

Invest does not mandate a particular web framework for developers. However it is native support is for React.

Whilst there is no reason that Angular or others could not be used in Invest we'll stick to Typescript and React in this guide.

## Creating a project

Ensure you have create-react-app installed:

```bash
npm install -g create-react-app
```

then create a new project (we've called it invest-ui-myplugin but you should pick something different):

```bash
create-react-app invest-ui-myplugin --scripts-version=react-scripts-ts
cd invest-ui-myplugin
```

In order to use the live Development Plugin later, in `package.json` add `PORT=3001` to the front of the `start` script.

```bash
   ...
"scripts": {
   "start": "PORT=3001 react-scripts-ts start",
   ...
```

Add the `invest-plugin` project as a dependency, and we'll use `semantic-ui-react` with its default CSS:

```
yarn add invest-plugin semantic-ui-react semantic-ui-css
```

You should now have a working web app, which you can test running visiting:

```bash
yarn start
```

which should start the application in development mode and open a browser at http://localhost:3001/.

You might see an error:

```bash
...invest-ui-myplugin/node_modules/@types/graphql/subscription/subscribe.d.ts
(17,4): error TS2304: Cannot find name 'AsyncIterator'.
```

If so, stop the app, edit `tsconfig.json` and add to the compilerOptions.libs array 'esnext':

```json
{
 "compilerOptions": {
   "outDir": "build/dist",
   "module": "esnext",
   "target": "es5",
   "lib": ["es6", "dom", "esnext"],
   "sourceMap": true,
   ...
```

Then run `yarn start` again.

## Making a hello world application

As convention dictates, we'll create a HelloWorld plugin. We can start clearing out the boilerplate form create-react-app:

* Delete App.css
* Delete App.tsx.test
* Amend App.tsx to read:

```ts
import * as React from 'react';

class App extends React.Component {
 render() {
   return (
     <div>
       <p>Hello world</p>
     </div>
   );
 }
}

export default App;
```

Now in `index.tsx` we'll add our CSS for `semantic-ui`, add near the top:

```ts
import 'semantic-ui-css/semantic.min.css'
```

Then you can drop in a replacement `ReactDOM.render()`, at the bottom of the file:

```ts
import { InvestUiPlugin } from 'invest-plugin'

ReactDOM.render(
 <InvestUiPlugin>
   <App />
 </InvestUiPlugin>,
 document.getElementById('root') as HTMLElement
)
```

Check back in the browser to see that 'Hello World' is displayed.

Run Invest Server with the live development plugin. Clicking on the Live Development plugin will show the same Hello World.

## Interacting with the server, using GraphQL

Let's request some data form the server. However as the Invest Server may have no exciting plugins etc, let's just one of the basic inbuilt GraphQL queries.

Our query will be:

```graphql
query {
 applicationSettings {
   title
 }
 }
```

Which will return JSON:

```json
{
 "data": {
   "applicationSettings": {
     "title": "Invest"
   }
 }
}
```

Let's create a Hello.tsx which will display 'Hello from Invest' using the title from above:

```typescript
import * as React from 'react'
import { graphql, gql, QueryProps } from 'react-apollo'


// Mirror our GraphQL result in a type safe interface
interface Response {
   applicationSettings: {
       title: string
   }
}

// In typescript we need to be very clear about types of props
// we say here that graphQL is going to provide us with a data field
// and that it'll have the GraphQL QueryProps (ie loading, refetch())
// plus the stuff in Response defined above
interface Props {
   data?: QueryProps & Partial<Response>
}

class Hello extend React.Component<Props> {
   render() {
       const { data } = props

       // Check if we have something to display...
       if (!data || data.loading || !data.applicationSettings) {
           // Display a placeholder...
           // In a real app this would be a spinner / loader
           return 'Waiting for who I am...'
       }

       // if we do, they print it
       return (
           <p>Hello from {data.applicationSettings.title}</p>
       )
   }
}

// Define the GraphQL query we want
const TITLE_QUERY = gql`
query GetTitle {
 applicationSettings {
   title
 }
}
`

// Use the graphql function to wrap our component
// Apollo will run our TITLE_QUERY and then provide the result
// in the data field in our props.
export default graphql<Response, {}, Props>(TITLE_QUERY)(container)

```

It's perhaps worth noting here that this GraphQL query is connected to our server through the RPC layer and the Outer Frame when run inside Invest and when run outside Invest (localhost:3001) we pass it directly to the server.

Now we change `App.tsx` to use our Hello component:

```typescript
import * as React from 'react';

// New:
import Hello from './hello'

class App extends React.Component {
 render() {
   return (
     <div>
       <Hello />
     </div>
   );
 }
}

export default App;
```

If you look at the browser now (http://localhost:3001 or the live development plugin) you should see 'Hello from Invest' (or whatever your app is called). If you refresh you might see the 'Waiting for who I am...' message flicker.

If you really want to see it say that in the http://localhost:3001 tab, use the debug view to place a breakpoint in the render function of Hello.tsx.



## Adding an action

Our plugin doesn't have any actions itself, but perhaps it could be passed the name of someone to say hello to.

The way a InvestUiPlugin React component receives an action is through its props. Lets change App.tsx to respond to those props.

```typescript

import { ChildProps } from 'invest-plugin'

// Store the name to say hello to in state
interface State {
   name?: string
}

// Our action is 'hello', but it has a payload which
// (may) contains the name
interface NamePayload {
   name?: string
}

// We add props and State types now.
// ChildProps are the Props that InvestPlugin will give to its
// React child.
class App extends React.Component<ChildProps, State> {
  state: State = {
     // Start with no name
     name: null
 }

 componentWillReceiveProps(nextProps: Props) {
   // Process action / payload if its changed
   if (this.props.action !== nextProps.action || !isEqual(this.props.payload, nextProps.payload)) {
  
     // If we support the action deal with it
     if(this.props.action === 'hello') {
       // Payload is any, but we know it must be a view payload now
       const payload = nextProps.payload as NamePayload
       this.setState({
           name: payload ? payload.name : undefined,
       })
     }
   }
 }
  render() {
   const { name } = this.state

   // Add a second line, but is name is null/undefined/etc
   // just say 'whoever'
   return (
       <div>
           <Hello />
           <p>Nice to meet you, {name || 'whoever you are'}.</p>
       </div>
       );
   }
 }

}
```

If we view this in the Live development plugin we'll just see the extra line but no name. That's because we've not triggered the action.

You can use the Action Development Plugin to send an action to a Plugin. Select this, then select the Live Development Plugin. In the action textfield enter 'hello' and in the payload enter

```json
{
   "name": "John Smith"
}
```

Click Send to test the action, and the Live Development Plugin should reappear, with 'John Smith' now the name.


## Build the UI plugin

So far we've been running the plugin live, but let's build a production version. Stop the development version (Ctrl+C) and then run

```bash
yarn build
```

## Invest.json

The invest.json has a standard layout of fields (defined effectively by `PluginJson` class in `invest-ui-plugin-host`):

```json
{
    "id": "unique_plugin_id",
    "name": "Friendly name",
    "description": "Short description",
    "icon": "user",
    "roles": ["USER", "DEV", "ADMIN"],
    "actions": [
                {
            "title": "Short name",
            "description": "What is does",
            "action": "the.action.name"
        }
    ]
}
```

Here `"id"` should uniquely reference your plugin. It should be URL safe so we'd suggest using lower case alphanumerical charactoers together with - (kebab case).  

The `"name"` and `"description"` are displayed in the menu sidebar. The name alone should be very clear to the user, but the description allows a little more information to help the user understand what the view will display.

The `"icon"` is a [Semantic UI icon](https://react.semantic-ui.com/elements/icon) which will be displayed next to the name in the side bar. 

The `"roles"` specify which user roles should be plugin be displayed to. Typically this will be left empty, meaning everyone. Certain plugins will be for DEVelopers or ADMINistrators only.

The `"actions"` section declare which actions can be sent to this plugin. An action in an indication that the plugin should so something in response to input. An action has a payload which carries information about tha action, for example the document to display or the user id to edit. The `"title"` and `"description"` of the action will be displayed to users before they click the action (in another plugin), so should be clear on the result of clicking that action. They might say "Edit user" for example. The `"action"` field in `invest.json` is the string constant. It is freeform, defined by your application needs and your plugins. Obviously the calling plugin and the receiving plugin need to use the same string. Typically the actions are of the form `datatype.method` such as `document.view` or `user.edit`. See the Ketos documentation for some illustrative examples of real world actions.

In the above example we had an action called `hello`. Our `invest.json` might look like:

```json
{
    "id": "hello",
    "name": "Hello",
    "description": "Displays hello",
    "icon": "comments",
    "roles": [],
    "actions": [
                {
            "title": "Hi",
            "description": "Hi to you",
            "action": "hello"
        }
    ]
}
```

Your build system will determine where you place your `invest.json`. It needs to be configured such that it is output to the same directory as your `index.html`. For `create-react-app` type projects place it in the `public/` directory.

