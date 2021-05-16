import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Local components
import GroupCenteredModal from "../components/NewGroupFormModal";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function NotificationModal(props) {
  const [confinedText, setConfinedText] = useState(
    "Tu grupo ha sido confinado"
  );
  const [unconfinedText, setUnconfinedText] = useState(
    "Tu grupo ha sido desconfinado"
  );

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
              <Form.Label>Mensaje para alumnos del grupo a confinar</Form.Label>
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
              <Form.Label>
                Mensaje para alumnos del grupo a desconfinar
              </Form.Label>
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
            setConfinedText("Tu grupo ha sido confinado");
            setUnconfinedText("Tu grupo ha sido desconfinado");
          }}
        >
          Finalizar
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

function ManageClass(props) {
  const { userData, setPage, setSelectedClass, selectedClass } = props;

  const [action, setAction] = useState("");
  const [groups, setGroups] = useState([]);
  const [selected, setSelected] = useState([]);
  const [showNewModal, setShowNewModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [notificationModalShow, setNotificationModalShow] = useState(false);

  useEffect(() => {
    retrieveGroups();
  }, []);

  const retrieveGroups = async () => {
    console.log("Refrescando grupos");
    const token = localStorage.getItem("token") || "";
    let response;
    let responseData;
    response = await fetch(backUrl + `/grupo/clase/${selectedClass.id}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });
    responseData = await response.json();
    setGroups(responseData);
  };

  const onInsertGroup = async ({ name, groupStudents }) => {
    const newGroup = {
      nombre: name,
      estadoSanitario: "no confinado",
      prioridad: null,
      alumnos: groupStudents
    };
    console.log(newGroup);
    try {
      const res0 = await fetch(
        backUrl +
          `/centro/insert/group/${userData?.centro}/${selectedClass.id}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify(newGroup)
        }
      );
      // console.log(res0);
      if (!res0.ok) {
        alert(`Hubo un fallo al crear el grupo`);
      } else {
        setShowNewModal(false);
      }
    } catch (e) {
      console.log(e);
    } finally {
      retrieveGroups();
    }
  };

  const onEditGroup = async ({ id, name, groupStudents }) => {
    const updatedGroup = {
      nombre: name,
      estadoSanitario: null,
      prioridad: null,
      alumnos: groupStudents
    };
    try {
      // console.log(newClass);
      const res = await fetch(
        backUrl + `/centro/update/group/${userData?.centro}/${id}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify(updatedGroup)
        }
      );
      // console.log(await res.json());
      if (!res.ok) {
        alert(`Hubo un fallo al modificar el grupo`);
      } else {
        setShowNewModal(false);
      }
    } catch (e) {
      console.log(e);
    } finally {
      retrieveGroups();
    }
  };

  const handleDelete = async () => {
    try {
      let res = await fetch(backUrl + `/grupo/delete/${selectedClass.id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
          "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(selected)
      });
      console.log(await res);
      if (!res.ok) {
        console.log(await res.json());
        alert(`Hubo un fallo al borrar un grupo`);
      }
    } catch (e) {
      console.log(e);
    } finally {
      await retrieveGroups();
    }
  };

  const handleAction = async (action, confinedText, unconfinedText) => {
    await fetch(backUrl + `/manage/${action}/bubbleGroups`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token") || ""}`
      },
      body: JSON.stringify(selected)
    });
    await retrieveGroups();
    // notify everyone
    selected.forEach(async value => {
      try {
        console.log(value);
        await fetch(
          backUrl + `/notification/subscription/bubbleGroups/` + value.id,
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
      <div className="manage-classes-container">
        <div className="left-menu">
          <div className="list-container">
            {(groups ?? []).map((item, index) => (
              <div
                key={index}
                onClick={e => {
                  if (!selected.some(e => e.nombre === item.nombre)) {
                    setSelected(selected.concat([item]));
                  }
                }}
                className={
                  "person-card green" +
                  (groups.some(e => e === item) ? "" : " selected")
                }
              >
                <h5>{item.nombre}</h5>
                <h6>
                  {item.estadoSanitario.charAt(0).toUpperCase() +
                    item.estadoSanitario.slice(1)}
                </h6>
                <h6>
                  {item.id === selectedClass.burbujaPresencial.id
                    ? "Turno:  presencial"
                    : "Turno:  no presencial"}
                </h6>
              </div>
            ))}
          </div>
        </div>
        <div className="right-options">
          <h2>Seleccionados</h2>
          <div className="seleccionados">
            {(selected || []).map((group, index) => (
              <div
                key={index}
                onClick={e => {
                  if (selected.some(e => e.nombre === group.nombre)) {
                    var filtered = selected.filter(function(value, index, arr) {
                      return value.nombre !== group.nombre;
                    });
                    setSelected(filtered);
                  }
                }}
                className={
                  "person-card green" +
                  (groups.some(e => e === group) ? " selected" : "")
                }
              >
                <h5>{group.nombre}</h5>
                <h6>
                  {group.estadoSanitario.charAt(0).toUpperCase() +
                    group.estadoSanitario.slice(1)}
                </h6>
                <h6>
                  {group.id === selectedClass.burbujaPresencial.id
                    ? "Turno: presencial"
                    : "Turno: no presencial"}
                </h6>
              </div>
            ))}
          </div>
        </div>
        <div className="buttons-container">
          <div className="padded">
            {selected.length === 1 ? (
              <>
                <Button
                  variant="primary"
                  className="nord-button"
                  onClick={e => {
                    if (selected.length === 1) {
                      setShowEditModal(true);
                    } else {
                      alert("Seleccione una sola clase para esta acción");
                    }
                  }}
                >
                  Propiedades
                </Button>
              </>
            ) : null}
            <Button
              variant="primary"
              className="nord-button"
              onClick={e => {
                setShowNewModal(true);
              }}
            >
              Añadir grupo
            </Button>
            <Button
              variant="primary"
              className="nord-button"
              onClick={e => {
                setPage("class");
                setSelectedClass({});
              }}
            >
              Volver
            </Button>
            {selected.length > 0 ? (
              <>
                <Button
                  variant="primary"
                  className="nord-button"
                  onClick={e => {
                    if (selected.length > 0) {
                      setAction("confine");
                      handleNotification();
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
                <Button
                  variant="primary"
                  className="nord-button red"
                  onClick={async e => {
                    if (selected.length > 0) {
                      handleDelete();
                      setSelected([]);
                    } else {
                      alert("Seleccione clases para ejecutar esta acción");
                    }
                  }}
                >
                  Borrar
                </Button>
              </>
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
        <GroupCenteredModal
          centro={userData.centro}
          existingGroup={showEditModal ? selected[0] : null}
          parentClass={selectedClass}
          show={showNewModal || showEditModal}
          onHide={() => {
            setShowNewModal(false);
            setShowEditModal(false);
            setSelected([]);
          }}
          handleInsert={onInsertGroup}
          handleEdit={onEditGroup}
        />
      ) : null}
    </div>
  );
}

export default withRouter(ManageClass);
