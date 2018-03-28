---
id: ui.dev-ui-extension
title: "Developing UI modules"
date: "2017-10-20"
order: 2500
hide: false
draft: false
---

Invest plugins are packaged as JAR files and deployed through the Invest Server. Invest UI Plugins are not different. As such they have `two project`, the first is the web project (written for example in Javascript or Typescript, HTML, CSS) and the second is a Java module which defines the plugin.

Here we will discuss the process from the Web perspective in detail, assuming a modern web development stack based on create-react-app. The Java side is covered [elsewhere](server.dev-ui-extension.html), which uses a very simple file based `index.html` with `script` tags to illustrate the process.

If you are developing UI component you should read both, but perhaps start with the one that corresponds to your web development approach.

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

**TODO : Check type dfn on published files...**

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

## Wrapping in a Java module

We have developed and tested out application as far as we can, but its not packaged as a Invest JAR plugin. Therefore we can only access it inside the application through the Live Development Plugin.

Rather than repeat the instructions here, we refer to the [Server Java UI extension](server.dev-ui-extension.html) page. There we create a plugin of the same name, but with an index.html as a static Java resource (in src/main/resources).

When using create-react-app or similar the `yarn build` process creates the index.html (and associated files). Therefore we can omit that stage of the Java UI extension guide and replace it with something that copies over the relevant files from the `yarn build` output. The `maven-resources-plugin` does that.

We'll assume that you have created a directory structure file:

```bash
js/
 invest-ui-myplugin/ <- package.json here    
java/
 invest-ui-myplugin/ <- pom.xml here 
```


To the Maven pom add:

```xml

<build>
   <plugins>
     <!-- Copy the UI build output to the resources area on the classpath -->
     <plugin>
       <artifactId>maven-resources-plugin</artifactId>
       <version>3.0.2</version>
       <executions>
         <execution>
           <id>copy-resources</id>
           <!-- Run the copy After the compilation, but before we package
             the JAR up -->
           <phase>process-classes</phase>
           <goals>
             <goal>copy-resources</goal>
           </goals>
           <configuration>
             <outputDirectory>${project.basedir}/target/classes/ui/invest-ui-myplugin</outputDirectory>
             <resources>
               <resource>
                 <directory>../../js/invest-ui-myplugin/build/dist</directory>
                 <filtering>false</filtering>
               </resource>
             </resources>
           </configuration>
         </execution>
       </executions>
     </plugin>

   </plugins>
 </build>

 ```

Running the following will do a complete build:

```bash
cd js/invest-ui-myplugin
yarn build
cd ../..
cd java/invest-ui-myplugin
mvn clean package
```

The result will be a JAR file in the `ava/invest-ui-myplugin/target` folder which contains the complete application.

## A note on different directory structures

If you are developing a UI plugin and have to Javascript and Java projects, how is it best to organise your code? It depends a little on how you view your plugins.

If you consider each plugin to be standalone, then likely you have it within a separate source code repository. This has the benefit that each plugin is separate and contained, independently versioned, and it is easier to trigger deployment or testing against commits. However it can lead to a lot of small repositories.

In this case there are several way to consider organising your code:

* Consider maven as the super project, as it wraps the javascript. In this case place the web project under src/main/web (alongside src/main/java). The build process can be fully automated through maven to first build the src/main/java, then copy the result from src/main/web/build into the src/main/resources/ui/invest-ui-myplugin (or into target/classes/ui/invest-ui-myplugin directly).
* Spilt the root folder into java and js subdirectories. Create a build script to first build JS, copy the resources, and then build Maven.

If you have many plugins you might place them in the same repository. If you do you might either:
* Have a folder for each plugin; or
* Separate the java from the javascript projects, that is have a top level java directory and another web directory. Each will have a 'invest-ui-myplugin' folder one will be a maven module for the java aspect and the other yarn/npm module for the web/js aspect.

If you adopt the former approach then this really is no different to the 'repo per plugin' method as above. If you adopt the latter, then you have the benefits of being able to use yarn workspaces/lerna, plus Maven parents to orchestrate and link all your plugin builds - which better dependency and interdependency control.

They are no right or wrong answers here, however we have found:

* It is tedious to work with many repositories, especially for very small plugins when they form part of a larger application.
* Using maven/gradle as a build process for Javascript seems attractive but requires more thought and configuration than a simple build shell script.
* It is nice to have the javascript separate (and obvious) so that 'yarn start' can be run (rather than hiding this within a maven wrapper)


