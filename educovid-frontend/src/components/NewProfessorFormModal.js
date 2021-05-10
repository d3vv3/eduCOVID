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
    setAllClasses(responseData.map(clase => clase.nombre));
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
      setProfessorClasses(responseData.map(clase => clase.nombre));
    }
  };

  const updateFormState = event => {
    // On change, set the states with the updates
    //console.log(event);
    const { name, value } = event.target;
    //console.log(name, value);
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
      case "professorClasses":
        setFeedbacks({
          professorClasses: ""
        });
        setErrors({});
        let values = Array.prototype.slice
          .call(event.target.selectedOptions)
          .map(item => item.value);
        setProfessorClasses(values);
        break;
      default:
        return;
    }
  };

  return (
    <Modal
      className="professor-modal-container"
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
            <Form.Control
              as="select"
              name="professorClasses"
              multiple
              onChange={updateFormState}
              isInvalid={!!errors.professorClasses}
            >
              {(allClasses || []).map(clase => {
                if (professorClasses.includes(clase)) {
                  return (
                    <option selected key={clase} value={clase}>
                      {clase}
                    </option>
                  );
                } else {
                  return (
                    <option key={clase} value={clase}>
                      {clase}
                    </option>
                  );
                }
              })}
            </Form.Control>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onHide}>Cancelar</Button>
        <Button
          onClick={() => {
            if (props.existingProfessor) {
              props.onInsert(
                professorName,
                id,
                professorClasses,
                errors,
                feedbacks,
                setErrors,
                setFeedbacks
              );
            } else {
              props.onEdit(
                professorName,
                id,
                professorClasses,
                errors,
                feedbacks,
                setErrors,
                setFeedbacks
              );
            }
            props.onHide();
          }}
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
