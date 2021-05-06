import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";


// Constants
import { backUrl } from "../constants/constants";

function MyVerticallyCenteredModal(props) {
  const [studentName, setStudentName] = useState("");
  const [numMat, setNumMat] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});
  const [feedbacks, setFeedbacks] = useState({
    numMat: ""
  });

  const updateFormState = event => {
    // On change, set the states with the updates
    // console.log(event);
    const { name, value } = event.target;
    switch (name) {
      case "studentName":
        setStudentName(value);
        break;
      case "numMat":
        setNumMat(value);
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
          Insertar Alumno
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
            <Form.Control.Feedback type="valid">
              Perfecto
            </Form.Control.Feedback>
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
            <Form.Control.Feedback type="valid">
              Perfecto
            </Form.Control.Feedback>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onInsert}>Insertar</Button>
        <Button onClick={props.onCancel}>Cancelar</Button>
      </Modal.Footer>
    </Modal>
  );
}

function ManageStudent(props) {
  const { history, onLogOut } = props;

  const [modalShow, setModalShow] = React.useState(false);
  const [students, setStudents] = useState([]);
  const [bubbleGroups, setBubbleGroups] = useState([]);
  const [selectedType, setSelectedType] = useState("students");
  const [selected, setSelected] = useState([]);
  const [selectedFilter, setSelectedFilter] = useState("*");

  const initComponent = async () => {
    let response;
    let responseData;
    response = await fetch(backUrl + "/manage/bubblegroups", {
      method: "GET",
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
      }
    });
    responseData = await response.json();
    setBubbleGroups(responseData);
    response = await fetch(backUrl + "/manage/students", {
      method: "GET",
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
      }
    });
    responseData = await response.json();
    setStudents(responseData);
    console.log("UPDATED A");
  };

  const callMainSelector = async () => {
    // const bubbleGroupsRes = await fetch(backUrl + "/manage/bubblegroups");
    let response;
    let responseData;
    switch (selectedType) {
      case "bubbleGroups":
        response = await fetch(backUrl + "/manage/bubblegroups", {
          method: "GET",
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
          }
        });
        responseData = await response.json();
        setBubbleGroups(responseData);
        break;
      case "students":
        if (selectedFilter === "*") {
          response = await fetch(backUrl + "/manage/students", {
            method: "GET",
            headers: {
              'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
            }
          });
          responseData = await response.json();
          setStudents(responseData);
        } else {
          response = await fetch(backUrl + `/alumno/grupo/${selectedFilter}`, {
            method: "GET",
            headers: {
              'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
            }
          });
          responseData = await response.json();
          console.log(responseData);
          console.log();
          setStudents(responseData);
        }
        break;
      default:
        break;
    }
  };

  useEffect(() => {
    callMainSelector();
  }, [selectedType, selectedFilter]);

  useEffect(() => {
    initComponent();
  }, []);

  const getListOnSelectedType = () => {
    switch (selectedType) {
      case "students":
        return students;
      default:
        return students;
    }
  };

  const handleConfine = async () => {
    selected.every(e => console.log(e.estadoSanitario.toLowerCase()));
    if (selected.every(e => e.estadoSanitario.toLowerCase() === "confinado")) {
      await fetch(backUrl + `/manage/unconfine/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem('token') || ""}` },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    } else if (
      selected.every(e => e.estadoSanitario.toLowerCase() === "no confinado")
    ) {
      await fetch(backUrl + `/manage/confine/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem('token') || ""}` },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    } else {
      await fetch(backUrl + `/manage/switch/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem('token') || ""}` },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    }
    // notify everyone
    selected.forEach(async (value) => {
      try {
        console.log(value);
        const notificationRes = await fetch(
          backUrl +
          `/notification/subscription/${selectedType}/` + value.id, {
          method: "GET",
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
          }
        });
      } catch (e) {
        console.log("Error pushing notification");
      }
    });
  };

  const handleInsertStudent = async () => {
    setModalShow(true);
  };

  return (
    <div>
      <ActionBar
        onLogOut={() => {
          onLogOut();
        }}
      />
      <div className="confine-container">
        <div className="left-menu">
          <div className="selector">
            {selectedType === "students" ? (
              <Form>
                <Form.Group controlId="group">
                  <Form.Control
                    as="select"
                    defaultValue="*"
                    onChange={e => {
                      setSelected([]);
                      setSelectedFilter(e.target.value);
                      callMainSelector();
                    }}
                  >
                    <option key="0" value="*">
                      Todos
                    </option>
                    {(bubbleGroups || []).map((group, index) => (
                      <option key={index} value={group.id}>
                        {group.nombre}
                      </option>
                    ))}
                  </Form.Control>
                </Form.Group>
              </Form>
            ) : (
              <div />
            )}
          </div>

          <div className="list-container">
            {(getListOnSelectedType() || []).map((item, index) => (
              <div
                key={index}
                onClick={e => {
                  if (!selected.some(e => e.nombre === item.nombre)) {
                    setSelected(selected.concat([item]));
                  }
                }}
                className={
                  "person-card" + " green" +
                  (getListOnSelectedType().some(e => e === item)
                    ? ""
                    : " selected")
                }
              >
                {item?.nombre?.includes("Grupo") ? (
                  <h5>{item.nombre}</h5>
                ) : (
                  <h5>{item.nombre}</h5>
                )}
                <h8>
                  {item.estadoSanitario === "no confinado"
                    ? "No confinado"
                    : "Confinado"}
                </h8>
              </div>
            ))}
          </div>
        </div>
        <div className="right-options">
          <h2>Seleccionados</h2>
          <div className="seleccionados">
            {(selected || []).map((person, index) => (
              <div
                key={index}
                onClick={e => {
                  if (selected.some(e => e.nombre === person.nombre)) {
                    var filtered = selected.filter(function (value, index, arr) {
                      return value.nombre !== person.nombre;
                    });
                    setSelected(filtered);
                  }
                }}
                className={
                  "person-card" + " green" +
                  (getListOnSelectedType().some(e => e === person)
                    ? " selected"
                    : "")
                }
              >
                {person.nombre.includes("Grupo") ? (
                  <h5>{person.nombre}</h5>
                ) : (
                  <h5>{person.nombre}</h5>
                )}
                <h8>
                  {person.estadoSanitario === "no confinado"
                    ? "No confinado"
                    : "Confinado"}
                </h8>
              </div>
            ))}
          </div>
        </div>
        <div className="buttons-container">
          <Form>
            {selected.length > 0 ? (
              <Button
                variant="primary"
                className="nord-button"
                onClick={e => {
                  if (selected != null) {
                    // let x = selected;
                    // let confined = x.forEach(e =>
                    //   e.estadoSanitario === "confinado"
                    //     ? (e.estadoSanitario = "no confinado")
                    //     : (e.estadoSanitario = "confinado")
                    // );
                    handleConfine();
                    setSelected([]);
                  } else {
                    alert("Seleccione las personas a confinar");
                  }
                }}
              >
                {selected.every(
                  e => e.estadoSanitario.toLowerCase() === "confinado"
                )
                  ? "Desconfinar"
                  : selected.every(
                    e => e.estadoSanitario.toLowerCase() === "no confinado"
                  )
                    ? "Confinar"
                    : "Cambiar estados"}
              </Button>
            ) : null}
            <Button
              variant="primary"
              className="nord-button"
              onClick={e => {
                handleInsertStudent();
              }}
            >
              Insertar alumno
              </Button>
          </Form>
        </div>
      </div>

      <MyVerticallyCenteredModal show={modalShow} onCancel={() => setModalShow(false)} />
    </div>
  );
}

export default withRouter(ManageStudent);
