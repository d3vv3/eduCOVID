import React from "react";

import { BrowserRouter, Route, Switch, useHistory } from "react-router-dom";

// Local imports
import Register from "../pages/Register";
import MainPage from "../pages/MainPage";
import ProfessorPage from "../pages/ProfessorPage";

function Routes(props) {

  // Create the history of the user (to go back and forth from the browser or
  // from the app itself by pushing routes)
  const history = useHistory();

  return (
    <BrowserRouter>
        <Switch>

            // Create a route for every page with a given path
            <Route exact path="/register">
                <Register history={history}/>
            </Route>

            <Route path="/professor/:professorId">
                <ProfessorPage history={history}/>
            </Route>

            <Route path="/">
                <MainPage />
            </Route>

        </Switch>
    </BrowserRouter>
  );
}

export default Routes;
