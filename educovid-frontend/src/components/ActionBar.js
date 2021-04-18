import React from "react";
import { Navbar, Nav } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

function ActionBar(props) {
  return (
    <Navbar bg="dark" variant="dark" className="navbar-container">
      <LinkContainer to="/dashboard">
        <Navbar.Brand>
          <img
            alt=""
            src={process.env.PUBLIC_URL + "logo512.png"}
            width="30"
            height="30"
            className="d-inline-block align-top"
          />{" "}
          eduCOVID
        </Navbar.Brand>
      </LinkContainer>
      <Nav className="mr-auto">
        <LinkContainer to="/confine">
          <Nav.Link>Gestionar confinamientos</Nav.Link>
        </LinkContainer>
        <LinkContainer to="/teaching">
          <Nav.Link>Gestionar docencia</Nav.Link>
        </LinkContainer>
        <LinkContainer to="/center">
          <Nav.Link>Gestionar centro</Nav.Link>
        </LinkContainer>
      </Nav>
    </Navbar>
  );
}

export default ActionBar;
