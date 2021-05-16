import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Local components
import ActionBar from "../components/ActionBar";
import ProfessorCenteredModal from "../components/NewProfessorFormModal";
import NotificationModal from "../components/NotificationModal";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Button from "react-bootstrap/Button";

function ManageProfessor(props) {
  const { onLogOut, userData } = props;

  const [action, setAction] = useState("");
  const [professors, setProfessors] = useState([]);
  const [selected, setSelected] = useState([]);
  const [showNewModal, setShowNewModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [notificationModalShow, setNotificationModalShow] = React.useState(false);


  useEffect(() => {
    retrieveProfessors();
  }, []);

  const retrieveProfessors = async () => {
    console.log("Recargando profesores");
    setProfessors([]);
    const token = localStorage.getItem("token") || "";
    let response;
    let responseData;
    response = await fetch(backUrl + `/centro/${userData?.centro}/professors`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });
    responseData = await response.json();
    setProfessors(responseData.sort((a, b) => a.id - b.id));
  };

  const handleInsertProfessor = async (id, name, nifNie, professorClasses) => {
    console.log("INSERT PROFE");
    const newProfessor = {
      nombre: name,
      hash: "",
      salt: "",
      subscriptionEndpoint: null,
      p256dh: null,
      auth: null,
      nifNie: nifNie,
      estadoSanitario: "no confinado"
    };
    try {
      professorClasses.forEach(async clase => {
        const res = await fetch(
          backUrl +
            `/centro/insert/professor/${userData?.centro}/${encodeURIComponent(
              clase
            )}`,
          {
            method: "POST",
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
              "Content-Type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(newProfessor)
          }
        );
        await res;
        if (!res.ok) {
          alert(`Hubo un fallo al crear el profesor`);
        } else {
          setShowNewModal(false);
        }
      });
    } catch (e) {
      console.log(e);
    } finally {
      await retrieveProfessors();
    }
  };

  const handleEditProfessor = async (
    id,
    name,
    nifNie,
    professorClasses,
    estadoSanitario,
    subscriptionEndpoint,
    p256dh,
    auth,
    fechaConfinamiento
  ) => {
    console.log("EDIT PROFE");
    // Create updated professor
    const newProfessor = {
      id,
      nombre: name,
      hash: "",
      salt: "",
      subscriptionEndpoint,
      p256dh,
      auth,
      nifNie,
      estadoSanitario,
      fechaConfinamiento
    };
    try {
      // Update professor on backend
      let res = await fetch(backUrl + `/professor/${encodeURIComponent(id)}`, {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
          "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(newProfessor)
      });
      await res;
      if (!res.ok) {
        alert(`Hubo un fallo al actualizar el profesor`);
      }
      res = await fetch(
        backUrl +
          `/centro/update/professor/${userData?.centro}/${encodeURIComponent(
            nifNie
          )}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify(professorClasses)
        }
      );
      await res;
      if (!res.ok) {
        alert(`Hubo un fallo al actualizar el profesor`);
      }
    } catch (e) {
      console.log(e);
    } finally {
      await retrieveProfessors();
    }
  };

  const handleDelete = async () => {
    selected.forEach(async professor => {
      let res = await fetch(backUrl + `/professor/${professor.id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
          "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(professor)
      });
      await res;
      if (!res.ok) {
        alert("Hubo un fallo al borrar al profesor " + professor.nombre);
      }
      await retrieveProfessors();
    });
  };

  const handleHealthState = async (action, confinedText, unconfinedText) => {
    const token = localStorage.getItem("token") || "";
    await fetch(backUrl + `/manage/${action}/professors`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(selected)
    });
    await retrieveProfessors();

    // notify everyone
    selected.forEach(async value => {
      try {
        const notificationRes = await fetch(
          backUrl +
          `/notification/subscription/professors/` + value.id, {
          method: "POST",
          headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem('token') || ""}` },
          body: JSON.stringify({ "confineMessage": confinedText, "unconfineMessage": unconfinedText })
        });
      } catch (e) {
        console.log("Error pushing notification");
      }
    });
  };

  const handleNotification = async () => {
    setNotificationModalShow(true);
  }

  return (
    <div>
      <ActionBar
        onLogOut={() => {
          onLogOut();
        }}
      />
      <div className="manage-professor-container">
        <div className="left-menu">
          <div className="list-container">
            {(professors ?? []).map((item, index) => (
              <div
                key={index}
                onClick={e => {
                  if (!selected.some(e => e.nombre === item.nombre)) {
                    setSelected(selected.concat([item]));
                  }
                }}
                className={
                  "person-card green" +
                  (professors.some(e => e === item) ? "" : " selected")
                }
              >
                <h5>{item.nombre}</h5>
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
                  "person-card green" +
                  (professors.some(e => e === person) ? " selected" : "")
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
                  if (selected.length > 0) {
                    setAction("confine");
                    handleNotification();
                    //handleHealthState("confine");
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
                  if (selected != null) {
                    setAction("unconfine");
                    handleNotification();
                    //handleHealthState("unconfine");
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
              Añadir profesor
            </Button>
            {selected.length > 0 ? (
              <Button
                variant="primary"
                className="nord-button red"
                onClick={async e => {
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

      {notificationModalShow ? <NotificationModal
        show={notificationModalShow}
        action={action}
        onHide={() => setNotificationModalShow(false)}
        handleFinish={(confinedText, unconfinedText) => {
          // handleConfine();
          handleHealthState(action, confinedText, unconfinedText);
          setSelected([]);
          setNotificationModalShow(false);
        }}
      /> : null}

      {showNewModal || showEditModal ? (
        <ProfessorCenteredModal
          existingProfessor={showEditModal ? selected[0] : null}
          show={showNewModal || showEditModal}
          onHide={() => {
            setShowNewModal(false);
            setShowEditModal(false);
            setSelected([]);
          }}
          handleInsert={handleInsertProfessor}
          handleEdit={handleEditProfessor}
        />
      ) : null}
    </div>
  );
}

export default withRouter(ManageProfessor);
