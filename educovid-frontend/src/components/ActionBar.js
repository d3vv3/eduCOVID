import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { Link } from "react-router-dom";

function ActionBar(props){
  return(
    <Navbar bg="primary" variant="dark">
      <Navbar.Brand href="/dashboard">eduCOVID</Navbar.Brand>
      <Nav className="mr-auto">
        <Nav.Link href="/confine">Gestionar confinamientos</Nav.Link>
        <Nav.Link href="/teaching">Gestionar docencia</Nav.Link>
        <Nav.Link href="/center">Gestionar centro</Nav.Link>
      </Nav>
    </Navbar>
  );
}

export default ActionBar;
