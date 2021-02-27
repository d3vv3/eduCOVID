import React from "react";

// Style
import "../styles/style.scss";

// Bootstrap imports
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

function Register() {
  return (
    <div className="register-container">
        <h1>
            Register your Center
        </h1>

        <div className="register-center-form">
        <Form>
            <Form.Group controlId="formCenterName">
                <Form.Label>Center name</Form.Label>
                <Form.Control type="center" placeholder="Enter your center name" />
                <Form.Text className="text-muted">
                  It must not exist yet in our service
                </Form.Text>
            </Form.Group>

            <Form.Group controlId="formAdminUsername">
                <Form.Label>Admin username</Form.Label>
                <Form.Control type="center" placeholder="Enter your admin's username" />
                <Form.Text className="text-muted">
                  It must not exist yet in our service
                </Form.Text>
            </Form.Group>

            <Form.Group controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Password" />
            </Form.Group>

            <Form.Group controlId="formGDPRCheckbox">
                <Form.Check type="checkbox" label="Accept GDPR" />
            </Form.Group>

            <Button variant="primary" type="submit">
                Submit
            </Button>
        </Form>
        </div>

        <div className="register-form-admin">

        </div>

    </div>
  );
}

export default Register;
