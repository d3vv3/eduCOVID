import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Local components
import GroupCenteredModal from "../components/NewGroupFormModal";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Button from "react-bootstrap/Button";

function ManageClass(props) {
  const { userData, setPage, setSelectedClass, selectedClass } = props;

  const [groups, setGroups] = useState([]);
  const [selected, setSelected] = useState([]);
  const [showNewModal, setShowNewModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);

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

  const onInsertGroup = async (name, groupStudents) => {
    const newGroup = {
      nombre: name,
      estadoSanitario: null,
      prioridad: null,
      alumnos: groupStudents
    };
    try {
      const res0 = await fetch(
        backUrl + `/centro/insert/group/${userData?.centro}`,
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
        alert(`Hubo un fallo al crear la clase`);
      } else {
        setShowNewModal(false);
      }
      groupStudents.forEach(async professor => {
        const res = await fetch(
          backUrl +
            `/centro/insert/class/${userData?.centro}/${encodeURIComponent(
              professor
            )}`,
          {
            method: "POST",
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
              "Content-Type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(newGroup)
          }
        );
        if (!res.ok) {
          alert(`Hubo un fallo al crear la clase`);
        } else {
          setShowNewModal(false);
        }
      });
    } catch (e) {
      console.log(e);
    } finally {
      retrieveGroups();
    }
  };

  const onEditGroup = async ({
    id,
    nombre,
    burbujaPresencial,
    fechaInicioConmutacion,
    tiempoConmutacion,
    profesores,
    gruposBurbuja
  }) => {
    const newGroup = {
      id,
      nombre,
      burbujaPresencial,
      fechaInicioConmutacion,
      tiempoConmutacion,
      profesores,
      gruposBurbuja
    };
    try {
      // console.log(newClass);
      const res = await fetch(
        backUrl + `/centro/update/class/${userData?.centro}/${id}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify(profesores)
        }
      );
      // console.log(await res.json());
      if (!res.ok) {
        alert(`Hubo un fallo al modificar la clase`);
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
      selected.forEach(async grupo => {
        let res = await fetch(
          backUrl + `/grupo/delete/${grupo.id}/${selectedClass.id}`,
          {
            method: "DELETE",
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
              "Content-Type": "application/json; charset=UTF-8"
            }
            // body: JSON.stringify(clase)
          }
        );
        console.log(await res);
        if (!res.ok) {
          console.log(await res.json());
          alert(`Hubo un fallo al borrar la clase`);
        }
      });
    } catch (e) {
      console.log(e);
    } finally {
      await retrieveGroups();
    }
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
              </div>
            ))}
          </div>
        </div>
        <div className="right-options">
          <h2>Seleccionados</h2>
          <div className="seleccionados">
            {(selected || []).map((indivClass, index) => (
              <div
                key={index}
                onClick={e => {
                  if (selected.some(e => e.nombre === indivClass.nombre)) {
                    var filtered = selected.filter(function(value, index, arr) {
                      return value.nombre !== indivClass.nombre;
                    });
                    setSelected(filtered);
                  }
                }}
                className={
                  "person-card green" +
                  (groups.some(e => e === indivClass) ? " selected" : "")
                }
              >
                {indivClass.nombre.includes("Grupo") ? (
                  <h5>{indivClass.nombre}</h5>
                ) : (
                  <h5>{indivClass.nombre}</h5>
                )}
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
            ) : null}
          </div>
        </div>
      </div>
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
