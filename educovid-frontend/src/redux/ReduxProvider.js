// Redux related
import { createStore } from "redux";
import { Provider } from "react-redux";
import GlobalState from "./reducers";

// Constants
import { initialState } from "../constants/constants";

import React from "react";
import App from "../App";

export default function ReduxProvider(props) {
  const store = createStore(GlobalState, initialState);

  return (
    <Provider store={store}>
      <App store={store} />
    </Provider>
  );
}
