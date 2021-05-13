import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";


// Constants
import { backUrl } from "../constants/constants";

function MyVerticallyCenteredModal(props) {
  const [studentName, setStudentName] = useState("");
  const [numMat, setNumMat] = useState("");
  const [studentBubbleGroup, setStudentBubbleGroup] = useState("");
  const [studentClass, setStudentClass] = useState("");
  const [errors, setErrors] = useState({});
  const [feedbacks, setFeedbacks] = useState({
    numMat: ""
  });

  const updateFormState = event => {
    // On change, set the states with the updates
    // console.log(event);
    const { name, value } = event.target;
    switch (name) {
      case "studentName":
        setFeedbacks({
          numMat: ""
        });
        setErrors({});
        setStudentName(value);
        break;
      case "numMat":
        setFeedbacks({
          numMat: ""
        });
        setErrors({});
        setNumMat(value);
        break;
      case "studentBubbleGroup":
        setFeedbacks({
          numMat: ""
        });
        setErrors({});
        setStudentBubbleGroup(value);
        break;
      case "studentClass":
        setFeedbacks({
          numMat: ""
        });
        setErrors({});
        setStudentClass(value);
        break;
      default:
        return;
    }
  };

  return (
    <Modal
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Insertar Alumno
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formNumMat">
            <Form.Label>Número de matrícula</Form.Label>
            <Form.Control
              required
              type="text"
              placeholder="Introduzca el número de matrícula"
              name="numMat"
              onChange={updateFormState}
              value={numMat}
              isInvalid={!!errors.numMat}
            />
            <Form.Text className="text-muted">
              Debe ser único en el centro
            </Form.Text>
            <Form.Control.Feedback type="invalid">
              {feedbacks.numMat}
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">
              Perfecto
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="formStudentName">
            <Form.Label>Nombre del alumno</Form.Label>
            <Form.Control
              required
              autoFocus
              type="text"
              placeholder="Introduzca el nombre del alumno"
              name="studentName"
              onChange={updateFormState}
              value={studentName}
              isInvalid={!!errors.studentName}
            />
            <Form.Control.Feedback type="invalid">
              Nombre de alumno inválido
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">
              Perfecto
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="studentClass">
            <Form.Label>Nombre de la clase del alumno</Form.Label>
            <Form.Control
              required
              autoFocus
              type="text"
              placeholder="Introduzca el nombre de la clase"
              name="studentClass"
              onChange={updateFormState}
              value={studentClass}
              isInvalid={!!errors.studentClass}
            />
            <Form.Control.Feedback type="invalid">
              Nombre de grupo inválido
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">
              Perfecto
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="studentBubbleGroup">
            <Form.Label>Nombre del grupo burbuja del alumno</Form.Label>
            <Form.Control
              required
              autoFocus
              type="text"
              placeholder="Introduzca el nombre del grupo burbuja"
              name="studentBubbleGroup"
              onChange={updateFormState}
              value={studentBubbleGroup}
              isInvalid={!!errors.studentBubbleGroup}
            />
            <Form.Control.Feedback type="invalid">
              Nombre de grupo inválido
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">
              Perfecto
            </Form.Control.Feedback>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onHide}>Cancelar</Button>
        <Button onClick={() => props.onInsert(studentName, numMat, studentClass, studentBubbleGroup, errors, feedbacks, setErrors, setFeedbacks)} disabled={numMat === "" || studentName === "" || studentBubbleGroup === "" || studentClass === ""}>Insertar</Button>
      </Modal.Footer>
    </Modal>
  );
}

function NotificationModal(props) {
  const [confinedText, setConfinedText] = useState("Has sido confinado");
  const [unconfinedText, setUnconfinedText] = useState("Has sido desconfinado");
  const [stateStudents, setStateStudents] = useState({});

  const { onHide, onFinish, onChangeConfineMessage, onChangeUnconfineMessage } = props;

  return (
    <Modal
      {...props}
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
          {localStorage.getItem("no confinados") === "true" ? (
          <Form.Group controlId="formConfinedText">
            <Form.Label>Mensaje para alumnos a confinar</Form.Label>
            <Form.Control
              placeholder="Introduzca el mensaje"
              onChange={(e) => {
                setConfinedText(e.target.value);
                onChangeConfineMessage(e.target.value);
              }}
              value={confinedText}
            />
          </Form.Group>
          ) : null}
          {localStorage.getItem("confinados") === "true" ? (
          <Form.Group controlId="formUnconfinedText">
            <Form.Label>Mensaje para alumnos a desconfinar</Form.Label>
            <Form.Control
              placeholder="Introduzca el mensaje"
              onChange={(e) => {
                setUnconfinedText(e.target.value);
                onChangeUnconfineMessage(e.target.value);
              }}
              value={unconfinedText}
            />
          </Form.Group>
          ) : null}
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={onHide}>Cancelar</Button>
        <Button onClick={() => {
          setConfinedText("Has sido confinado");
          setUnconfinedText("Has sido desconfinado");
          onFinish();
        }}>Finalizar</Button>
      </Modal.Footer>
    </Modal>
  );
}

function ManageStudent(props) {
  const { history, onLogOut, userData, confineMessage, unconfineMessage, onChangeConfineMessage, onChangeUnconfineMessage } = props;

  const [insertModalShow, setInsertModalShow] = React.useState(false);
  const [notificationModalShow, setNotificationModalShow] = React.useState(false);
  const [students, setStudents] = useState([]);
  const [bubbleGroups, setBubbleGroups] = useState([]);
  const [selectedType, setSelectedType] = useState("students");
  const [selected, setSelected] = useState([]);
  const [selectedFilter, setSelectedFilter] = useState("*");

  const initComponent = async () => {
    let response;
    let responseData;
    response = await fetch(backUrl + "/manage/bubblegroups", {
      method: "GET",
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
      }
    });
    responseData = await response.json();
    setBubbleGroups(responseData);
    response = await fetch(backUrl + "/manage/students", {
      method: "GET",
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
      }
    });
    responseData = await response.json();
    setStudents(responseData);
    console.log("UPDATED A");
  };

  const callMainSelector = async () => {
    // const bubbleGroupsRes = await fetch(backUrl + "/manage/bubblegroups");
    let response;
    let responseData;
    if (selectedFilter === "*") {
      response = await fetch(backUrl + "/manage/students", {
        method: "GET",
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
        }
      });
      responseData = await response.json();
      setStudents(responseData);
    } else {
      response = await fetch(backUrl + `/alumno/grupo/${selectedFilter}`, {
        method: "GET",
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
        }
      });
      responseData = await response.json();
      console.log(responseData);
      console.log();
      setStudents(responseData);
    }
  };

  useEffect(() => {
    callMainSelector();
  }, [selectedType, selectedFilter]);

  useEffect(() => {
    initComponent();
  }, []);

  const getListOnSelectedType = () => {
    return students;
  };

  const handleConfine = async () => {
    console.log("MENSAJE CONFINAR: " + confineMessage);
    console.log("MENSAJE DESCONFINAR: " + unconfineMessage);
    console.log(JSON.stringify(selected));
    console.log(JSON.stringify({"confineMessage" : confineMessage, "unconfineMessage" : unconfineMessage}));
    selected.every(e => console.log(e.estadoSanitario.toLowerCase()));
    if (selected.every(e => e.estadoSanitario.toLowerCase() === "confinado")) {
      await fetch(backUrl + `/manage/unconfine/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem('token') || ""}` },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    } else if (
      selected.every(e => e.estadoSanitario.toLowerCase() === "no confinado")
    ) {
      await fetch(backUrl + `/manage/confine/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem('token') || ""}` },
        body: JSON.stringify(selected)
      });
      await callMainSelector();
    } else {
      await fetch(backUrl + `/manage/switch/${selectedType}`, {
        method: "POST",
        headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem('token') || ""}` },
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
          `/notification/subscription/${selectedType}/` + value.id, {
          method: "POST",
          headers: { "Content-Type": "application/json", 'Authorization': `Bearer ${localStorage.getItem('token') || ""}` },
          body: JSON.stringify({"confineMessage" : confineMessage, "unconfineMessage" : unconfineMessage})
        });
      } catch (e) {
        console.log("Error pushing notification");
      }
    });
    // selected.forEach(async (value) => {
    //   try {
    //     console.log(value);
    //     const notificationRes = await fetch(
    //       backUrl +
    //       `/notification/subscription/${selectedType}/` + value.id, {
    //       method: "GET",
    //       headers: {
    //         'Authorization': `Bearer ${localStorage.getItem('token') || ""}`
    //       }
    //     });
    //   } catch (e) {
    //     console.log("Error pushing notification");
    //   }
    // });
  };

  const handleInsertStudent = async () => {
    setInsertModalShow(true);
  };

  const handleNotification = async () => {
    setNotificationModalShow(true);
    localStorage.setItem("confinados", selected.some(e => e.estadoSanitario.toLowerCase() === "confinado"));
    localStorage.setItem("no confinados", selected.some(e => e.estadoSanitario.toLowerCase() === "no confinado"));
  }

  return (
    <div>
      <ActionBar
        onLogOut={() => {
          onLogOut();
        }}
      />
      <div className="confine-container">
        <div className="left-menu">
          <div className="selector">
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
                  "person-card" + " green" +
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
                  "person-card" + " green" +
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
                    handleNotification();
                    //handleConfine();
                    //setSelected([]);
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
            <Button
              variant="primary"
              className="nord-button"
              onClick={e => {
                handleInsertStudent();
              }}
            >
              Insertar alumno
              </Button>
          </Form>
        </div>
      </div>

      <NotificationModal
        show={notificationModalShow}
        onHide={() => setNotificationModalShow(false)}
        onFinish={() => {
          handleConfine();
          setSelected([]);
          setNotificationModalShow(false);
          localStorage.removeItem("confinados");
          localStorage.removeItem("no confinados");
        }}
        onChangeConfineMessage={(message) => {
          onChangeConfineMessage(message);
        }}
        onChangeUnconfineMessage={(message) => {
          onChangeUnconfineMessage(message);
        }}
      />

      <MyVerticallyCenteredModal show={insertModalShow} onHide={() => setInsertModalShow(false)} onInsert={async (name, numMat, studentClass, studentBubbleGroup, errors, feedbacks, setErrors, setFeedbacks) => {
        const newStudent = {
          nombre: name,
          hash: "",
          salt: "",
          subscriptionEndpoint: null,
          p256dh: null,
          auth: null,
          numeroMatricula: numMat,
          estadoSanitario: "no confinado",
          fechaConfinamiento: null
        };
        try {
          // const res = await fetch(backUrl + "/alumno", {
          //   method: "POST",
          //   headers: {
          //     'Authorization': `Bearer ${localStorage.getItem('token') || ""}`,
          //     'Content-Type': 'application/json; charset=UTF-8'
          //   },
          //   body: JSON.stringify(newStudent)
          // });
          const res = await fetch(backUrl + `/centro/insert/alumno/${userData.centro}/${studentClass}/${studentBubbleGroup}`, {
            method: "POST",
            headers: {
              'Authorization': `Bearer ${localStorage.getItem('token') || ""}`,
              'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(newStudent)
          });
          if (!res.ok) {
            errors.numMat = true;
            feedbacks.numMat = "No se puede insertar el alumno en el grupo burbuja de la clase especificada.";
            setErrors(errors);
            setFeedbacks(feedbacks);
          } else {
            setInsertModalShow(false);
          }
        } catch (e) {
          // Nothing to do
        }
      }} />
    </div>
  );
}

export default withRouter(ManageStudent);
