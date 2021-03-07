import React, { useEffect, useState } from "react";


// Component to provide password strength visual feedback to the user
const PersonCard = ({ index, person, isSelected, onClick }) => {
  return (
    <div className={ "person-card" + (person.state === "Confinado" ? " red" : " green") }>
      <h5>
          {person.name}
      </h5>
    </div>
  );
};

export default PersonCard;
