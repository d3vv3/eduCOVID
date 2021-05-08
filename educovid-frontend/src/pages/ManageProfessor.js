import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Local components
import ActionBar from "../components/ActionBar";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

function ManageProfessor(props) {
  const { history, onLogOut } = props;

  const [professors, setProfessors] = useState([]);
  const [selected, setSelected] = useState([]);

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

  const handleNewProfessor = async () => {};

  const handleDelete = async () => {};

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
        const notificationRes = await fetch(
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
                  "person-card" +
                  (item.estadoSanitario === "confinado" ? " red" : " green") +
                  (professors.some(e => e === item) ? "" : " selected")
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
                    var filtered = selected.filter(function(value, index, arr) {
                      return value.nombre !== person.nombre;
                    });
                    setSelected(filtered);
                  }
                }}
                className={
                  "person-card" +
                  (person.estadoSanitario === "confinado" ? " red" : " green") +
                  (professors.some(e => e === person) ? " selected" : "")
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
              <div className="confine-option-buttons">
                <Button
                  variant="primary"
                  className="nord-button"
                  onClick={e => {
                    if (selected != null) {
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
            <div>
              <Button
                variant="primary"
                className="nord-button"
                onClick={e => {
                  if (selected != null) {
                    handleNewProfessor();
                    setSelected([]);
                  } else {
                    alert("Seleccione personas para ejecutar esta acción");
                  }
                }}
              >
                Añadir
              </Button>
              <Button
                variant="primary"
                className="nord-button red"
                onClick={e => {
                  if (selected != null) {
                    handleDelete();
                    setSelected([]);
                  } else {
                    alert("Seleccione personas para ejecutar esta acción");
                  }
                }}
              >
                Borrar
              </Button>
            </div>
          </Form>
        </div>
      </div>
    </div>
  );
}

export default withRouter(ManageProfessor);
