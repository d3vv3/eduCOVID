import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";

function Teaching(props) {
  return (
    <div>
      <ActionBar />
      <h1>Gestionar docencia en desarrollo</h1>
    </div>
  );
}

export default withRouter(Teaching);
