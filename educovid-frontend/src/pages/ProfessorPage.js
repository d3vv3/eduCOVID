import React, { useState, useLayoutEffect } from 'react';
import { withRouter } from 'react-router-dom'; // No sé si es necesario

// Bootstrap imports
import Accordion from 'react-bootstrap/Accordion';
import Card from 'react-bootstrap/Card';

import { professor } from '../tests/prueba';

function ProfessorPage({ history, match }){

  const [professorId, setProfessorId] = useState(match.params.professorId);
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
        <h1>{professorName}</h1>
        <h2 className={professorState === "Confinado" ? "bad" : "good"}>
            {professorState}
        </h2>

        <p className="description">
            {professorState === "Confinado" ? "Debe impartir clase de manera online" : "Debe impartir clase de manera presencial"}
        </p>

        <Accordion>

        {professorGroups?.map((group, index) =>
          <Card>
            <Accordion.Toggle as={Card.Header} eventKey={index+1}>
              {group.name}
            </Accordion.Toggle>
            <Accordion.Collapse eventKey={index+1}>
              <Card.Body>Hello! I'm the body</Card.Body>
            </Accordion.Collapse>
          </Card>
        )}
        </Accordion>

      </div>

    </div>
  );
}


export default withRouter(ProfessorPage);
