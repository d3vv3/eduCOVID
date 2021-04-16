import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom"; // No sé si es necesario

// Bootstrap imports
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";

// Constants
import { backUrl } from "../constants/constants";

//import { professor, students } from "../tests/prueba";

function ProfessorPage(props) {
  const { history, userData } = props;

  const [professorId] = useState(userData.id);
  const [professorName, setProfessorName] = useState(userData.nombre);
  const [professorState, setProfessorState] = useState(userData.estadoSanitario);
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
        const groupsRes = await fetch(backUrl + "/grupoburbuja/" + professorId);
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
      <div className="centered-div">
        <h4>Profesor/a</h4>
        <h1>{professorName}</h1>
        <h2 className={professorState.toLowerCase() === "confinado" ? "bad" : "good"}>
          {professorState}
        </h2>
        <p className="description">
          {professorState.toLowerCase() === "confinado"
            ? "Debe impartir clase de manera online"
            : "Puede impartir clase de manera presencial"}
        </p>
        <Accordion className="accordion">
        {professorGroups.map((group, index) => (
            <Card key={index}>
              <Accordion.Toggle
                key={index}
                className={
                  group.estadoDocencia.toLowerCase() === "online"
                    ? "card-header-bad"
                    : "card-header-good"
                }
                as={Card.Header}
                eventKey={index + 1}
              >
                {group.nombre}
              </Accordion.Toggle>
              {group.estadoDocencia.toLowerCase() === "presencial" ? (
                <Accordion.Collapse
                    className="card-body"
                    key={index + professorGroups.length}
                    eventKey={index + 1}
                  >
                    <Card.Body key={index}>
                        <div key={index} className="accordion-item">
                          <p
                            className="p-good"
                            key={index}
                          >
                            {group.estadoSanitario === "alumnosconfinados" ? "Hay alumnos confinados" : "No hay alumnos confinados"}
                          </p>
                        </div>
                    </Card.Body>
                  </Accordion.Collapse>
              ) : null}
            </Card>
          ))}
        </Accordion>
      </div>
    </div>
  );
}

export default withRouter(ProfessorPage);
