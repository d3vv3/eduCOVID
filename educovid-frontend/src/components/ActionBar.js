import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { LinkContainer } from "react-router-bootstrap";
import { Link } from "react-router-dom";

function ActionBar(props){
  return(
    <Navbar bg="dark" variant="dark">
      <Navbar.Brand href="/dashboard">eduCOVID</Navbar.Brand>
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
