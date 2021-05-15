import React, { useState, useEffect } from "react";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function ClassCenteredModal(props) {
  const { existingClass, handleEdit, handleInsert } = props;
  const [invidClassName, setIndivClassName] = useState(
    existingClass?.nombre || ""
  );
  const [id, setId] = useState(existingClass?.id || "");
  const [classProfessors, setClassProfessors] = useState([]);
  const [allProfessors, setAllProfessors] = useState([]);
  const [errors, setErrors] = useState({});
  const [feedbacks, setFeedbacks] = useState({
    classes: ""
  });

  useEffect(() => {
    retrieveProfessors();
  }, []);

  const retrieveProfessors = async () => {
    const token = localStorage.getItem("token") || "";
    let response, responseData;
    response = await fetch(backUrl + `/centro/${props.centro}/professors`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });
    responseData = await response.json();
    setAllProfessors(responseData.sort((a, b) => a.id - b.id));
    if (props.existingClass) {
      response = await fetch(
        backUrl +
          `/professor/professors/${props.centro}/${props.existingClass.nombre}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        }
      );
      responseData = await response.json();
      setClassProfessors(responseData.map(p => p.id.toString()));
    }
  };

  const updateFormState = event => {
    // On change, set the states with the updates
    //console.log(event);
    const { name, value } = event.target;
    //console.log(name, value);
    switch (name) {
      case "indivClassName":
        setFeedbacks({});
        setErrors({});
        setIndivClassName(value);
        break;
      case "classProfessors":
        setFeedbacks({
          classProfessors: ""
        });
        setErrors({});
        let values = Array.prototype.slice
          .call(event.target.selectedOptions)
          .map(item => item.value.toString());
        setClassProfessors(values);
        break;
      default:
        return;
    }
  };

  return (
    <Modal
      className="class-modal-container"
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          {existingClass ? (
            <div>Propiedades de la clase</div>
          ) : (
            <div>Añadir clase</div>
          )}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formIndivClassName">
            <Form.Label>Nombre de la clase</Form.Label>
            <Form.Control
              required
              type="text"
              placeholder="Introduzca el nombre de la clase"
              name="indivClassName"
              onChange={updateFormState}
              value={invidClassName}
              isInvalid={!!errors.indivClassName}
            />
            <Form.Control.Feedback type="invalid">
              Nombre de clase inválido
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="classProfessors" className="classes">
            <Form.Label>Profesores de la clase</Form.Label>
            <Form.Control
              as="select"
              name="classProfessors"
              multiple
              onChange={updateFormState}
              isInvalid={!!errors.classProfessors}
            >
              {(allProfessors || []).map(professor => {
                if (classProfessors.includes(professor.id.toString())) {
                  return (
                    <option selected key={professor.id} value={professor.id}>
                      {professor.nombre}
                    </option>
                  );
                } else {
                  return (
                    <option key={professor.id} value={professor.id}>
                      {professor.nombre}
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
            if (!existingClass) {
              handleInsert(invidClassName, classProfessors);
            } else {
              handleEdit({
                id,
                nombre: invidClassName,
                burbujaPresencial: existingClass?.burbujaPresencial,
                fechaInicioConmutacion: existingClass?.fechaInicioConmutacion,
                tiempoConmutacion: existingClass?.tiempoConmutacion,
                profesores: allProfessors
                  .map(p => {
                    if (classProfessors.includes(p.id.toString())) {
                      return p.id;
                    } else {
                      return false;
                    }
                  })
                  .filter(p => p),
                gruposBurbuja: existingClass?.gruposBurbuja
              });
            }
            props.onHide();
          }}
          disabled={invidClassName === "" || classProfessors === ""}
        >
          {existingClass ? <div>Aplicar cambios </div> : <div>Añadir</div>}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ClassCenteredModal;
