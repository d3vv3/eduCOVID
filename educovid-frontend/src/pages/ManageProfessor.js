import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";

// Local components
import ActionBar from "../components/ActionBar";
// import ProfessorCenteredModal from "../components/NewProfessorFormModal";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function ProfessorCenteredModal(props) {
  const [professorName, setProfessorName] = useState("");
  const [id, setId] = useState("");
  const [professorClasses, setProfessorClasses] = useState("");
  const [errors, setErrors] = useState({});
  const [feedbacks, setFeedbacks] = useState({
    id: "",
    classes: ""
  });

  const updateFormState = event => {
    // On change, set the states with the updates
    // console.log(event);
    const { name, value } = event.target;
    switch (name) {
      case "professorName":
        setFeedbacks({
          id: ""
        });
        setErrors({});
        setProfessorName(value);
        break;
      case "id":
        setFeedbacks({
          id: ""
        });
        setErrors({});
        setId(value);
        break;
      case "professorClasses":
        setFeedbacks({
          id: ""
        });
        setErrors({});
        setProfessorClasses(value);
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
          Insertar Profesor
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formNumMat">
            <Form.Label>NIF/NIE</Form.Label>
            <Form.Control
              required
              type="text"
              placeholder="Introduzca el NIF/NIE"
              name="id"
              onChange={updateFormState}
              value={id}
              isInvalid={!!errors.id}
            />
            <Form.Text className="text-muted">
              Debe ser único en el centro
            </Form.Text>
            <Form.Control.Feedback type="invalid">
              {feedbacks.id}
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="formStudentName">
            <Form.Label>Nombre del profesor</Form.Label>
            <Form.Control
              required
              autoFocus
              type="text"
              placeholder="Introduzca el nombre del profesor"
              name="professorName"
              onChange={updateFormState}
              value={professorName}
              isInvalid={!!errors.professorName}
            />
            <Form.Control.Feedback type="invalid">
              Nombre de profesor inválido
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
          <Form.Group controlId="professorClasses">
            <Form.Label>Clases del profesor</Form.Label>
            <Form.Control
              required
              autoFocus
              type="text"
              placeholder="Introduzca las clases separadas por coma: 11,12,13"
              name="professorClasses"
              onChange={updateFormState}
              value={professorClasses}
              isInvalid={!!errors.professorClasses}
            />
            <Form.Control.Feedback type="invalid">
              Clases inválidas
            </Form.Control.Feedback>
            <Form.Control.Feedback type="valid">Perfecto</Form.Control.Feedback>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={props.onHide}>Cancelar</Button>
        <Button onClick={() => props.onInsert(professorName, id, professorClasses)}
          disabled={
            id === "" || professorName === "" || professorClasses === ""
          }
        >
          Insertar
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

function ManageProfessor(props) {
  const { history, onLogOut, userData } = props;

  const [professors, setProfessors] = useState([]);
  const [selected, setSelected] = useState([]);
  const [showModal, setShowModal] = useState(false);

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

  const handleNewProfessor = async () => {
    setShowModal(true);
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
                    var filtered = selected.filter(function (value, index, arr) {
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
                  } else {
                    alert("Seleccione personas para ejecutar esta acción");
                  }
                }}
              >
                Añadir profesor
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
                Borrar profesor
              </Button>
            </div>
          </Form>
        </div>
      </div>
      <ProfessorCenteredModal show={showModal} onHide={() => setShowModal(false)} onInsert={async (name, id, professorClasses) => {
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
          // Create professor on database
          // const res = await fetch(
          //   // TODO backend method
          //   backUrl + `/centro/insert/professor/${userData.centro}`,
          //   {
          //     method: "POST",
          //     headers: {
          //       Authorization: `Bearer ${localStorage.getItem("token") ||
          //         ""}`,
          //       "Content-Type": "application/json; charset=UTF-8"
          //     },
          //     body: JSON.stringify(newProfessor)
          //   }
          // );
          // Create profesor and add it to each class
          let classes = professorClasses.trim().split(",");
          // let promises = classes.map(clase => {
          //   return fetch(
          //     // TODO backend method and change the path
          //     backUrl + `/centro/insert/professor/${userData.centro}/${clase}`,
          //     {
          //       method: "POST",
          //       headers: {
          //         Authorization: `Bearer ${localStorage.getItem("token") ||
          //           ""}`,
          //         "Content-Type": "application/json; charset=UTF-8"
          //       },
          //       body: JSON.stringify(newProfessor)
          //     }
          //   );
          // });
          console.log(classes);
          classes.forEach(async (clase) => {
            const res = await fetch(
              // TODO backend method and change the path
              backUrl + `/centro/insert/professor/${userData.centro}/${clase}`,
              {
                method: "POST",
                headers: {
                  Authorization: `Bearer ${localStorage.getItem("token") ||
                    ""}`,
                  "Content-Type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify(newProfessor)
              }
            );
            if (!res.ok) {
              alert(`Hubo un fallo al crear el profesor`);
            } else {
              setShowModal(false);
            }
          });
          // let responses = Promise.all(promises);
          // responses.some(response =>
          //   !response.ok
          //     ? alert(`Hubo un fallo al crear el profesor`)
          //     : setShowModal(false)
          // );
        } catch (e) {
          console.log(e);
        }
      }}
      />
    </div>
  );
}

export default withRouter(ManageProfessor);
