import React from "react";

import { BrowserRouter, Route, Switch } from "react-router-dom";

// Local imports
import Register from "../pages/Register";
import MainPage from "../pages/MainPage";

function Routes(props) {
  return (
    <BrowserRouter>
        <Switch>
            <Route exact path="/register">
                <Register />
            </Route>
            <Route path="/">
                <MainPage />
            </Route>
        </Switch>
    </BrowserRouter>
  );
}

export default Routes;
