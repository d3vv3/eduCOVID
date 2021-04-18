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
  const [professors, setProfessors] = useState([]);
  const [students, setStudents] = useState([]);
  const [bubbleGroups, setBubbleGroups] = useState([]);
  const [selectedType, setSelectedType] = useState("bubbleGroups");
  const [selected, setSelected] = useState([]);
  const [selectedFilter, setSelectedFilter] = useState("*");

  const initComponent = async () => {
    let response;
    let responseData;
    response = await fetch(backUrl + "/manage/bubblegroups");
    responseData = await response.json();
    setBubbleGroups(responseData);
    response = await fetch(backUrl + "/manage/students");
    responseData = await response.json();
    setStudents(responseData);
    response = await fetch(backUrl + "/manage/professors");
    responseData = await response.json();
    setProfessors(responseData);
    console.log("UPDATED A");
  };

  const callMainSelector = async () => {
    // const bubbleGroupsRes = await fetch(backUrl + "/manage/bubblegroups");
    let response;
    let responseData;
    switch (selectedType) {
      case "bubbleGroups":
        response = await fetch(backUrl + "/manage/bubblegroups");
        responseData = await response.json();
        setBubbleGroups(responseData);
        break;
      case "students":
        if (selectedFilter === "*") {
          response = await fetch(backUrl + "/manage/students");
          responseData = await response.json();
          setStudents(responseData);
        } else {
          response = await fetch(backUrl + `/alumno/grupo/${selectedFilter}`);
          responseData = await response.json();
          console.log(responseData);
          console.log();
          setStudents(responseData);
        }
        break;
      case "professors":
        response = await fetch(backUrl + "/manage/professors");
        responseData = await response.json();
        setProfessors(responseData);
        break;
      default:
        break;
    }
  };

  useEffect(() => {
    callMainSelector();
  }, [selectedType, selectedFilter]);

  useEffect(() => {
    initComponent();
  }, []);

  const getListOnSelectedType = () => {
    switch (selectedType) {
      case "bubbleGroups":
        return bubbleGroups;
      case "professors":
        return professors;
      case "students":
        return students;
      default:
        return bubbleGroups;
    }
  };

  const handleConfine = async () => {
    selected.every(e => console.log(e.estadoSanitario.toLowerCase()));
    if (selected.every(e => e.estadoSanitario.toLowerCase() === "confinado")) {
      await fetch(backUrl + `/manage/unconfine/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    } else if (
      selected.every(e => e.estadoSanitario.toLowerCase() === "no confinado")
    ) {
      await fetch(backUrl + `/manage/confine/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    } else {
      await fetch(backUrl + `/manage/switch/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    }
    // notify everyone
    selected.forEach(async (value) => {
      try {
        console.log(value);
        const notificationRes = await fetch(
          backUrl +
          `/notification/subscription/${selectedType}/` + value.id
        );
      } catch (e) {
        console.log("Error pushing notification");
      }
    });
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
                  callMainSelector();
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
                  defaultValue="*"
                  onChange={e => {
                    setSelected([]);
                    setSelectedFilter(e.target.value);
                    callMainSelector();
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
          ) : (
            <div />
          )}
        </div>

        <div className="list-container">
          {(getListOnSelectedType() || []).map((item, index) => (
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
                (getListOnSelectedType().some(e => e === item)
                  ? ""
                  : " selected")
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
                  var filtered = selected.filter(function (value, index, arr) {
                    return value.nombre !== person.nombre;
                  });
                  setSelected(filtered);
                }
              }}
              className={
                "person-card" +
                (person.estadoSanitario === "confinado" ? " red" : " green") +
                (getListOnSelectedType().some(e => e === person)
                  ? " selected"
                  : "")
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
              {selected.every(
                e => e.estadoSanitario.toLowerCase() === "confinado"
              )
                ? "Desconfinar"
                : selected.every(
                  e => e.estadoSanitario.toLowerCase() === "no confinado"
                )
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
