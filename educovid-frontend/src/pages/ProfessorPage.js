import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom"; 

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
  const [professorClasses, setProfessorClasses] = useState([]);

  useEffect(() => {
    let isMounted = true;
    const callClasses = async () => {
      try {
        const classesRes = await fetch(backUrl + "/clase/" + professorId);
        let classesData = await classesRes.json();
        setProfessorClasses(classesData);
      } catch (e) {
        // Nothing to do
      }
    };
    callClasses();
    return () => { isMounted = false };
  }, []);

  // useEffect(() => {
  //   setProfessorName(userData.nombre);
  //   setProfessorState(userData.estadoSanitario);
  //   setprofessorClasses(userData.clases);
  // }, [userData]);

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
          {professorClasses?.map((clase, index) => (
            (clase.gruposBurbuja).map((group, index) => (
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
                  {clase.nombre} - {group.id}
                </Accordion.Toggle>
                {group.estadoDocencia.toLowerCase() === "presencial" ? (
                  <Accordion.Collapse
                    className="card-body"
                    key={index + professorClasses.length}
                    eventKey={index + 1}
                  >
                    <Card.Body key={index}>
                      {(group.alumnos).map((student, index) => (
                        <div key={index} className="accordion-item">
                          <p
                            className={
                              student.estadoSanitario.toLowerCase() === "confinado" ? "p-bad" : "p-good"
                            }
                            key={index}
                          >
                            {student.nombre}
                          </p>
                          <p
                            className={
                              student.estadoSanitario.toLowerCase() === "confinado" ? "p-bad" : "p-good"
                            }
                            key={index + student.length}
                          >
                            {student.estadoSanitario}
                          </p>
                          {index === group.alumnos.length - 1 ? null : <hr />}
                        </div>
                      ))}
                    </Card.Body>
                  </Accordion.Collapse>
                ) : null}
              </Card>
            ))
          ))}
        </Accordion>
      </div>
    </div>
  );
}

export default withRouter(ProfessorPage);
