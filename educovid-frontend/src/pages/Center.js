import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";

function Center(props) {
  return (
    <div>
      <ActionBar />
      <h1>Gestionar centro en desarrollo</h1>
    </div>
  );
}

export default withRouter(Center);
