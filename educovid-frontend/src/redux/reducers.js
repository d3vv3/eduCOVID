// Redux
import { combineReducers } from "redux";

// Constants
import { LOG_IN, LOG_OUT } from "../constants/constants";

function loggedIn(state = false, action) {
  switch (action.type) {
    case LOG_IN:
      return true;
    case LOG_OUT:
      return false;
    default:
      return state;
  }
}

function userData(state = null, action) {
  switch (action.type) {
    case LOG_IN:
      return action.payload.userData;
    case LOG_OUT:
      return null;
    default:
      return state;
  }
}

const GlobalState = combineReducers({
    loggedIn,
    userData
});

export default GlobalState;