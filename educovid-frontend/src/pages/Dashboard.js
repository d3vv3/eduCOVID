import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";

function Dashboard(props){
  return (
    <ActionBar />
  );
}

export default withRouter(Dashboard);
