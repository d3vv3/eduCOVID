import React, { useState, useEffect } from 'react';
import { withRouter } from 'react-router-dom';

// Bootstrap imports
import Accordion from 'react-bootstrap/Accordion';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';

import { student } from '../tests/prueba';

function StudentPage({ history, match }){

  const [studentId] = useState(match.params.studentId);
  const [studentName, setStudentName] = useState("");
  const [studentState, setStudentState] = useState("");
  const [studentGroup, setStudentGroup] = useState("");

  useEffect(() => {
    setStudentName(student[studentId].name); //BBDD
    setStudentState(student[studentId].state); //BBDD
    setStudentGroup(student[studentId].group); //BBDD
}, [studentId]);

  return (
    <div className="student-page-container">

      <div className="centered-div">
        <h1>{studentName}</h1>
        <h2 className={studentState === "Confinado" ? "bad" : "good"}>
            {studentState}
        </h2>

        <Card className={studentGroup.state === ("Confinado" || "Online") ? "card-header-bad" : "card-header-good"} as={Card.Header}>
          {studentGroup.name} - {studentGroup.state}
        </Card>

        <p className="description">
            {(studentGroup?.state === "Confinado" || studentGroup?.state === "Online" || studentState === "Confinado") ? "Debe recibir clase de manera online" : "Debe recibir clase de manera presencial"}
        </p>

      </div>

    </div>
  );
}


export default withRouter(StudentPage);