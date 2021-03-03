import React, { useState } from "react";
import { withRouter } from 'react-router-dom';

// Bootstrap imports
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

// Local imports
import PasswordStrength from '../components/PasswordStrength'

// External library imports
import zxcvbn from "zxcvbn";


function Register({ history }) {

    const [centerName, setCenterName] = useState("");
    const [idNumber, setIdNumber] = useState("");
    const [password, setPassword] = useState("");
    const [gdprAcceptance, setGdprAcceptance] = useState(false);
    const [errors, setErrors] = useState({});
    const [feedbacks, setFeedbacks] = useState({
        centerName: "",
        idNumber: "",
        password: "",
        gdprAcceptance: "Debe aceptar la Ley General de Protección de Datos Europea para usar nuestros servicios",
    });

    const validateForm = () => {

        let errs = {};
        let msgs = {};

        const validateCenterName = () => {
            // Validate center name field by string length
            msgs.centerName = "The center name must be at least 4 caracters"
            errs.centerName = centerName.length < 4
            // TODO: Check if the center name already exists in the service
        }

        const validateID = () => {
            // Validate responsable ID via regex
            let idNumberRegex = /^[0-9]{8,8}[A-Za-z]$/
            msgs.idNumber = "El DNI o pasaporte no es válido"
            errs.idNumber = !idNumberRegex.test(idNumber)
            // TODO: Check with backend if DNI or passport already in center
        }

        const validatePassword = () => {
            // This validation is provided by an open source tool here
            // https://github.com/dropbox/zxcvbn
            msgs.password = "La contraseña es demasiado débil"
            errs.password = zxcvbn(password).score < 2
        }

        // Run the validations
        validateCenterName();
        validateID();
        validatePassword();

        // Set states only once instead of once per validation
        setErrors(errs);
        setFeedbacks(msgs);

        // Return true if there were no errors
        return !Object.values(errs).some(item => item === true)

    }

    const handleSubmit = (event) => {
        // Handle submit and prevent the form from submiting to validate
        event.preventDefault();
        let valid = validateForm();
        if (Object.keys(errors).length > 0
            && valid) {
            history.push("/");
            return true;
        }

    };

    const updateFormState = (event) => {
        // On change, set the states with the updates
        const { name, value } = event.target;
        switch (name) {
          case "centerName":
            setCenterName(value);
            break;
          case "idNumber":
            setIdNumber(value);
            break;
          case "password":
            setPassword(value);
            break;
          case "gdprAcceptance":
            setGdprAcceptance(value);
            break;
          default:
            return;
        }
  };


  return (
    <div className="register-container">
        <h1>
            Registra tu Centro
        </h1>

        <div className="register-center-form">
        <Form
            onSubmit={handleSubmit.bind(this)}
            >
            <Form.Group controlId="formCenterName">
                <Form.Label>Nombre del centro</Form.Label>
                <Form.Control
                    required
                    autoFocus
                    type="text"
                    placeholder="Introduzca el nombre del centro"
                    name="centerName"
                    onChange={updateFormState}
                    value={centerName}
                    isInvalid={!!errors.centerName}
                />
                <Form.Text className="text-muted">
                    No debe existir en nuestra plataforma
                </Form.Text>
                <Form.Control.Feedback type="invalid">
                    {feedbacks.centerName}
                </Form.Control.Feedback>
                <Form.Control.Feedback type="valid">
                    Perfecto
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group controlId="formAdminID">
                <Form.Label>ID del Responsable COVID</Form.Label>
                <Form.Control
                    required
                    type="text"
                    name="idNumber"
                    placeholder="Introduzca un DNI o pasaporte"
                    onChange={updateFormState}
                    value={idNumber}
                    isInvalid={!!errors.idNumber}
                />
                <Form.Text className="text-muted">
                    Debe ser único para el centro
                </Form.Text>
                <Form.Control.Feedback type="invalid">
                    {feedbacks.idNumber}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group controlId="formBasicPassword">
                <Form.Label>Contraseña</Form.Label>
                <Form.Control
                    required
                    type="password"
                    name="password"
                    placeholder="Introduzca una contraseña"
                    onChange={updateFormState}
                    value={password}
                    isInvalid={!!errors.password}
                />
                <PasswordStrength password={password}/>
                <Form.Control.Feedback type="invalid">
                    {feedbacks.password}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group controlId="formGDPRCheckbox">
                <Form.Check
                    required
                    type="checkbox"
                    label={"Acepto la Ley General de Protección de Datos Europea"}
                    feedback={feedbacks.gdprAcceptance}
                    isInvalid={!!errors.gdprAcceptance}
                />
            </Form.Group>

            <Button className="nord-button" variant="primary" type="submit">
                Enviar
            </Button>
        </Form>
        </div>

        <div className="register-form-admin">

        </div>

    </div>
  );
}

// We need to wrap the exported component with withRouter to provide the history
// and be able to call it within the component
export default withRouter(Register);
