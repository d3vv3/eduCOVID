import React from "react";

// Bootstrap imports
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function MainPage() {
  return (
    <div className="main-page-container">

        <div className="centered-div">
            <img
                className="logo"
                src={process.env.PUBLIC_URL + "assets/logo.png"}
                alt="Shield from COVID logotype"
            />
            <h1>eduCOVID</h1>

            <p className="description">
                Una herramienta para seguimiento y control del COVID en los centros
                educativos.
            </p>

            <p className="tagline">
                Para estudiantes, por estudiantes.
            </p>

            <div className="buttons-container">
                <Form action="/register">
                    <Button
                        type="submit" variant="primary" className="nord-button">
                        Registra tu centro
                    </Button>
                </Form>
                <Form action="/">
                    <Button
                        type="submit" variant="primary" className="nord-button">
                        Accede
                    </Button>
                </Form>
            </div>
        </div>
    </div>
  );
}

export default MainPage;
