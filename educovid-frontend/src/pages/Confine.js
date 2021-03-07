import React, { useEffect, useState } from "react";
import { withRouter } from 'react-router-dom';

// Temporary import during first sprint
import { professor } from "../tests/prueba";

// Local imports
import PersonCard from "../components/PersonCard";

// Bootstrap imports
import Form from 'react-bootstrap/Form';

function Confine({ history }) {

    const [data, setData] = useState({
        professors: professor,
        students: [],
        bubbleGroups: []
    })
    const [selectedType, setSelectedType] = useState("")
    const [selected, setSelected] = useState([]);

    useEffect(() => {
        // TODO: load list from API
    }, [])

    const handleOnPersonCardClick = (item, event) => {
        history.push(`/people/${item.id}`, item);
        setSelected(selected.add(item));
        
    };

    return (
        <div className="confine-container">
            <div className="left-menu">
                <div className="selector">
                    <Form>
                        <Form.Group controlId="group">
                            <Form.Control
                                as="select"
                                defaultValue="Grupos burbuja"
                                onChange={e => { setSelectedType(e.target.value) }}>
                                <option key="1" value="bubbleGroups">Grupos burbuja</option>
                                <option key="2" value="students">Alumnos</option>
                                <option key="3" value="professors">Profesores</option>
                            </Form.Control>
                        </Form.Group>
                    </Form>
                </div>
                <div className="list-container">
                    {(data[selectedType] || []).map((person, index) => (
                        <PersonCard
                            index={index}
                            person={person}
                            isSelected={true}
                            onClick={(item, event) =>
                                handleOnPersonCardClick(item, event)
                            }
                        />
                    ))}
                </div>
            </div>
            <div className="right-options">
                    Cositas
            </div>
        </div>
    );
};

export default withRouter(Confine);