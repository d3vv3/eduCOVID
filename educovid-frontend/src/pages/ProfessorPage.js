import React, { useState, useEffect } from 'react';
import { withRouter } from 'react-router-dom'; // No sé si es necesario

// Bootstrap imports
import Accordion from 'react-bootstrap/Accordion';
import Card from 'react-bootstrap/Card';

import { professor } from '../tests/prueba';

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
            <Accordion.Toggle className={group.state === "Confinado" ? "card-header-bad" : "card-header-good"} as={Card.Header} eventKey={index+1}>
              {group.name} - {group.state}
            </Accordion.Toggle>
            <Accordion.Collapse className={group.state === "Confinado" ? "card-body-bad" : "card-body-good"} eventKey={index+1}>
              <Card.Body>{group.students.map((student, index) => <p key={index}>{student}</p>)}</Card.Body>
            </Accordion.Collapse>
          </Card>
        )}
        </Accordion>

      </div>

    </div>
  );
}


export default withRouter(ProfessorPage);
