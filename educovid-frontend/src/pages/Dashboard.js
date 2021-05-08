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
                <Card.Title>Gestionar docencia</Card.Title>
                <Card.Text>
                  En esta sección podrá crear distintos grupos burbuja por clase
                  o modificar los existentes, además de gestionar la alternancia
                  entre docencia presencial y online.
                </Card.Text>
              </Card.Body>
              <LinkContainer
                className="container-dashboard"
                to="/manage/professor"
              >
                <Button type="submit" variant="primary" className="nord-button">
                  Ir a docencia
                </Button>
              </LinkContainer>
            </Card>
            <Card>
              <Card.Body>
                <Card.Title>Gestionar centro</Card.Title>
                <Card.Text>
                  En esta sección podrá consultar y gestionar los datos del
                  centro en la plataforma, así como modificar, eliminar o añadir
                  alumnos y profesores.
                </Card.Text>
              </Card.Body>
              <LinkContainer className="container-dashboard" to="/center">
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
