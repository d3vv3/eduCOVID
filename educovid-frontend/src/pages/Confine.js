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
    const callUsers = async () => {
      // Get all centers (not secure, everyone can read all centers)

      let method = "";
      switch (selectedType) {
        case 'bubbleGroups':
          method = "/manage/bubblegroups";
          break;
        case 'students':
          method = "/manage/students";
          break;
        case 'professors':
          method = "/manage/teachers";
          break;
        default:
          method = "/manage/bubblegroups";
      }
      try {
        const usersRes = await fetch(backUrl + 
          method
          );
        let usersData = await usersRes.json();
        console.log(usersData);
        setData(data[selectedType] = usersData)
      } catch (e) {
        // Nothing to do
      }
    };
    callUsers();
  }, [selectedType]);

  const handleConfine = async () => {

  }

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
                  {(data["bubbleGroups"] || []).map((value, index) => (
                    <option key={index} value={value.name}>
                      {value.name}
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
          {(data[selectedType] || []).map((person, index) =>
            (person?.group?.name === selectedFilter) |
            (selectedFilter === "*") ? (
              <div
                key={index}
                onClick={e => {
                  if (!selected.some(e => e.name === person.name)) {
                    setSelected(selected.concat([person]));
                  }
                }}
                className={
                  "person-card" +
                  (person.state === "Confinado" ? " red" : " green") +
                  (data[selectedType].some(e => e === person) ? "" : "selected")
                }
              >
                {person.name.includes("Grupo") ? (
                  <h5>{person.name} - 1ºB</h5>
                ) : (
                  <h5>{person.name}</h5>
                )}
                <h8>{person.state}</h8>
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
