import React, { useState, useEffect } from "react";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function ProfessorCenteredModal(props) {
  const [professorName, setProfessorName] = useState(
    props?.existingProfessor?.nombre || ""
  );
  const [id, setId] = useState(props?.existingProfessor?.nifNie || "");
  const [healthState, setHealthState] = useState(
    props?.existingProfessor?.estadoSanitario || ""
  );
  const [confineDate, setConfineDate] = useState(
    props?.existingProfessor?.fechaConfinamiento || ""
  );
  const [professorClasses, setProfessorClasses] = useState([]);
  const [allClasses, setAllClasses] = useState([]);
  const [errors, setErrors] = useState({});
  const [feedbacks, setFeedbacks] = useState({
    id: "",
    classes: ""
  });

  useEffect(() => {
    retrieveClasses();
  }, []);

  const retrieveClasses = async () => {
    const token = localStorage.getItem("token") || "";
    let response, responseData;
    response = await fetch(backUrl + "/clase/all", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });
    responseData = await response.json();
    setAllClasses(responseData);
    if (props.existingProfessor) {
      response = await fetch(
        backUrl + `/clase/profesor/${props.existingProfessor.id}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        }
      );
      responseData = await response.json();
      setProfessorClasses(responseData);
    }
  };

  const updateFormState = event => {
    // On change, set the states with the updates
    // console.log(event);
    const { name, value } = event.target;
    switch (name) {
      case "professorName":
        setFeedbacks({
          id: ""
        });
        setErrors({});
        setProfessorName(value);
        break;
      case "id":
        setFeedbacks({
          id: ""
        });
        setErrors({});
        setId(value);
        break;
      case "healthState":
        setFeedbacks({
          healthState: ""
        });
        setErrors({});
        setHealthState(value);
        break;
      case "confineDate":
        setFeedbacks({
          confineDate: ""
        });
        setErrors({});
        setConfineDate(value);
        break;
      case "professorClasses":
        setFeedbacks({
          id: ""
        });
        setErrors({});
        setProfessorClasses(value);
        break;
      default:
        return;
    }
  };

  return (
    <Modal
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          {props.existingProfessor ? (
            <div>Propiedades del profesor </div>
          ) : (
            <div>Insertar profesor</div>
          )}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formProfessorName">
            <Form.Label>Nombre del profesor</Form.Label>
            <Form.Control
              required
              type="text"
              placeholder="Introduzca el nombre del profesor"
              name="professorName"
              onChange={updateFormState}
              value={professorName}
              isInvalid={!!errors.professorName}
            />
            <Form.Control.Feedback type="invalid">
              Nombre de profesor inválido
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="formNifNie">
            <Form.Label>NIF/NIE</Form.Label>
            <Form.Control
              autoFocus
              required
              type="text"
              placeholder="Introduzca el NIF/NIE"
              name="id"
              onChange={updateFormState}
              value={id}
              isInvalid={!!errors.id}
            />
            <Form.Text className="text-muted">
              Debe ser único en el centro
            </Form.Text>
            <Form.Control.Feedback type="invalid">
              {feedbacks.id}
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="professorClasses" className="classes">
            <Form.Label>Clases del profesor</Form.Label>
            <Form.Control as="select" multiple>
              {(allClasses || []).map(clase => {
                let professorClassesIds = professorClasses.map(
                  claseProfesor => {
                    return claseProfesor.id;
                  }
                );
                if (professorClassesIds.includes(clase.id)) {
                  return (
                    <option selected key={clase.id} value={clase.id}>
                      {clase.nombre}
                    </option>
                  );
                } else {
                  return (
                    <option key={clase.id} value={clase.id}>
                      {clase.nombre}
                    </option>
                  );
                }
              })}
            </Form.Control>
          </Form.Group>
          {props.existingProfessor ? (
            <Form.Group controlId="formHealthState">
              <Form.Label>Estado sanitario</Form.Label>
              <Form.Control
                as="select"
                name="healthState"
                value={healthState}
                onChange={updateFormState}
              >
                <option key="1" value="no confinado">
                  No confinado
                </option>
                <option key="2" value="confinado">
                  Confinado
                </option>
              </Form.Control>
              <Form.Control.Feedback type="invalid">
                Nombre de profesor inválido
              </Form.Control.Feedback>
              <Form.Control.Feedback type="valid">
                Perfecto
              </Form.Control.Feedback>
            </Form.Group>
          ) : null}
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onHide}>Cancelar</Button>
        <Button
          onClick={() =>
            props.onInsert(
              professorName,
              id,
              professorClasses,
              errors,
              feedbacks,
              setErrors,
              setFeedbacks
            )
          }
          disabled={
            id === "" || professorName === "" || professorClasses === ""
          }
        >
          {props.existingProfessor ? (
            <div>Aplicar cambios </div>
          ) : (
            <div>Insertar</div>
          )}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ProfessorCenteredModal;
