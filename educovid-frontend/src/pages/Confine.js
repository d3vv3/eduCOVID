import React, { useEffect, useState } from "react";
import { withRouter } from 'react-router-dom';

// Temporary import during first sprint
import { professor, students, bubbleGroups } from "../tests/prueba";

// Bootstrap imports
import Form from 'react-bootstrap/Form';

function Confine({ history }) {

    const [data, setData] = useState({
        professors: professor,
        students: students,
        bubbleGroups: bubbleGroups
    })
    const [selectedType, setSelectedType] = useState("")
    const [selected, setSelected] = useState([]);

    useEffect(() => {
        // TODO: load list from API
    }, [])


    return (
        <div className="confine-container">
            <div className="left-menu">
                <div className="selector">
                    <Form>
                        <Form.Group controlId="group">
                            <Form.Control
                                as="select"
                                defaultValue="Grupos burbuja"
                                onChange={e => {
                                        setSelectedType(e.target.value);
                                        setSelected([])
                                    }}>
                                <option key="1" value="bubbleGroups">Grupos burbuja</option>
                                <option key="2" value="students">Alumnos</option>
                                <option key="3" value="professors">Profesores</option>
                            </Form.Control>
                        </Form.Group>
                    </Form>
                </div>
                <div className="list-container">
                    {(data[selectedType] || []).map((person, index) => (
                        <div
                            person={person}
                            onClick={(e) => {
                                console.log(person);
                                //este some falla en el caso que te paso por el video de whatsapp
                                if (!selected.some(e => e.name === person.name) && person.state === "No Confinado") {
                                    setSelected(selected.concat([person]));
                                }
                            }
                            }
                            className={ 
                                "person-card" +
                                (person.state === "Confinado" ? " red" : " green") +
                                (data[selectedType].some(e => e === person) ? "" : "selected")
                            }
                        >
                            <h5>
                                {person.name}
                            </h5>
                        </div>
                    ))}
                </div>
            </div>
            <div className="right-options">
                {(selected || []).map((person, index) => (
                    <div
                        person={person}
                        onClick={(e) => {
                            console.log(index);

                            if (selected.some(e => e.name === person.name)) {
                                setSelected(selected.splice(index, 1));
                            }
                        }
                        }
                        className={ 
                            "person-card" +
                            (person.state === "Confinado" ? " red" : " green") +
                            (data[selectedType].some(e => e === person) ? " selected" : "")
                        }
                    >
                    <h5>
                        {person.name}
                    </h5>
                    </div>
                ))}
            </div>
            <div 
                onClick={(e) => {
                    if(selected != null){
                        setSelected(selected.forEach(e => e.state = "Confinado"));
                        console.log(selected);
                    }else{
                        alert("Seleccione las personas a confinar")
                    } 
                }
                }
                className="confine-button"
            >
                <h5>
                    Confinar
                </h5>

            </div>
        </div>
    );
};

export default withRouter(Confine);