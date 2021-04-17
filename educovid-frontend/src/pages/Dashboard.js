import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";

import Card from 'react-bootstrap/Card'
import CardDeck from 'react-bootstrap/CardDeck'

function Dashboard(props){

  const {userData} = props;

  const [responsibleId] = useState(userData.id);
  const [responsibleName, setResponsibleName] = useState(userData.nombre);
  const [responsibleCenter, setResponsibleCenter] = useState(userData.centro);

  return (
    <div>
      <ActionBar />
      <div className="dashboard-page-container">
        <div className="centered-div">
          <h1>¡Bienvenido {responsibleName}!</h1>
          <h3>Aquí podrás gestionar el centro: {responsibleCenter}</h3>
        </div>
        <div className="cards-container">
          <CardDeck>
            <Card>
              <Card.Body>
                <Card.Title>Gestionar confinamientos</Card.Title>
                <Card.Text>
                  En esta sección podrá confinar y desconfinar alumnos, profesores o grupos burbuja.
                </Card.Text>
              </Card.Body>
            </Card>
            <Card>
              <Card.Body>
                <Card.Title>Gestionar docencia</Card.Title>
                <Card.Text>
                  En esta sección podrá crear distintos grupos burbuja por clase o modificar los existentes, además de gestionar la alternancia entre docencia presencial y online.
                </Card.Text>
              </Card.Body>
            </Card>
            <Card>
              <Card.Body>
                <Card.Title>Gestionar centro</Card.Title>
                <Card.Text>
                  En esta sección podrá consultar y gestionar los datos del centro en la plataforma, así como modificar, eliminar o añadir alumnos y profesores.
                </Card.Text>
              </Card.Body>
            </Card>
          </CardDeck>
        </div>
      </div>
    </div>
  );
}

export default withRouter(Dashboard);
