import React from "react";

// Local imports
import Routes from "./routes/routes";
import Notifications from "./components/Notifications";

// Style
import "./styles/style.scss";

function App() {
  return (
    <div className="App">
      <Notifications />
      {
        // Render routes, which will render based on the given path or url
      }
      <Routes />
    </div>
  );
}

export default App;
