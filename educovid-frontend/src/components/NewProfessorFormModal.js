import React, { useState } from "react";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function ProfessorCenteredModal(props) {
  const [professorName, setProfessorName] = useState("");
  const [id, setId] = useState("");
  const [professorClasses, setProfessorClasses] = useState("");
  const [errors, setErrors] = useState({});
  const [feedbacks, setFeedbacks] = useState({
    id: "",
    classes: ""
  });

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
          Insertar Profesor
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formNumMat">
            <Form.Label>NIF/NIE</Form.Label>
            <Form.Control
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
          <Form.Group controlId="formStudentName">
            <Form.Label>Nombre del profesor</Form.Label>
            <Form.Control
              required
              autoFocus
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
          <Form.Group controlId="professorClasses">
            <Form.Label>Clases del profesor</Form.Label>
            <Form.Control
              required
              autoFocus
              type="text"
              placeholder="Introduzca las clases separadas por coma: 11,12,13"
              name="professorClasses"
              onChange={updateFormState}
              value={professorClasses}
              isInvalid={!!errors.professorClasses}
            />
            <Form.Control.Feedback type="invalid">
              Clases inválidas
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
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
          Insertar
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ProfessorCenteredModal;
