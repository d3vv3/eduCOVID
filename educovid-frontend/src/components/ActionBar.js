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
          <Nav.Link>Confinamientos</Nav.Link>
        </LinkContainer>
        <LinkContainer to="/teaching">
          <Nav.Link>Docencia</Nav.Link>
        </LinkContainer>
        <LinkContainer to="/center">
          <Nav.Link>Centro</Nav.Link>
        </LinkContainer>
      </Nav>
    </Navbar>
  );
}

export default ActionBar;
