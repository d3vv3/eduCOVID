// Redux
import { combineReducers } from "redux";

// Constants
import { LOG_IN, LOG_OUT, CONFINE_MESSAGE, UNCONFINE_MESSAGE } from "../constants/constants";

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

function confineMessage(state = "", action) {
  switch (action.type) {
    case CONFINE_MESSAGE:
      return action.payload.confineMessage;
    default:
      return state;
  }
}

function unconfineMessage(state = "", action) {
  switch (action.type) {
    case UNCONFINE_MESSAGE:
      return action.payload.unconfineMessage;
    default:
      return state;
  }
}

const GlobalState = combineReducers({
    loggedIn,
    userData,
    confineMessage,
    unconfineMessage
});

export default GlobalState;
