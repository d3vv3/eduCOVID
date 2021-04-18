import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Bootstrap imports
import Card from "react-bootstrap/Card";

// import { students } from "../tests/prueba";
import { backUrl } from "../constants/constants";

import Notifications from "../components/Notifications";

function StudentPage(props) {

  const { history, userData } = props;

  const [studentId] = useState(userData.id);
  const [studentName, setStudentName] = useState(userData.nombre);
  const [studentState, setStudentEstadoSanitario] = useState(userData.estadoSanitario);
  const [studentGroup, setStudentGroup] = useState();
  const [studentClass, setStudentClass] = useState();

  let groupDownloaded = false;
  let classDownloaded = false;
  useEffect(() => {
    let isMounted = true;
    const callGroup = async () => {
      try {
        const groupRes = await fetch(backUrl + "/grupo/alumno/" + studentId);
        let groupData = await groupRes.json();
        if (isMounted) setStudentGroup(groupData);
        console.log(groupData);
        groupDownloaded = true;
      } catch (e) {
        // Nothing to do
      }
    };
    const callClass = async () => {
      try {
        const classRes = await fetch(backUrl + "/clase/alumno/" + studentId);
        let classData = await classRes.json();
        if (isMounted) setStudentClass(classData);
        console.log(classData);
        classDownloaded = true;
      } catch (e) {
        // Nothing to do
      }
    };
    callClass();
    callGroup();
    return () => { isMounted = false };
  }, [studentId]);

  return (
    <div className="student-page-container">
      <Notifications userId={studentId} role="alumno" />
      <div className="centered-div">
        <h4>Alumno/a</h4>
        <h1>{studentName}</h1>
        <h2 className={studentState.toLowerCase() === "confinado" ? "bad" : "good"}>
        {studentState.toLowerCase() === "confinado" ? "Confinado" : "No confinado"}
        </h2>
            <p className="description">
             {studentGroup?.estadoSanitario.toLowerCase() === "confinado" ||
                studentGroup?.estadoDocencia.toLowerCase() === "online" ||
                studentState.toLowerCase() === "confinado"
                ? "Debe recibir clase de manera online" : "Debe recibir clase de manera presencial"}
            </p>

            <Card className={studentGroup?.estadoSanitario.toLowerCase() === "confinado" || studentGroup?.estadoDocencia?.toLowerCase() === "online"
               ? "card-header-bad" : "card-header-good"} as={Card.Header}>
                  {studentClass?.nombre} - {studentGroup?.nombre} - Docencia: {studentGroup?.estadoDocencia.toLowerCase() === "online" ? "Online" : "Presencial"}
            </Card>
      </div>
    </div>
  );
}

export default withRouter(StudentPage);
