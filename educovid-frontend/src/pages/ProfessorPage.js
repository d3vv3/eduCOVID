import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Bootstrap imports
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";

// Constants
import { backUrl } from "../constants/constants";

import Notifications from "../components/Notifications";

function ProfessorPage(props) {
  const { history, userData } = props;

  const [professorId] = useState(userData.id);
  const [professorName, setProfessorName] = useState(userData.nombre);
  const [professorState, setProfessorState] = useState(userData.estadoSanitario);
  const [professorCenter, setProfessorCenter] = useState(userData.centro);
  // const [professorClasses, setProfessorClasses] = useState([]);
  const [professorGroups, setProfessorGroups] = useState([]);
  // const [professorGroupsState, setProfessorGroupsState] = useState([]);

  // useEffect(() => {
  //   let isMounted = true;
  //   const callClasses = async () => {
  //     try {
  //       let groups = [];
  //       let groupsState = [];
  //       const classesRes = await fetch(backUrl + "/clase/" + professorId);
  //       let classesData = await classesRes.json();
  //       //Getting groups
  //       classesData.forEach((clase, index) => {
  //         clase.gruposBurbuja.forEach((group, i) => {
  //           groups.push(group);
  //         });
  //       });
  //       //Getting groups state
  //       groups.forEach(async (item, i) => {
  //         const stateRes = await fetch(backUrl + "/grupoburbuja/" + item.id);
  //         let stateData = await stateRes.json();
  //         groupsState.push(stateData);
  //       });
  //       if(isMounted){
  //         setProfessorClasses(classesData);
  //         setProfessorGroups(groups)
  //         setProfessorGroupsState(groupsState);
  //         console.log(professorGroupsState)
  //         console.log(professorGroups)
  //       }
  //     } catch (e) {
  //       // Nothing to do
  //     }
  //   };
  //   callClasses();
  //   return () => { isMounted = false };
  // }, []);

  useEffect(() => {
    let isMounted = true;
    const callGroups = async () => {
      try {
        const groupsRes = await fetch(backUrl + "/grupo/" + professorId + "/" + professorCenter, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
        let groupsData = await groupsRes.json();
        console.log(groupsData)
        if(isMounted){
          setProfessorGroups(groupsData)
        }
      } catch (e) {
        // Nothing to do
      }
    };
    callGroups();
    return () => { isMounted = false };
  }, []);

  return (
    <div className="professor-page-container">
      <Notifications userId={professorId} role="profesor" />
      <div className="centered-div">
        <h4>Profesor/a</h4>
        <h1>{professorName}</h1>
        <h1>Centro: {professorCenter}</h1>
        <h2 className={professorState.toLowerCase() === "confinado" ? "bad" : "good"}>
          {professorState.toLowerCase() === "confinado" ? "Confinado" : "No Confinado"}
        </h2>

        <p className="description">
          {professorState.toLowerCase() === "confinado"
            ? "Debe impartir clase de manera online"
            : "Puede impartir clase de manera presencial"}
        </p>
        {console.log(professorGroups)}
        {professorGroups.map((group, index) =>
          <Card>
            <Card.Header className={group.estadoDocencia.toLowerCase() === "online" ? "card-header-bad" : "card-header-good"}>
              {group.nombre} - {group.estadoDocencia.toLowerCase() === "online" ? "Online" : "Presencial"}
            </Card.Header>
            {group.estadoDocencia.toLowerCase() === "presencial" ? (
              <Card.Body>
                <blockquote className="blockquote mb-0">
                  <p className={group.estadoSanitario === "alumnosconfinados" ? "p-bad" : "p-good"}>
                    {group.estadoSanitario === "alumnosconfinados" ? "Hay alumnos confinados" : "No hay alumnos confinados"}
                  </p>
                </blockquote>
              </Card.Body>
            ) : null}
          </Card>
        )}
      </div>
    </div>
  );
}

export default withRouter(ProfessorPage);
