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

  useEffect(() => {
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
            x["bubbleGroups"] = responseData;
          }
          break;
        case "professors":
          response = await fetch(backUrl + "/manage/teachers");
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
    callMainSelector();
  }, [selectedType, selectedFilter]);

  const handleConfine = async () => {};

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
                    <option key={index} value={group.nombre}>
                      {group.name}
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
            (item?.nombre?.name === selectedFilter) |
            (selectedFilter === "*") ? (
              <div
                key={index}
                onClick={e => {
                  if (!selected.some(e => e.name === item.name)) {
                    setSelected(selected.concat([item]));
                  }
                }}
                className={
                  "person-card" +
                  (item.state === "Confinado" ? " red" : " green") +
                  (data[selectedType].some(e => e === item) ? "" : "selected")
                }
              >
                {item.name.includes("Grupo") ? (
                  <h5>{item.name} - 1ºB</h5>
                ) : (
                  <h5>{item.name}</h5>
                )}
                <h8>{item.state}</h8>
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
                if (selected.some(e => e.name === person.name)) {
                  var filtered = selected.filter(function(value, index, arr) {
                    return value.name !== person.name;
                  });
                  setSelected(filtered);
                }
              }}
              className={
                "person-card" +
                (person.state === "Confinado" ? " red" : " green") +
                (data[selectedType].some(e => e === person) ? " selected" : "")
              }
            >
              {person.name.includes("Grupo") ? (
                <h5>{person.name} - 1ºB</h5>
              ) : (
                <h5>{person.name}</h5>
              )}
              <h8>{person.state}</h8>
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
                  let x = selected;
                  let confined = x.forEach(e =>
                    e.state === "Confinado"
                      ? (e.state = "No Confinado")
                      : (e.state = "Confinado")
                  );
                  handleConfine();
                  setSelected([]);
                } else {
                  alert("Seleccione las personas a confinar");
                }
              }}
            >
              {selected.every(e => e.state.toLowerCase() === "confinado")
                ? "Desconfinar"
                : selected.every(e => e.state.toLowerCase() === "no confinado")
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
