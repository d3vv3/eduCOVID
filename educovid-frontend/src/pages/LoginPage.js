import React, { useState } from "react";
import { withRouter } from "react-router-dom";

// Bootstrap imports
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

// Logic

// Constants
import { mockUserData } from "../constants/mockUserData";

function LoginPage(props) {
  const { onLogIn } = props;

  const [usernameField, setUsernameField] = useState("");
  const [passwordField, setPasswordField] = useState("");
  const [roleField, setRoleField] = useState("Alumno");
  const [centerField, setCenterField] = useState("");
  const [errors, setErrors] = useState({});

  const updateInputField = (event) => {
    const { name, value } = event.target;
    switch (name) {
      case "usernameField":
        setUsernameField(value);
        setErrors({});
        break;
      case "passwordField":
        setPasswordField(value);
        setErrors({});
        break;
      case "roleField":
        setRoleField(value);
      default:
        break;
    }
  };

  const handleSubmit = (event) => {
    // fetch REST API
    // TODO

    // Mock up for the moment
    setErrors({ username: "", password: "Usuario o contraseña inválidos." });
    mockUserData.forEach((user, index) => {
      if (user.username === usernameField && user.password === passwordField) {
        onLogIn(user);
        setErrors({});
      }
    });

    // Clean fields
    setPasswordField("");
    setUsernameField("");
  };

  return (
    <div className="login-container">
      <h1>Login</h1>

      <div className="login-center-form">
        <Form>
          <Form.Group controlId="formRole">
            <Form.Label>Rol</Form.Label>
            <Form.Control
              as="select"
              name="roleField"
              value={roleField}
              onChange={updateInputField}
            >
              <option>Alumno</option>
              <option>Profesor</option>
              <option>Responsable de COVID</option>
            </Form.Control>
          </Form.Group>

          <Form.Group controlId="formCenter">
            <Form.Label>Nombre del centro educativo</Form.Label>
            <Form.Control
              required
              autoFocus
              type="text"
              placeholder="Centro educativo"
              name="centerField"
              onChange={updateInputField}
              value={centerField}
              isInvalid={!!errors.center}
            />
            <Form.Control.Feedback type="invalid">
              {errors.username}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group controlId="formUsername">
            <Form.Label>Nombre de usuario</Form.Label>
            <Form.Control
              required
              autoFocus
              type="text"
              placeholder="Usuario"
              name="usernameField"
              onChange={updateInputField}
              value={usernameField}
              isInvalid={!!errors.username}
            />
            <Form.Control.Feedback type="invalid">
              {errors.username}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Label>Contraseña</Form.Label>
            <Form.Control
              required
              type="password"
              name="passwordField"
              placeholder="Contraseña"
              onChange={updateInputField}
              value={passwordField}
              isInvalid={!!errors.password}
            />
            <Form.Control.Feedback type="invalid">
              {errors.password}
            </Form.Control.Feedback>
          </Form.Group>

          <Button
            className="nord-button"
            variant="primary"
            onClick={handleSubmit}
          >
            Enviar
          </Button>
        </Form>
      </div>

      <div className="register-form-admin"></div>
    </div>
  );
}

export default withRouter(LoginPage);
