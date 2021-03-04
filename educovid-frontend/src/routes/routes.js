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
import LoginPage from "../pages/LoginPage";

function Routes(props) {
  const { userData, loggedIn } = props;
  const history = useHistory();

  return (
    <BrowserRouter>
      <Switch>
        <Route exact path="/login">
          {loggedIn ? <Redirect to="/" /> : null}
          <LoginPage
            onLogIn={(userData) => {
              props.dispatch(logIn(userData));
            }}
          />
        </Route>
        <Route exact path="/register">
          <Register history={history} />
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
