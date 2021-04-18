import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";

// Temporary import during first sprint
//import { professor, students, bubbleGroups } from "../tests/prueba";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

// Constants
import { backUrl } from "../constants/constants";

function Confine({ history }) {
  const [data, setData] = useState({
    professors: [],
    students: [],
    bubbleGroups: []
  });
  const [selectedType, setSelectedType] = useState("bubbleGroups");
  const [selected, setSelected] = useState([]);
  const [selectedFilter, setSelectedFilter] = useState("*");

  const callMainSelector = async () => {
    // const bubbleGroupsRes = await fetch(backUrl + "/manage/bubblegroups");
    let x = data;
    let response;
    let responseData;
    switch (selectedType) {
      case "bubbleGroups":
        response = await fetch(backUrl + "/manage/bubblegroups");
        responseData = await response.json();
        x[selectedType] = responseData;
        break;
      case "students":
        response = await fetch(backUrl + "/manage/students");
        responseData = await response.json();
        x[selectedType] = responseData;
        if (selectedFilter === "*") {
          response = await fetch(backUrl + "/manage/bubblegroups");
          responseData = await response.json();
          x["bubbleGroups"] = responseData;
        } else {
          response = await fetch(backUrl + `/alumno/grupo/${selectedFilter}`);
          responseData = await response.json();
          x["students"] = responseData;
        }
        break;
      case "professors":
        response = await fetch(backUrl + "/manage/professors");
        responseData = await response.json();
        x[selectedType] = responseData;
        break;
      default:
        break;
    }
    try {
      setData(x);
    } catch (e) {
      // Nothing to do
    }
  };

  useEffect(() => {
    callMainSelector();
  }, [selectedType, selectedFilter]);

  useEffect(() => {
    callMainSelector();
  }, []);

  const handleConfine = async () => {
    selected.every(e => console.log(e.estadoSanitario.toLowerCase()));
    if (selected.every(e => e.estadoSanitario.toLowerCase() === "confinado")) {
      const resData = await fetch(backUrl + `/manage/unconfine/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    } else if (selected.every(e => e.estadoSanitario.toLowerCase() === "no confinado")) {
      const resData = await fetch(backUrl + `/manage/confine/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    } else {
      const resData = await fetch(backUrl + `/manage/switch/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    }
  };

  return (
    <div className="confine-container">
      <div className="left-menu">
        <div className="selector">
          <Form>
            <Form.Group controlId="group">
              <Form.Control
                as="select"
                defaultValue="bubbleGroups"
                onChange={e => {
                  setSelectedType(e.target.value);
                  setSelectedFilter("*");
                  setSelected([]);
                }}
              >
                <option key="1" value="bubbleGroups">
                  Grupos burbuja
                </option>
                <option key="2" value="students">
                  Alumnos
                </option>
                <option key="3" value="professors">
                  Profesores
                </option>
              </Form.Control>
            </Form.Group>
          </Form>
          {selectedType === "students" ? (
            <Form>
              <Form.Group controlId="group">
                <Form.Control
                  as="select"
                  defaultValue="bubbleGroups"
                  onChange={e => {
                    setSelectedFilter(e.target.value);
                  }}
                >
                  <option key="0" value="*">
                    Todos
                  </option>
                  {(data["bubbleGroups"] || []).map((group, index) => (
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
          {(data[selectedType] || []).map((item, index) =>
            (item?.nombre === selectedFilter) | (selectedFilter === "*") ? (
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
                  (data[selectedType].some(e => e === item) ? "" : "selected")
                }
              >
                {item?.nombre?.includes("Grupo") ? (
                  <h5>{item.nombre} - 1ºB</h5>
                ) : (
                  <h5>{item.nombre}</h5>
                )}
                <h8>{item.estadoSanitario === "no confinado" ? "No confinado" : "Confinado"}</h8>
              </div>
            ) : (
              <div />
            )
          )}
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
                "person-card" +
                (person.estadoSanitario === "confinado" ? " red" : " green") +
                (data[selectedType].some(e => e === person) ? " selected" : "")
              }
            >
              {person.nombre.includes("Grupo") ? (
                <h5>{person.nombre} - 1ºB</h5>
              ) : (
                <h5>{person.nombre}</h5>
              )}
              <h8>{person.estadoSanitario === "no confinado" ? "No confinado" : "Confinado"}</h8>
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
              {selected.every(e => e.estadoSanitario.toLowerCase() === "confinado")
                ? "Desconfinar"
                : selected.every(e => e.estadoSanitario.toLowerCase() === "no confinado")
                  ? "Confinar"
                  : "Cambiar estados"}
            </Button>
          ) : null}
        </Form>
      </div>
    </div>
  );
}

export default withRouter(Confine);
