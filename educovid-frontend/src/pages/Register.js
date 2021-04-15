import React, { useState, useCallback } from "react";
import { withRouter } from "react-router-dom";

// Bootstrap imports
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { LinkContainer } from "react-router-bootstrap";

// Local imports
import PasswordStrength from "../components/PasswordStrength";
import DropZone from "../components/DropZone";
import zxcvbn from "zxcvbn";

// Constants
import { backUrl } from "../constants/constants";

// External library imports
const fetch = require("node-fetch");

function Register({ history }) {
  const [centerName, setCenterName] = useState("");
  const [idNumber, setIdNumber] = useState("");
  const [password, setPassword] = useState("");
  const [gdprAcceptance, setGdprAcceptance] = useState(false);
  const [errors, setErrors] = useState({});
  const [registerStep, setRegisterStep] = useState(1);
  const [files, setFiles] = useState([]);
  const [feedbacks, setFeedbacks] = useState({
    centerName: "",
    idNumber: "",
    password: "",
    gdprAcceptance: "Debe aceptar los términos y condiciones de uso"
  });
  const [studentCSV, setStudentCSV] = useState("");
  const [professorCSV, setProfessorCSV] = useState("");
  const [responsibleResponse, setResponsibleResponse] = useState({});
  const [studentCSVResponse, setStudentCSVResponse] = useState({});
  const [professorCSVResponse, setProfessorCSVResponse] = useState({});

  function download(filename, text) {
    var element = document.createElement("a");
    element.setAttribute(
      "href",
      "data:text/csv;charset=utf-8," + encodeURIComponent(text)
    );
    element.setAttribute("download", filename);
    element.style.display = "none";
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
  }

  const onDrop = useCallback(
    acceptedFiles => {
      let x = files;
      acceptedFiles.forEach(file => {
        // console.log(files);
        x = x.concat([file.name]);
        const reader = new FileReader();
        reader.onabort = () => console.log("File reading was aborted");
        reader.onerror = () => console.log("File reading has failed");
        reader.onload = () => {
          const binaryStr = reader.result;
          // TODO: Process file contents
          let decoder = new TextDecoder("utf-8");
          // console.log(binaryStr);
          let content = decoder.decode(binaryStr);
          if (file.name.includes("student") || file.name.includes("alumno")) {
            setStudentCSV(content);
          }
          if (
            file.name.includes("professor") ||
            file.name.includes("profesor")
          ) {
            setProfessorCSV(content);
          }
        };

        reader.readAsArrayBuffer(file);
      });
      console.log(x);
      setFiles(x);
    },
    [files]
  );

  const validateForm = () => {
    let errs = {};
    let msgs = {};

    const validateCenterName = () => {
      // Validate center name field by string length
      msgs.centerName = "El nombre del centro debe tener al menos 4 caracteres";
      errs.centerName = centerName.length < 4;
      // TODO: Check if the center name already exists in the service
    };

    const validateID = () => {
      // Validate responsable ID via regex
      let idNumberRegex = /^[0-9]{8,8}[A-Za-z]$/;
      msgs.idNumber = "El NIF o NIE no es válido";
      errs.idNumber = !idNumberRegex.test(idNumber);
      // TODO: Check with backend if DNI or passport already in center
    };

    const validatePassword = () => {
      // This validation is provided by an open source tool here
      // https://github.com/dropbox/zxcvbn
      msgs.password = "La contraseña es demasiado débil";
      errs.password = zxcvbn(password).score < 2;
    };

    // Run the validations
    validateCenterName();
    validateID();
    validatePassword();

    // Set states only once instead of once per validation
    setErrors(errs);
    setFeedbacks(msgs);

    // Return true if there were no errors
    return !Object.values(errs).some(item => item === true);
  };

  const handleSubmit = async event => {
    // Handle submit and prevent the form from submiting to validate
    event.preventDefault();
    let valid = validateForm();
    if (valid && registerStep === 1) {
      //history.push("/");
      setRegisterStep(2);
      return true;
    } else if (valid && registerStep === 2) {
      // TODO: Should validate files!
      // Register the responsible
      let response = await fetch(`${backUrl}/register/responsible`, {
        method: "post",
        body: JSON.stringify({
          center: centerName,
          nifNie: idNumber,
          password: password,
          terms: gdprAcceptance
        }),
        headers: { "Content-Type": "application/json" }
      });
      setResponsibleResponse(await response.json());
      response = await fetch(`${backUrl}/register/students`, {
        method: "post",
        body: studentCSV,
        headers: { "Content-Type": "text/csv" }
      });
      setStudentCSVResponse(await response.json());
      response = await fetch(`${backUrl}/register/professors`, {
        method: "post",
        body: professorCSV,
        headers: { "Content-Type": "text/csv" }
      });
      setProfessorCSVResponse(await response.json());

      history.push("/");
      return true;
    }
  };

  const updateFormState = event => {
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
      <h1>Registra tu Centro</h1>
      {registerStep === 1 ? (
        <div className="register-center-form">
          <Form onSubmit={handleSubmit.bind(this)}>
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
                placeholder="Introduzca un NIF o NIE"
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
              <PasswordStrength password={password} />
              <Form.Control.Feedback type="invalid">
                {feedbacks.password}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group controlId="formGDPRCheckbox">
              <div className="terms">
                <Form.Check
                  required
                  type="checkbox"
                  feedback={feedbacks.gdprAcceptance}
                  isInvalid={!!errors.gdprAcceptance}
                />
                <span>
                  Acepto los <a href="/terms">términos y condiciones</a> de uso.
                </span>
              </div>
            </Form.Group>
            <div className="buttons-container">
              <LinkContainer to="/">
                <Button className="nord-button" variant="primary">
                  Atrás
                </Button>
              </LinkContainer>
              <Button className="nord-button" variant="primary" type="submit">
                Continuar
              </Button>
            </div>
          </Form>
        </div>
      ) : (
        <div className="csv-upload">
          <div className="templates">
            <div className="item">
              <img src="/csv_icon.png" alt="CSV document icon" />
              <div className="buttons-container">
                <Button
                  className="nord-button"
                  variant="primary"
                  onClick={() => {
                    download(
                      "plantilla_alumnos.csv",
                      "Clase,Nombre,Apellidos,Numero de matricula"
                    );
                  }}
                >
                  Descargar plantilla CSV alumnos
                </Button>
              </div>
            </div>
            <div className="item">
              <img src="/csv_icon.png" alt="CSV document icon" />
              <div className="buttons-container">
                <Button
                  className="nord-button"
                  variant="primary"
                  onClick={() => {
                    download(
                      "plantilla_profesores.csv",
                      "Clase,Nombre,Apellidos,NIF/NIE"
                      // El centro se coge del estado
                    );
                  }}
                >
                  Descargar plantilla CSV profesores
                </Button>
              </div>
            </div>
          </div>
          <DropZone onDrop={onDrop} />
          <div className="selected-files">
            {files?.map((value, index) => (
              <div
                className="filename"
                key={index}
                onClick={() => {
                  let x = files;
                  x = x.filter((filename, index, arr) => {
                    return filename !== value;
                  });
                  console.log(x);
                  setFiles(x);
                }}
              >
                {value}
              </div>
            ))}
          </div>
          <div className="buttons-container">
            <Button
              className="nord-button"
              variant="primary"
              onClick={() => setRegisterStep(1)}
            >
              Atrás
            </Button>
            <Button
              className="nord-button"
              variant="primary"
              onClick={handleSubmit}
            >
              Finalizar
            </Button>
          </div>
        </div>
      )}
    </div>
  );
}

// We need to wrap the exported component with withRouter to provide the history
// and be able to call it within the component
export default withRouter(Register);
