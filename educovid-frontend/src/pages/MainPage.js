import React from "react";

// Bootstrap imports
import Button from "react-bootstrap/Button";
import { LinkContainer } from "react-router-bootstrap";

function MainPage(props) {
  return (
    <div className="main-page-container">
      <div className="centered-div">
        <img
          className="logo"
          src={process.env.PUBLIC_URL + "logo512.png"}
          alt="Shield from COVID logotype"
        />
        <h1>eduCOVID</h1>

        <p className="description">
          Una herramienta para seguimiento y control del COVID en los centros
          educativos.
        </p>

        <p className="tagline">Para estudiantes, por estudiantes.</p>

        <div className="buttons-container">
          {!props.loggedIn ? (
            <LinkContainer to="/register">
              <Button type="submit" variant="primary" className="nord-button">
                Registra tu centro
              </Button>
            </LinkContainer>
          ) : null}
          {!props.loggedIn ? (
            <LinkContainer to="/login">
              <Button type="submit" variant="primary" className="nord-button">
                Accede
              </Button>
            </LinkContainer>
          ) : null}
        </div>
      </div>
    </div>
  );
}

export default MainPage;
