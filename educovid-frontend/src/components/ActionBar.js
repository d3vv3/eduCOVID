import React from "react";
import { Navbar, Nav, Button } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

function ActionBar(props) {
  return (
    <Navbar bg="dark" variant="dark" className="navbar-container">
      <LinkContainer to="/dashboard">
        <Navbar.Brand>
          <img
            alt=""
            src="/logo512.png"
            width="30"
            height="30"
            className="d-inline-block align-top"
          />{" "}
          eduCOVID
        </Navbar.Brand>
      </LinkContainer>
      <Nav className="mr-auto">
        <LinkContainer to="/manage/student">
          <Nav.Link>Alumnos</Nav.Link>
        </LinkContainer>
        <LinkContainer to="/manage/professor">
          <Nav.Link>Profesores</Nav.Link>
        </LinkContainer>
        <LinkContainer to="/manage/class">
          <Nav.Link>Clases</Nav.Link>
        </LinkContainer>
      </Nav>
      <Button
        className="nord-button"
        variant="primary"
        onClick={() => {
          props.onLogOut();
          localStorage.removeItem("token");
        }}
      >
        Cerrar sesión
      </Button>
    </Navbar>
  );
}

export default ActionBar;
