import React, { useEffect, useState } from "react";

import { withRouter } from "react-router-dom";

import ActionBar from "../components/ActionBar";

import {clases, gruposBurbuja} from "../tests/prueba"

// Bootstrap imports
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

// Constants
import { backUrl } from "../constants/constants";

function Center(props) {
  const [classes, setClasses] = useState([]);
  const [bubbleGroups, setBubbleGroups] = useState([]);
  const [selectedClass, setSelectedClass] = useState();
  const [selectedGroup, setSelectedGroup] = useState();
  const { userData, onLogOut } = props;
  
  const initComponent = async () => {
    //let response;
    //let responseData;
    //response = await fetch(backUrl + );
    //responseData = await response.json();
    setBubbleGroups(gruposBurbuja);
    //response = await fetch(backUrl + );
    //responseData = await response.json();
    setClasses(clases);
    console.log("UPDATED A");
  }; 
  



  return (
    
    <div>
      
      <ActionBar
        onLogOut={() => {
          onLogOut();
        }}
      />
      <div className="center-container">
        <div className="left-menu">
          <div className="list-container">
            {console.log("clases" + classes.clases.grupos.nombre)}
            {(clases || []).map((item, index) => (
              <div
                key={index}
                onClick={e => {
                  if (selectedClass != null) {
                    setSelectedClass(selectedClass.concat([item]));
                  }
                }}
                className={
                  "class-card" +
                  (classes.some(e => e === item)
                    ? ""
                    : " selected")
                }
              >
                {item?.nombre?.includes("Grupo") ? (
                  <h5></h5>
                ) : (
                  <h5></h5>
                )}
                
              </div>
            ))}
          </div>
        </div>
        <div className="right-options">
          <h2>Seleccionados</h2>
          <div className="grupos">
            {/* {(selectedClass.bubbleGroups || []).map((person, index) => (
              <div
                key={index}
                onClick={e => {
                  if (selected.some(e => e.nombre === person.nombre)) {
                    var filtered = selectedClass.filter(function(value, index, arr) {
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
                  <h5></h5>
                ) : (
                  <h5></h5>
                )}

              </div>
            ))} */}
          </div>
        </div>
        <div className="buttons-container">
          <Form>
            {selectedClass != null ? (
              <Button
                variant="primary"
                className="nord-button"
                onClick={e => {
                  if (selectedGroup != null) {
                    
                  } else {
                    
                  }
                }}
              >
                
              </Button>
            ) : null}
          </Form>
        </div>
      </div>
    </div>
  );
}

export default withRouter(Center);
