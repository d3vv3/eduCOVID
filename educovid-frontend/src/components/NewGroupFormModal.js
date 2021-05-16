import React, { useState, useEffect } from "react";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function GroupCenteredModal(props) {
  const {
    parentClass,
    existingGroup,
    handleEdit,
    handleInsert,
    onHide,
    show
  } = props;
  const [groupName, setGroupName] = useState(existingGroup?.nombre || "");
  const [id, setId] = useState(existingGroup?.id || "");
  const [groupStudents, setGroupStudents] = useState([]);
  const [allStudents, setAllStudents] = useState([]);
  const [errors, setErrors] = useState({});
  const [feedbacks, setFeedbacks] = useState({
    classes: ""
  });

  useEffect(() => {
    retrieveStudents();
  }, []);

  const retrieveStudents = async () => {
    const token = localStorage.getItem("token") || "";
    let response, responseData;
    response = await fetch(backUrl + `/centro/${props.centro}/students`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });
    responseData = await response.json();
    // console.log(responseData);
    setAllStudents(responseData.sort((a, b) => a.id - b.id));
    if (existingGroup) {
      response = await fetch(backUrl + `/alumno/grupo/${id}`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json"
        }
      });
      responseData = await response.json();
      // console.log(responseData);
      setGroupStudents(responseData.map(p => p.id.toString()));
    }
  };

  const updateFormState = event => {
    // On change, set the states with the updates
    //console.log(event);
    const { name, value } = event.target;
    //console.log(name, value);
    switch (name) {
      case "groupName":
        setFeedbacks({});
        setErrors({});
        setGroupName(value);
        setFeedbacks({
          groupName: ""
        });
        break;
      case "groupStudents":
        setFeedbacks({
          groupStudents: ""
        });
        setErrors({});
        let values = Array.prototype.slice
          .call(event.target.selectedOptions)
          .map(item => item.value.toString());
        setGroupStudents(values);
        break;
      default:
        return;
    }
  };

  return (
    <Modal
      className="group-modal-container"
      show
      onHide={onHide}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          {existingGroup ? (
            <div>Propiedades de la clase</div>
          ) : (
            <div>Añadir clase</div>
          )}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formGroupName">
            <Form.Label>Nombre del grupo</Form.Label>
            <Form.Control
              required
              type="text"
              placeholder="Introduzca el nombre de la clase"
              name="groupName"
              onChange={updateFormState}
              value={groupName}
              isInvalid={!!errors.groupName}
            />
            <Form.Control.Feedback type="invalid">
              Nombre de grupo inválido
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="groupStudents" className="classes">
            <Form.Label>Alumnos del grupo</Form.Label>
            <Form.Control
              as="select"
              name="groupStudents"
              multiple
              onChange={updateFormState}
              isInvalid={!!errors.groupStudents}
            >
              {(allStudents || []).map(student => {
                if (groupStudents.includes(student.id.toString())) {
                  return (
                    <option selected key={student.id} value={student.id}>
                      {student.nombre}
                    </option>
                  );
                } else {
                  return (
                    <option key={student.id} value={student.id}>
                      {student.nombre}
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
            if (!existingGroup) {
              handleInsert({
                name: groupName,
                groupStudents: allStudents
                  .map(p => {
                    if (groupStudents.includes(p.id.toString())) {
                      return p;
                    } else {
                      return false;
                    }
                  })
                  .filter(p => p)
              });
            } else {
              handleEdit({
                id,
                name: groupName,
                groupStudents: allStudents
                  .map(p => {
                    if (groupStudents.includes(p.id.toString())) {
                      return p;
                    } else {
                      return false;
                    }
                  })
                  .filter(p => p)
              });
            }
            props.onHide();
          }}
          disabled={groupName === "" || groupStudents.length === 0}
        >
          {existingGroup ? <div>Aplicar cambios </div> : <div>Añadir</div>}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default GroupCenteredModal;
