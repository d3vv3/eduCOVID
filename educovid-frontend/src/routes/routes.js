import React from "react";

// Redux related
import { connect } from "react-redux";
import { logIn } from "../redux/actions";

import {
  BrowserRouter,
  Redirect,
  Route,
  Switch,
  useHistory,
} from "react-router-dom";

// Local imports
import Register from "../pages/Register";
import MainPage from "../pages/MainPage";
import StudentPage from "../pages/StudentPage";
import ProfessorPage from "../pages/ProfessorPage";
import LoginPage from "../pages/LoginPage";

function Routes(props) {
  // Create the history of the user (to go back and forth from the browser or
  // from the app itself by pushing routes)
  const { userData, loggedIn } = props;
  const history = useHistory();

  return (
    <BrowserRouter>
      <Switch>
        {
          // Create a route for every page with a given path
        }
        <Route exact path="/register">
          <Register history={history} />
        </Route>
        <Route exact path="/login">
          {loggedIn ? <Redirect to="/" /> : null}
          <LoginPage
            onLogIn={(userData) => {
              props.dispatch(logIn(userData));
            }}
          />
        </Route>
        <Route path="/student/:studentId">
              <StudentPage history={history}/>
            </Route>
        <Route path="/professor/:professorId">
          <ProfessorPage history={history} />
        </Route>

        <Route path="/">
          <MainPage />
        </Route>
      </Switch>
  </BrowserRouter>
  );
}

function mapStateToProps(state) {
  return { ...state };
}

export default connect(mapStateToProps)(Routes);
