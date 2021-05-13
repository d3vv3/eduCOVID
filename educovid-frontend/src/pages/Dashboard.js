import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import CardDeck from "react-bootstrap/CardDeck";
import { LinkContainer } from "react-router-bootstrap";

function Dashboard(props) {
  const { userData, onLogOut } = props;

  const [responsibleId, setResponsibleId] = useState(-1);
  const [responsibleName, setResponsibleName] = useState("");
  const [responsibleCenter, setResponsibleCenter] = useState("");

  useEffect(() => {
    if (userData) {
      setResponsibleId(userData.id);
      setResponsibleName(userData.nombre);
      setResponsibleCenter(userData.centro);
    }
  }, [userData]);

  return (
    <div>
      <ActionBar
        onLogOut={() => {
          onLogOut();
        }}
      />
      <div className="dashboard-page-container">
        <div className="centered-div">
          <h1>¡Bienvenido {responsibleName}!</h1>
          <h3>Responsable COVID de:</h3>
          <h2>{responsibleCenter}</h2>
        </div>
        <div className="cards-container">
          <CardDeck>
            <Card>
              <Card.Body>
                <Card.Title>Alumnos</Card.Title>
                <Card.Text>
                  En esta sección podrá gestionar los alumnos.
                </Card.Text>
              </Card.Body>
              <LinkContainer
                className="container-dashboard"
                to="/manage/student"
              >
                <Button type="submit" variant="primary" className="nord-button">
                  Alumnos
                </Button>
              </LinkContainer>
            </Card>
            <Card>
              <Card.Body>
                <Card.Title>Profesores</Card.Title>
                <Card.Text>
                  En esta sección podrá gestionar los profesores.
                </Card.Text>
              </Card.Body>
              <LinkContainer
                className="container-dashboard"
                to="/manage/professor"
              >
                <Button type="submit" variant="primary" className="nord-button">
                  Profesores
                </Button>
              </LinkContainer>
            </Card>
            <Card>
              <Card.Body>
                <Card.Title>Clases</Card.Title>
                <Card.Text>
                  En esta sección podrá gestionar las clases.
                </Card.Text>
              </Card.Body>
              <LinkContainer className="container-dashboard" to="/manage/class">
                <Button type="submit" variant="primary" className="nord-button">
                  Ir a centro
                </Button>
              </LinkContainer>
            </Card>
          </CardDeck>
        </div>
      </div>
    </div>
  );
}

export default withRouter(Dashboard);
