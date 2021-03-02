# Routes components


## What is it for

Routing is needed to move between pages of the app, render different components,
generate a history inside the client's browser, etc.


## How has it been solved

We have created a router using `react-router-dom`, which renders a different
page or component based on the route or path within the app:
* `/` takes the user to the component `MainPage`.
* `/register` takes the user to the component `Register`.

The history is also generated in this file, in order to be passed down to other
components. This is important to manager the user's ability to go back and forth
within the app.


## Disclaimers

None.


## References

Tree of the folders related to this documentation.

```
src/routes
└── routes.js
```
