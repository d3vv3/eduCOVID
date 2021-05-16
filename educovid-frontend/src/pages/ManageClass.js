import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Local components
import ClassCenteredModal from "../components/NewClassFormModal";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Button from "react-bootstrap/Button";

function ManageClass(props) {
  const { userData, setPage, setSelectedClass } = props;

  const [classes, setClasses] = useState([]);
  const [selected, setSelected] = useState([]);
  const [showNewModal, setShowNewModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);

  useEffect(() => {
    retrieveClasses();
  }, []);

  const retrieveClasses = async () => {
    console.log("Refrescando clases");
    const token = localStorage.getItem("token") || "";
    let response;
    let responseData;
    response = await fetch(backUrl + `/centro/${userData?.centro}/classes`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });
    responseData = await response.json();
    setClasses(responseData);
  };

  const onInsertClass = async (name, classProfessors) => {
    const newClass = {
      nombre: name,
      burbujaPresencial: null,
      fechaInicioConmutacion: null,
      tiempoConmutacion: null,
      profesores: null,
      gruposBurbuja: null
    };
    try {
      const res0 = await fetch(
        backUrl + `/centro/insert/class/${userData?.centro}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify(newClass)
        }
      );
      // console.log(res0);
      if (!res0.ok) {
        alert(`Hubo un fallo al crear la clase`);
      } else {
        setShowNewModal(false);
      }
      classProfessors.forEach(async professor => {
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
            body: JSON.stringify(newClass)
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
      retrieveClasses();
    }
  };

  const onEditClass = async ({
    id,
    nombre,
    burbujaPresencial,
    fechaInicioConmutacion,
    tiempoConmutacion,
    profesores,
    gruposBurbuja
  }) => {
    const newClass = {
      id,
      nombre,
      burbujaPresencial,
      fechaInicioConmutacion,
      tiempoConmutacion,
      profesores,
      gruposBurbuja
    };
    try {
      console.log(newClass);
      const res = await fetch(
        backUrl + `/centro/update/class/${userData?.centro}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify(newClass)
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
      retrieveClasses();
    }
  };

  const handleDelete = async () => {
    try {
      selected.forEach(async clase => {
        let res = await fetch(backUrl + `/clase/${clase.id}`, {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
            "Content-Type": "application/json; charset=UTF-8"
          }
          // body: JSON.stringify(clase)
        });
        console.log(await res);
        if (!res.ok) {
          console.log(await res.json());
          alert(`Hubo un fallo al borrar la clase`);
        }
      });
    } catch (e) {
      console.log(e);
    } finally {
      setTimeout(() => retrieveClasses(), 100);
    }
  };

  return (
    <div>
      <div className="manage-classes-container">
        <div className="left-menu">
          <div className="list-container">
            {(classes ?? []).map((item, index) => (
              <div
                key={index}
                onClick={e => {
                  if (!selected.some(e => e.nombre === item.nombre)) {
                    setSelected(selected.concat([item]));
                  }
                }}
                className={
                  "person-card green" +
                  (classes.some(e => e === item) ? "" : " selected")
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
                  (classes.some(e => e === indivClass) ? " selected" : "")
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
                <Button
                  variant="primary"
                  className="nord-button"
                  onClick={e => {
                    if (selected.length === 1) {
                      setPage("groups");
                      setSelectedClass(selected[0]);
                    } else {
                      alert("Seleccione una sola clase para esta acción");
                    }
                  }}
                >
                  Ver grupos
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
              Añadir clase
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
        <ClassCenteredModal
          centro={userData.centro}
          existingClass={showEditModal ? selected[0] : null}
          show={showNewModal || showEditModal}
          onHide={() => {
            setShowNewModal(false);
            setShowEditModal(false);
            setSelected([]);
          }}
          handleInsert={onInsertClass}
          handleEdit={onEditClass}
        />
      ) : null}
    </div>
  );
}

export default withRouter(ManageClass);
