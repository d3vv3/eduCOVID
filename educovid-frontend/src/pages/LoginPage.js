import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";

// Bootstrap imports
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import ListGroup from "react-bootstrap/ListGroup";
import { LinkContainer } from "react-router-bootstrap";

// Constants
import { backUrl } from "../constants/constants";

function LoginPage(props) {
  const { onLogIn } = props;

  const [suggestions, setSuggestions] = useState([]);
  const [usernameField, setUsernameField] = useState("");
  const [passwordField, setPasswordField] = useState("");
  const [roleField, setRoleField] = useState("Alumno");
  const [centerField, setCenterField] = useState("");
  const [errors, setErrors] = useState({});
  const [centers, setCenters] = useState([]);

  useEffect(() => {
    const callCenters = async () => {
      // Get all centers (not secure, everyone can read all centers)
      const centersRes = await fetch(backUrl + "/centro");
      const centers = await centersRes.json();
      setCenters(centers);
    };
    callCenters();
  }, [centers]);

  const updateInputField = async (event) => {
    const { name, value } = event.target;
    switch (name) {
      case "username":
        setUsernameField(value);
        setErrors({});
        break;
      case "password":
        setPasswordField(value);
        setErrors({});
        break;
      case "role":
        setRoleField(value);
        setSuggestions([]);
        setCenterField("");
        break;
      case "center":
        let suggestions = centers
          .filter((center, index) => centers.indexOf(center) === index) // Remove duplicates
          .filter((center) =>
            center.toLowerCase().startsWith(value.toLowerCase())
          );
        if (suggestions.length > 0) {
          if (suggestions.length === 1 && value.length >= centerField.length) {
            setSuggestions([]);
            setCenterField(suggestions[0]);
          } else {
            setSuggestions(suggestions);
            setCenterField(value);
          }
        }
        break;
      default:
        break;
    }
  };

  const handleSubmit = async (event) => {
    /* fetch REST API */
    // Prepare body
    const details = {
      username: usernameField,
      password: passwordField,
      center: centerField
    };
    let formBody = [];
    for (var property in details) {
      const encodedKey = encodeURIComponent(property);
      const encodedValue = encodeURIComponent(details[property]);
      formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");
    // Make request
    setErrors({ username: "", password: "Usuario o contraseña inválidos." });
    switch (roleField.toLowerCase()) {
      case "alumno":
        const alumnoRes = await fetch(backUrl + "/login/alumno", {
          method: "POST",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
          },
          body: formBody
        });
        if (alumnoRes.ok) {
          const alumnoData = alumnoRes.json();
          onLogIn({ ...alumnoData, role: "alumno" });
          setErrors({});
        }
        break;
      case "profesor":
        const profesorRes = await fetch(backUrl + "/login/profesor", {
          method: "POST",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
          },
          body: formBody
        });
        if (profesorRes.ok) {
          const profesorData = profesorRes.json();
          onLogIn({ ...profesorData, role: "profesor" });
          setErrors({});
        }
        break;
      case "responsable de covid":
        const responsableRes = await fetch(backUrl + "/login/responsable", {
          method: "POST",
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
          },
          body: formBody
        });
        if (responsableRes.ok) {
          const responsableData = responsableRes.json();
          onLogIn({ ...responsableData, role: "responsable" });
          setErrors({});
        }
        break;
      default:
        break;
    }

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
              name="role"
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
              name="center"
              onChange={updateInputField}
              value={centerField}
              isInvalid={!!errors.center}
            />
            <ListGroup>
              {centerField.length > 0
                ? suggestions.map((center) => (
                  <ListGroup.Item variant="light" key={center}>{center}</ListGroup.Item>
                ))
                : null}
            </ListGroup>
          </Form.Group>

          <Form.Group controlId="formUsername">
            <Form.Label>Identificador de usuario</Form.Label>
            <Form.Control
              required
              type="text"
              placeholder="Usuario"
              name="username"
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
              name="password"
              placeholder="Contraseña"
              onChange={updateInputField}
              value={passwordField}
              isInvalid={!!errors.password}
            />
            <Form.Control.Feedback type="invalid">
              {errors.password}
            </Form.Control.Feedback>
          </Form.Group>

          <div className="buttons-container">
            <LinkContainer to="/">
              <Button className="nord-button" variant="primary">
                Atrás
              </Button>
            </LinkContainer>
            <Button
              className="nord-button"
              variant="primary"
              onClick={handleSubmit}
            >
              Enviar
            </Button>
          </div>
        </Form>
      </div>
    </div>
  );
}

export default withRouter(LoginPage);
