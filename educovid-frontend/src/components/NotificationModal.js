import React, { useState, useEffect } from "react";

// Constants
import { backUrl } from "../constants/constants";

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

function NotificationModal(props) {
  const [confinedText, setConfinedText] = useState("Has sido confinado");
  const [unconfinedText, setUnconfinedText] = useState("Has sido desconfinado");

  const { onHide, handleFinish, show, action } = props;

  return (
    <Modal
      onHide={onHide}
      show={show}
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
          {action === "confine" ? (
            <Form.Group controlId="formConfinedText">
              <Form.Label>Mensaje para alumnos a confinar</Form.Label>
              <Form.Control
                placeholder="Introduzca el mensaje"
                onChange={(e) => {
                  setConfinedText(e.target.value);
                  // handleChangeConfineMessage(e.target.value);
                }}
                value={confinedText}
              />
            </Form.Group>
          ) : null}
          {action === "unconfine" ? (
            <Form.Group controlId="formUnconfinedText">
              <Form.Label>Mensaje para alumnos a desconfinar</Form.Label>
              <Form.Control
                placeholder="Introduzca el mensaje"
                onChange={(e) => {
                  setUnconfinedText(e.target.value);
                  // handleChangeUnconfineMessage(e.target.value);
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
          handleFinish(confinedText, unconfinedText);
          setConfinedText("Has sido confinado");
          setUnconfinedText("Has sido desconfinado");
        }}>Finalizar</Button>
      </Modal.Footer>
    </Modal>
  );
}

export default NotificationModal;
