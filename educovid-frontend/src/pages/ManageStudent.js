import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";
import StudentCenteredModal from "../components/NewStudentFormModal";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

// Constants
import { backUrl } from "../constants/constants";

function ManageStudent(props) {
  const { onLogOut, userData } = props;

  const [showNewModal, setShowNewModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [students, setStudents] = useState([]);
  const [bubbleGroups, setBubbleGroups] = useState([]);
  const [selected, setSelected] = useState([]);
  const [selectedFilter, setSelectedFilter] = useState("*");

  useEffect(() => {
    refreshStudents();
  }, [selectedFilter]);

  useEffect(() => {
    initComponent();
  }, []);

  const refreshStudents = async () => {
    let response, responseData;
    if (selectedFilter === "*") {
      response = await fetch(backUrl + "/manage/students", {
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

  const initComponent = async () => {
    let response;
    let responseData;
    response = await fetch(backUrl + "/manage/bubblegroups", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token") || ""}`
      }
    });
    responseData = await response.json();
    setBubbleGroups(responseData);
    refreshStudents();
  };

  const onEditStudent = async ({
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
      refreshStudents();
    }
  };

  const onInsertStudent = async (
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
      estadoSanitario: "no confinado",
      fechaConfinamiento: null
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
    refreshStudents();
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
    refreshStudents();
  };

  const handleAction = async action => {
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
        await fetch(
          backUrl + `/notification/subscription/students/` + value.id,
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token") || ""}`
            }
          }
        );
      } catch (e) {
        console.log("Error pushing notification");
      }
    });
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
                      {group.nombre}
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
                {item?.nombre?.includes("Grupo") ? (
                  <h5>{item.nombre}</h5>
                ) : (
                  <h5>{item.nombre}</h5>
                )}
                <h6>
                  {item.estadoSanitario === "no confinado"
                    ? "No confinado"
                    : "Confinado"}
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
                {person.nombre.includes("Grupo") ? (
                  <h5>{person.nombre}</h5>
                ) : (
                  <h5>{person.nombre}</h5>
                )}
                <h6>
                  {person.estadoSanitario === "no confinado"
                    ? "No confinado"
                    : "Confinado"}
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
                  if (selected.length < 1) {
                    handleAction("confine");
                    setSelected([]);
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
                  if (selected.length < 1) {
                    handleAction("unconfine");
                    setSelected([]);
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
      {showNewModal || showEditModal ? (
        <StudentCenteredModal
          existingStudent={showEditModal ? selected[0] : null}
          show={showNewModal || showEditModal}
          onHide={() => {
            setShowNewModal(false);
            setShowEditModal(false);
            setSelected([]);
          }}
          handleInsert={onInsertStudent}
          handleEdit={onEditStudent}
        />
      ) : null}
    </div>
  );
}

export default withRouter(ManageStudent);
