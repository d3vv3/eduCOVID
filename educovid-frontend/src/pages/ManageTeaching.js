import React, { useState } from "react";
import { withRouter } from "react-router-dom";

// Local components
import ActionBar from "../components/ActionBar";

// Local pages
import ManageClass from "./ManageClass";
import ManageGroup from "./ManageGroup";

function ManageTeaching(props) {
  const { onLogOut, userData } = props;

  const [page, setPage] = useState("class");
  const [selectedClass, setSelectedClass] = useState({});

  return (
    <>
      <ActionBar
        onLogOut={() => {
          onLogOut();
        }}
      />
      {page === "class" ? (
        <ManageClass
          userData={userData}
          setPage={setPage}
          setSelectedClass={setSelectedClass}
        />
      ) : (
        <ManageGroup
          userData={userData}
          setPage={setPage}
          setSelectedClass={setSelectedClass}
          selectedClass={selectedClass}
        />
      )}
    </>
  );
}

export default withRouter(ManageTeaching);
