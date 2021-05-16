import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";
import StudentCenteredModal from "../components/NewStudentFormModal";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

// Constants
import { backUrl } from "../constants/constants";

function NotificationModal(props) {
  const [confinedText, setConfinedText] = useState("Has sido confinado");
  const [unconfinedText, setUnconfinedText] = useState("Has sido desconfinado");

  const { onHide, handleFinish, show, action } = props;

  return (
    <Modal
      onHide={onHide}
      show={show}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Mensaje explicativo
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          {action === "confine" ? (
            <Form.Group controlId="formConfinedText">
              <Form.Label>Mensaje para alumnos a confinar</Form.Label>
              <Form.Control
                placeholder="Introduzca el mensaje"
                onChange={e => {
                  setConfinedText(e.target.value);
                  // handleChangeConfineMessage(e.target.value);
                }}
                value={confinedText}
              />
            </Form.Group>
          ) : null}
          {action === "unconfine" ? (
            <Form.Group controlId="formUnconfinedText">
              <Form.Label>Mensaje para alumnos a desconfinar</Form.Label>
              <Form.Control
                placeholder="Introduzca el mensaje"
                onChange={e => {
                  setUnconfinedText(e.target.value);
                  // handleChangeUnconfineMessage(e.target.value);
                }}
                value={unconfinedText}
              />
            </Form.Group>
          ) : null}
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={onHide}>Cancelar</Button>
        <Button
          onClick={() => {
            handleFinish(confinedText, unconfinedText);
            setConfinedText("Has sido confinado");
            setUnconfinedText("Has sido desconfinado");
          }}
        >
          Finalizar
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

function ManageStudent(props) {
  const { history, onLogOut, userData } = props;

  const [action, setAction] = useState("");
  const [showNewModal, setShowNewModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [notificationModalShow, setNotificationModalShow] = useState(false);
  const [students, setStudents] = useState([]);
  const [bubbleGroups, setBubbleGroups] = useState([]);
  const [indivClasses, setIndivClasses] = useState([]);
  const [selected, setSelected] = useState([]);
  const [selectedFilter, setSelectedFilter] = useState("*");

  useEffect(() => {
    updateAll();
  }, [selectedFilter]);

  useEffect(() => {
    retrieveGlobalState();
  }, []);

  const updateAll = async () => {
    await refreshStudents();
    await retrieveGlobalState();
  };

  const refreshStudents = async () => {
    let response, responseData;
    if (userData === null) return;
    if (selectedFilter === "*") {
      // response = await fetch(backUrl + "/manage/students", {
      //   method: "GET",
      //   headers: {
      //     Authorization: `Bearer ${localStorage.getItem("token") || ""}`
      //   }
      // });
      response = await fetch(backUrl + `/centro/${userData?.centro}/students`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token") || ""}`
        }
      });
      responseData = await response.json();
      setStudents(responseData);
    } else {
      response = await fetch(backUrl + `/alumno/grupo/${selectedFilter}`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token") || ""}`
        }
      });
      responseData = await response.json();
      setStudents(responseData);
    }
  };

  const retrieveGlobalState = async () => {
    let response;
    let responseData;
    if (userData === null) return;
    // response = await fetch(backUrl + "/manage/bubblegroups", {
    //   method: "GET",
    //   headers: {
    //     Authorization: `Bearer ${localStorage.getItem("token") || ""}`
    //   }
    // });
    response = await fetch(
      backUrl + `/centro/${userData?.centro}/bubblegroups`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token") || ""}`
        }
      }
    );
    responseData = await response.json();
    setBubbleGroups(responseData);
    response = await fetch(backUrl + `/centro/${userData?.centro}/classes`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token") || ""}`
      }
    });
    responseData = await response.json();
    // console.log(responseData);
    setIndivClasses(responseData);
  };

  const handleEditStudent = async ({
    id,
    name,
    numMat,
    subscriptionEndpoint,
    p256dh,
    auth,
    studentClass,
    studentBubbleGroup,
    healthState,
    confineDate
  }) => {
    const newStudent = {
      id,
      nombre: name,
      hash: "",
      salt: "",
      subscriptionEndpoint: null,
      p256dh: null,
      auth: null,
      numeroMatricula: numMat,
      estadoSanitario: healthState,
      fechaConfinamiento: confineDate
    };
    try {
      const res = await fetch(
        backUrl +
          `/centro/update/alumno/${userData?.centro}/${studentClass}/${studentBubbleGroup}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify(newStudent)
        }
      );
      if (!res.ok) {
        alert("Hubo un fallo al actualizar el alumno");
      } else {
        setShowNewModal(false);
      }
    } catch (e) {
      console.log(e);
    } finally {
      updateAll();
    }
  };

  const handleInsertStudent = async (
    name,
    numMat,
    studentClass,
    studentBubbleGroup
  ) => {
    const newStudent = {
      nombre: name,
      hash: "",
      salt: "",
      subscriptionEndpoint: null,
      p256dh: null,
      auth: null,
      numeroMatricula: numMat,
      estadoSanitario: "no confinado"
    };
    try {
      const res = await fetch(
        backUrl +
          `/centro/insert/alumno/${userData?.centro}/${studentClass}/${studentBubbleGroup}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify(newStudent)
        }
      );
      if (!res.ok) {
        alert("Hubo un fallo al crear el alumno");
      } else {
        setShowNewModal(false);
      }
    } catch (e) {
      // Nothing to do
    }
    updateAll();
  };

  const handleDelete = async () => {
    let pendingDeletes = selected.map(student => {
      return fetch(backUrl + `/alumno/${student.id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
          "Content-Type": "application/json; charset=UTF-8"
        }
        // body: JSON.stringify(student)
      });
    });
    let responses = await Promise.all(pendingDeletes);
    responses.some(response =>
      !response.ok ? alert(`Hubo un fallo al borrar un profesor`) : null
    );
    await updateAll();
  };

  const handleAction = async (action, confinedText, unconfinedText) => {
    await fetch(backUrl + `/manage/${action}/students`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token") || ""}`
      },
      body: JSON.stringify(selected)
    });
    await refreshStudents();
    // notify everyone
    selected.forEach(async value => {
      try {
        console.log(value);
        const notificationRes = await fetch(
          backUrl + `/notification/subscription/students/` + value.id,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.getItem("token") || ""}`
            },
            body: JSON.stringify({
              confineMessage: confinedText,
              unconfineMessage: unconfinedText
            })
          }
        );
      } catch (e) {
        console.log("Error pushing notification");
      }
    });
  };

  const handleNotification = async () => {
    setNotificationModalShow(true);
  };

  return (
    <div>
      <ActionBar
        onLogOut={() => {
          onLogOut();
        }}
      />
      <div className="manage-student-container">
        <div className="left-menu">
          <div className="selector">
            <Form>
              <Form.Group controlId="group">
                <Form.Control
                  as="select"
                  defaultValue="*"
                  onChange={e => {
                    setSelected([]);
                    setSelectedFilter(e.target.value);
                    refreshStudents();
                  }}
                >
                  <option key="0" value="*">
                    Todos
                  </option>
                  {(bubbleGroups || []).map((group, index) => (
                    <option key={index} value={group.id}>
                      {
                        indivClasses.find(
                          indivClass =>
                            indivClass.gruposBurbuja
                              .map(grupoBurbuja => grupoBurbuja.id)
                              .indexOf(group.id) !== -1
                        )?.nombre
                      }{" "}
                      - {group.nombre}
                    </option>
                  ))}
                </Form.Control>
              </Form.Group>
            </Form>
          </div>

          <div className="list-container">
            {(students || []).map((item, index) => (
              <div
                key={index}
                onClick={e => {
                  if (!selected.some(e => e.nombre === item.nombre)) {
                    setSelected(selected.concat([item]));
                  }
                }}
                className={
                  "person-card" +
                  " green" +
                  (students.some(e => e === item) ? "" : " selected")
                }
              >
                <h5>{item.nombre}</h5>
                <h6>
                  {item.estadoSanitario === "no confinado"
                    ? "No confinado"
                    : "Confinado"}
                </h6>
                <h6>
                  {indivClasses
                    .map(clase => {
                      if (
                        clase?.burbujaPresencial?.alumnos
                          .map(a => a.id)
                          .includes(item.id)
                      ) {
                        return true; // Presencial
                      } else {
                        return false; // No presencial
                      }
                    })
                    .some(i => i === true)
                    ? "Presencial"
                    : "No presencial"}
                </h6>
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
                    var filtered = selected.filter(function(value, index, arr) {
                      return value.nombre !== person.nombre;
                    });
                    setSelected(filtered);
                  }
                }}
                className={
                  "person-card" +
                  " green" +
                  (students.some(e => e === person) ? " selected" : "")
                }
              >
                <h5>{person.nombre}</h5>
                <h6>
                  {person.estadoSanitario === "no confinado"
                    ? "No confinado"
                    : "Confinado"}
                </h6>
                <h6>
                  {indivClasses
                    .map(clase => {
                      if (
                        clase?.burbujaPresencial?.alumnos
                          .map(a => a.id)
                          .includes(person.id)
                      ) {
                        return true; // Presencial
                      } else {
                        return false; // No presencial
                      }
                    })
                    .some(i => i === true)
                    ? "Presencial"
                    : "No presencial"}
                </h6>
              </div>
            ))}
          </div>
        </div>
        <div className="buttons-container">
          {selected.length > 0 ? (
            <div className="padded">
              <Button
                variant="primary"
                className="nord-button"
                onClick={e => {
                  if (selected.length > 0) {
                    setAction("confine");
                    handleNotification();
                    //handleAction("confine");
                    //setSelected([]);
                  } else {
                    alert("Seleccione las personas a confinar");
                  }
                }}
              >
                Confinar
              </Button>
              <Button
                variant="primary"
                className="nord-button"
                onClick={e => {
                  // if (selected != null) {
                  // let x = selected;
                  // let confined = x.forEach(e =>
                  //   e.estadoSanitario === "confinado"
                  //     ? (e.estadoSanitario = "no confinado")
                  //     : (e.estadoSanitario = "confinado")
                  // );
                  //handleConfine();
                  //setSelected([]);
                  if (selected.length > 0) {
                    setAction("unconfine");
                    handleNotification();
                    //handleAction("unconfine");
                    //setSelected([]);
                  } else {
                    alert("Seleccione las personas a confinar");
                  }
                }}
              >
                Desconfinar
              </Button>
            </div>
          ) : null}
          <div className="padded">
            {selected.length === 1 ? (
              <Button
                variant="primary"
                className="nord-button"
                onClick={e => {
                  if (selected.length === 1) {
                    setShowEditModal(true);
                  } else {
                    alert("Seleccione un solo profesor para esta acción");
                  }
                }}
              >
                Propiedades
              </Button>
            ) : null}
            <Button
              variant="primary"
              className="nord-button"
              onClick={e => {
                setShowNewModal(true);
              }}
            >
              Añadir alumno
            </Button>
            {selected.length > 0 ? (
              <Button
                variant="primary"
                className="nord-button red"
                onClick={e => {
                  if (selected.length > 0) {
                    handleDelete();
                    setSelected([]);
                  } else {
                    alert("Seleccione personas para ejecutar esta acción");
                  }
                }}
              >
                Borrar
              </Button>
            ) : null}
          </div>
        </div>
      </div>

      {notificationModalShow ? (
        <NotificationModal
          show={notificationModalShow}
          action={action}
          onHide={() => setNotificationModalShow(false)}
          handleFinish={(confinedText, unconfinedText) => {
            // handleConfine();
            handleAction(action, confinedText, unconfinedText);
            setSelected([]);
            setNotificationModalShow(false);
          }}
        />
      ) : null}

      {showNewModal || showEditModal ? (
        <StudentCenteredModal
          existingStudent={showEditModal ? selected[0] : null}
          show={showNewModal || showEditModal}
          onHide={() => {
            setShowNewModal(false);
            setShowEditModal(false);
            setSelected([]);
          }}
          handleInsert={handleInsertStudent}
          handleEdit={handleEditStudent}
        />
      ) : null}
    </div>
  );
}

export default withRouter(ManageStudent);
