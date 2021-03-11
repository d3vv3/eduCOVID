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

  const routesMapper = {
    alumno: "student",
    profesor: "professor",
    responsable: "", // TODO: Dont know the route yet
  };

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
          {loggedIn ? (
            <Redirect to={`/${routesMapper[userData.role]}/${userData.id}`} />
          ) : null}
          <LoginPage
            onLogIn={(userData) => {
              props.dispatch(logIn(userData));
            }}
          />
        </Route>
        <Route path="/student/:studentId">
          {!loggedIn ? <Redirect to={`/login`} /> : null}
          <StudentPage history={history} />
        </Route>
        <Route path="/professor/:professorId">
          {!loggedIn ? <Redirect to={`/login`} /> : null}
          <ProfessorPage history={history} />
        </Route>
        <Route path="/">
          <MainPage loggedIn={loggedIn} />
        </Route>
      </Switch>
    </BrowserRouter>
  );
}

function mapStateToProps(state) {
  return { ...state };
}

export default connect(mapStateToProps)(Routes);
