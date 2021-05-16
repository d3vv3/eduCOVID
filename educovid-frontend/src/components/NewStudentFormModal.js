import React, { useState, useEffect } from "react";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function StudentCenteredModal(props) {
  const { existingStudent, handleInsert, handleEdit } = props;
  const [studentName, setStudentName] = useState(existingStudent?.nombre || "");
  const [numMat, setNumMat] = useState(existingStudent?.numeroMatricula || "");
  const [studentBubbleGroup, setStudentBubbleGroup] = useState("");
  const [studentClass, setStudentClass] = useState("");
  const [allClasses, setAllClasses] = useState([]);
  const [errors, setErrors] = useState({});
  const [feedbacks, setFeedbacks] = useState({
    numMat: ""
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
    // console.log(responseData);
    setAllClasses(responseData);
    setStudentClass(responseData[0]?.nombre);
    setStudentBubbleGroup(responseData[0]?.gruposBurbuja[0]?.nombre);
    if (existingStudent) {
      response = await fetch(backUrl + `/clase/alumno/${existingStudent.id}`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json"
        }
      });
      responseData = await response.json();
      // console.log(responseData);
      setStudentClass(responseData.nombreClase);
      response = await fetch(backUrl + `/grupo/alumno/${existingStudent.id}`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json"
        }
      });
      responseData = await response.json();
      setStudentBubbleGroup(responseData.nombre);
    }
  };

  const updateFormState = event => {
    // On change, set the states with the updates
    const { name, value } = event.target;
    switch (name) {
      case "studentName":
        setFeedbacks({
          numMat: ""
        });
        setErrors({});
        setStudentName(value);
        break;
      case "numMat":
        setFeedbacks({
          numMat: ""
        });
        setErrors({});
        setNumMat(value);
        break;
      case "studentBubbleGroup":
        setFeedbacks({
          numMat: ""
        });
        setErrors({});
        setStudentBubbleGroup(value);
        break;
      case "studentClass":
        setFeedbacks({
          numMat: ""
        });
        setErrors({});
        setStudentClass(value);
        setStudentBubbleGroup(
          allClasses.filter(clase => clase.nombre === value)[0]
            ?.gruposBurbuja[0].nombre
        );
        break;
      default:
        return;
    }
  };

  return (
    <Modal
      className="student-modal-container"
      onHide={props.onHide}
      show={props.show}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          {existingStudent ? (
            <div>Propiedades del alumno </div>
          ) : (
            <div>Insertar alumno</div>
          )}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formStudentName">
            <Form.Label>Nombre del alumno</Form.Label>
            <Form.Control
              required
              autoFocus
              type="text"
              placeholder="Introduzca el nombre del alumno"
              name="studentName"
              onChange={updateFormState}
              value={studentName}
              isInvalid={!!errors.studentName}
            />
            <Form.Control.Feedback type="invalid">
              Nombre de alumno inválido
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="formNumMat">
            <Form.Label>Número de matrícula</Form.Label>
            <Form.Control
              required
              type="text"
              placeholder="Introduzca el número de matrícula"
              name="numMat"
              onChange={updateFormState}
              value={numMat}
              isInvalid={!!errors.numMat}
            />
            <Form.Text className="text-muted">
              Debe ser único en el centro
            </Form.Text>
            <Form.Control.Feedback type="invalid">
              {feedbacks.numMat}
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="formStudentClass">
            <Form.Label>Clase del alumno</Form.Label>
            <Form.Control
              as="select"
              name="studentClass"
              value={studentClass}
              isInvalid={!!errors.studentClass}
              onChange={updateFormState}
            >
              {(allClasses || []).map(clase => {
                return (
                  <option key={clase.id} value={clase.nombre}>
                    {clase.nombre}
                  </option>
                );
              })}
            </Form.Control>
            <Form.Control.Feedback type="invalid">
              Nombre de grupo inválido
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="formStudentBubbleGroup">
            <Form.Label>Grupo burbuja del alumno</Form.Label>
            <Form.Control
              as="select"
              name="studentBubbleGroup"
              value={studentBubbleGroup}
              isInvalid={!!errors.studentClass}
              onChange={updateFormState}
            >
              {(
                (allClasses || []).filter(
                  clase => clase.nombre === studentClass
                )[0]?.gruposBurbuja || []
              ).map(grupo => {
                return (
                  <option key={grupo.id} value={grupo.nombre}>
                    {grupo.nombre}
                  </option>
                );
              })}
            </Form.Control>
            <Form.Control.Feedback type="invalid">
              {feedbacks.studentBubbleGroup}
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onHide}>Cancelar</Button>
        <Button
          onClick={() => {
            if (!existingStudent) {
              handleInsert(
                studentName,
                numMat,
                studentClass,
                studentBubbleGroup
              );
            } else {
              handleEdit({
                id: existingStudent?.id,
                name: studentName,
                numMat: numMat,
                subscriptionEndpoint: existingStudent?.subscriptionEndpoint,
                p256dh: existingStudent?.p256dh,
                auth: existingStudent?.auth,
                studentClass: studentClass,
                studentBubbleGroup: studentBubbleGroup,
                healthState: existingStudent?.estadoSanitario
              });
            }
            props.onHide();
          }}
          disabled={
            numMat === "" ||
            studentName === "" ||
            studentBubbleGroup === "" ||
            studentClass === ""
          }
        >
          {existingStudent ? <div>Aplicar cambios </div> : <div>Insertar</div>}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}
export default StudentCenteredModal;
