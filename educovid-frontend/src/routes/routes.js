import React from "react";

import { BrowserRouter, Route, Switch, useHistory } from "react-router-dom";

// Local imports
import Register from "../pages/Register";
import MainPage from "../pages/MainPage";
import StudentPage from "../pages/StudentPage";

function Routes(props) {

  const history = useHistory();

  return (
    <BrowserRouter>
        <Switch>
            <Route exact path="/register">
                <Register history={history}/>
            </Route>
            <Route path="/student/:studentId">
              <StudentPage history={history}/>
            </Route>
            <Route path="/">
                <MainPage />
            </Route>
        </Switch>
    </BrowserRouter>
  );
}

export default Routes;
