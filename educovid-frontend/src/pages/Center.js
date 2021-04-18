import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";

function Center(props){
  return(
    <div>
      <h1>Gestionar centro en desarrollo</h1>
      <ActionBar />
    </div>
  );
}

export default withRouter(Center);
