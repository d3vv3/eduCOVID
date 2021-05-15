import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Bootstrap imports
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";

// import { students } from "../tests/prueba";
import { backUrl } from "../constants/constants";

import Notifications from "../components/Notifications";

function StudentPage(props) {

  const { history, userData, onLogOut } = props;

  const [studentId, setStudentId] = useState(-1);
  const [studentName, setStudentName] = useState("");
  const [studentState, setStudentEstadoSanitario] = useState("");
  const [studentGroup, setStudentGroup] = useState();
  const [studentClass, setStudentClass] = useState();

  useEffect(() => {
    if(userData){
      setStudentId(userData.id);
      setStudentName(userData.nombre);
      setStudentEstadoSanitario(userData.estadoSanitario);
    }
  }, [userData]);

  useEffect(async () => {
    if(studentId != -1){
      try {
        const token =localStorage.getItem('token') || "";
        const groupRes = await fetch(backUrl + "/grupo/alumno/" + studentId, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        const classRes = await fetch(backUrl + "/clase/alumno/" + studentId, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        let groupData = await groupRes.json();
        let classData = await classRes.json();
        setStudentGroup(groupData);
        setStudentClass(classData);
      } catch (e) {
        // Nothing to do
      }
    }
  }, [studentId]);

  // let groupDownloaded = false;
  // let classDownloaded = false;
  // useEffect(() => {
  //   let isMounted = true;
  //   const callGroup = async () => {
  //     try {
  //       const token =localStorage.getItem('token') || "";
  //       const groupRes = await fetch(backUrl + "/grupo/alumno/" + studentId, {
  //         headers: {
  //           'Authorization': `Bearer ${token}`
  //         }
  //       });
  //       let groupData = await groupRes.json();
  //       if (isMounted) setStudentGroup(groupData);
  //       console.log(groupData);
  //       groupDownloaded = true;
  //     } catch (e) {
  //       // Nothing to do
  //     }
  //   };
  //   const callClass = async () => {
  //     try {
  //       const token =localStorage.getItem('token') || "";
  //       const classRes = await fetch(backUrl + "/clase/alumno/" + studentId, {
  //         headers: {
  //           'Authorization': `Bearer ${token}`
  //         }
  //       });
  //       let classData = await classRes.json();
  //       if (isMounted) setStudentClass(classData);
  //       console.log(classData);
  //       classDownloaded = true;
  //     } catch (e) {
  //       // Nothing to do
  //     }
  //   };
  //   callClass();
  //   callGroup();
  //   return () => { isMounted = false };
  // }, [studentId]);

  return (
    <div>
      <div className="logout-button">
        <Button
          className="nord-button"
          variant="primary"
          onClick={() => {
            onLogOut();
            localStorage.removeItem('token');
          }}
        >
          Cerrar sesión
        </Button>
      </div>
      <div className="student-page-container">
        {studentId !== -1 ? <Notifications userId={studentId} role="alumno" /> : null }
        <div className="centered-div">
          <h4>Alumno/a</h4>
          <h1>{studentName}</h1>
          <h2 className={studentState?.toLowerCase() === "confinado" ? "bad" : "good"}>
          {studentState?.toLowerCase() === "confinado" ? "Confinado" : "No confinado"}
          </h2>
              <p className="description">
               {studentGroup?.estadoSanitario.toLowerCase() === "confinado" ||
                  studentClass?.grupoPresencial === "false" ||
                  studentState?.toLowerCase() === "confinado"
                  ? "Debe recibir clase de manera online" : "Debe recibir clase de manera presencial"}
              </p>

              <Card className={studentGroup?.estadoSanitario.toLowerCase() === "confinado" || studentClass?.grupoPresencial === "false"
                 ? "card-header-bad" : "card-header-good"} as={Card.Header}>
                    {studentClass?.nombreClase} - {studentGroup?.nombre} - Docencia: {studentClass?.grupoPresencial == "true" ? "Presencial" : "Online"}
              </Card>
        </div>
      </div>
    </div>
  );
}

export default withRouter(StudentPage);
