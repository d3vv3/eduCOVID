# Project styling


## What is it for

Styling is needed to provide our project a consistent look through every page\
and component.


## How has it been solved

In order to develop faster components, we will be using this tools:
* [Bootstrap](https://react-bootstrap.github.io/components/alerts)
* [SASS](https://sass-lang.com/)

We are using **bootstrap** because it will provide prebuilt good looking
components such as buttons, dropdown menus, form fields and so.

We are using **SASS** to be able to use functions and variables within our
styling, since it will be much easier for development. It also provides comments
inside the style sheets.  
Some functions (or mixins as called within SASS) created inside
`scr/styles/_functions.scss` are:
* `flexCenter()` which allows to center items in the container or website using flex.
* `imageCenter()` to center an image inside its parent.
* `nordButton()` that customizes buttons for our ongoing color theme.

Regarding the app theme and color, inside `scr/styles/_variables.scss` we have
created a few variables in order to make consistent the app's look.

Because of the structure and order inside `scr/styles/styles.scss`, functions and
variables are reachable from every other style sheet.


## Disclaimers

None.


## References

Tree of the folders related to this documentation.

```
src/styles
├── _app.scss
├── _functions.scss
├── _mainpage.scss
├── _passwordstrength.scss
├── _register.scss
├── style.scss
└── _variables.scss
```
