import React, { useState, useEffect } from 'react';
import { withRouter } from 'react-router-dom'; // No sé si es necesario

// Bootstrap imports
import Accordion from 'react-bootstrap/Accordion';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';

import { professor } from '../tests/prueba';
import { student as students } from '../tests/pruebaStudent';

function ProfessorPage({ history, match }){

  const [professorId] = useState(match.params.professorId);
  const [professorName, setProfessorName] = useState("");
  const [professorState, setProfessorState] = useState("");
  const [professorGroups, setProfessorGroups] = useState([]);

  useEffect(() => {
    setProfessorName(professor[professorId].name); //BBDD
    setProfessorState(professor[professorId].state); //BBDD
    setProfessorGroups(professor[professorId].groups); //BBDD
}, [professorId]);

  return (
    <div className="professor-page-container">

      <div className="centered-div">
        <h4>Profesor</h4>
        <h1>{professorName}</h1>
        <h2 className={professorState === "Confinado" ? "bad" : "good"}>
            {professorState}
        </h2>

        <p className="description">
            {professorState === "Confinado" ? "Debe impartir clase de manera online" : "Debe impartir clase de manera presencial"}
        </p>

        <Accordion className="accordion">
        {professorGroups?.map((group, index) =>
          <Card key={index}>
            <Accordion.Toggle key={index} className={group.teachingState === "Online" ? "card-header-bad" : "card-header-good"} as={Card.Header} eventKey={index+1}>
              {group.name} - 1ºB - {group.teachingState}
            </Accordion.Toggle>
            {group.teachingState === "Presencial" ?
            <Accordion.Collapse className="card-body" key={index + professorGroups.length} eventKey={index+1}>
              <Card.Body key={index}>{students.map((student, index) =>
                <div key={index} className="accordion-item">
                  <p className={student.state === "Confinado" ? "p-bad" : "p-good"} key={index}>{student.name}</p>
                  <p className={student.state === "Confinado" ? "p-bad" : "p-good"} key={index + student.length}>{student.state}</p>
                  {index === (students.length - 1) ? null : <hr/>}
                </div>
                )}
              </Card.Body>
            </Accordion.Collapse> : null}
          </Card>
        )}
        </Accordion>

      </div>

    </div>
  );
}


export default withRouter(ProfessorPage);
