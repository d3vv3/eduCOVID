import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Local components
import ActionBar from "../components/ActionBar";
import ProfessorCenteredModal from "../components/NewProfessorFormModal";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Button from "react-bootstrap/Button";

function ManageProfessor(props) {
  const { onLogOut, userData } = props;

  const [professors, setProfessors] = useState([]);
  const [selected, setSelected] = useState([]);
  const [showNewModal, setShowNewModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);

  useEffect(() => {
    retrieveProfessors();
  }, []);

  const retrieveProfessors = async () => {
    const token = localStorage.getItem("token") || "";
    let response;
    let responseData;
    response = await fetch(backUrl + "/manage/professors", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });
    responseData = await response.json();
    setProfessors(responseData);
  };

  const onInsertProfessor = (name, id, professorClasses) => {
    const newProfessor = {
      nombre: name,
      hash: "",
      salt: "",
      subscriptionEndpoint: null,
      p256dh: null,
      auth: null,
      nifNie: id,
      estadoSanitario: "no confinado",
      fechaConfinamiento: null
    };
    try {
      professorClasses.forEach(async clase => {
        const res = await fetch(
          // TODO backend method and change the path
          backUrl +
            `/centro/insert/professor/${userData.centro}/${encodeURIComponent(
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
        if (!res.ok) {
          alert(`Hubo un fallo al crear el profesor`);
        } else {
          setShowNewModal(false);
        }
      });
    } catch (e) {
      console.log(e);
    } finally {
      retrieveProfessors();
    }
  };

  const onEditProfessor = async (name, id, professorClasses) => {
    await onInsertProfessor(name, id, professorClasses);
  };

  const handleDelete = async () => {
    let pendingDeletes = selected.map(professor => {
      return fetch(backUrl + `/professor/${professor.id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
          "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(professor)
      });
    });
    let responses = await Promise.all(pendingDeletes);
    responses.some(response =>
      !response.ok ? alert(`Hubo un fallo al borrar un profesor`) : null
    );
  };

  const handleHealthState = async action => {
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
        await fetch(
          backUrl + `/notification/subscription/professor/` + value.id,
          {
            method: "POST",
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json"
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
                    handleHealthState("confine");
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
                  if (selected != null) {
                    handleHealthState("unconfine");
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
              Añadir profesor
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
        <ProfessorCenteredModal
          existingProfessor={showEditModal ? selected[0] : null}
          show={showNewModal || showEditModal}
          onHide={() => {
            setShowNewModal(false);
            setShowEditModal(false);
            setSelected([]);
          }}
          onInsert={onInsertProfessor}
          onEdit={onEditProfessor}
        />
      ) : null}
    </div>
  );
}

export default withRouter(ManageProfessor);
