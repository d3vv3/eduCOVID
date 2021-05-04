import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Bootstrap imports
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";

// Constants
import { backUrl } from "../constants/constants";

import Notifications from "../components/Notifications";

function ProfessorPage(props) {
  const { history, userData, onLogOut } = props;

  const [professorId, setProfessorId] = useState(-1);
  const [professorName, setProfessorName] = useState("");
  const [professorState, setProfessorState] = useState("");
  const [professorCenter, setProfessorCenter] = useState("");
  // const [professorClasses, setProfessorClasses] = useState([]);
  const [professorGroups, setProfessorGroups] = useState([]);
  // const [professorGroupsState, setProfessorGroupsState] = useState([]);

  useEffect(() => {
    if(userData){
      setProfessorId(userData.id);
      setProfessorName(userData.nombre);
      setProfessorState(userData.estadoSanitario);
      setProfessorCenter(userData.centro);
    }
  }, [userData]);

  useEffect(async () => {
    if(professorId != -1){
      try {
        const token = localStorage.getItem('token') || "";
        const groupsRes = await fetch(backUrl + "/grupo/" + professorId + "/" + professorCenter, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        let groupsData = await groupsRes.json();
        console.log(groupsData)
        setProfessorGroups(groupsData)
      } catch (e) {
        // Nothing to do
      }
    }
  }, [professorId]);

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
          {professorGroups.map((group, index) =>
            <Card>
              <Card.Header className={group.grupoPresencial.toLowerCase() === "true" ? "card-header-good" : "card-header-bad"}>
                {group.nombre} - {group.grupoPresencial.toLowerCase() === "true" ? "Presencial" : "Online"}
              </Card.Header>
              {group.grupoPresencial.toLowerCase() === "true" ? (
                <Card.Body>
                  <blockquote className="blockquote mb-0">
                    <p className={group.alumnosCnfinados === "true" ? "p-bad" : "p-good"}>
                      {group.alumnosConfinados === "true" ? "Hay alumnos confinados" : "No hay alumnos confinados"}
                    </p>
                  </blockquote>
                </Card.Body>
              ) : null}
            </Card>
          )}
        </div>
      </div>
    </div>
  );
}

export default withRouter(ProfessorPage);
