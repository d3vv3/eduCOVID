import React from "react";

import { BrowserRouter, Route, Switch, useHistory } from "react-router-dom";

// Local imports
import Register from "../pages/Register";
import MainPage from "../pages/MainPage";

function Routes(props) {

  const history = useHistory();

  return (
    <BrowserRouter>
        <Switch>
            <Route exact path="/register">
                <Register history={history}/>
            </Route>
            <Route path="/">
                <MainPage />
            </Route>
        </Switch>
    </BrowserRouter>
  );
}

export default Routes;
