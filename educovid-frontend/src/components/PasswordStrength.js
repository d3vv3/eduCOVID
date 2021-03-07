import React, { useEffect, useState } from "react";
import zxcvbn from "zxcvbn";

const createPasswordLabel = (result) => {
  switch (result.score) {
    case 0:
      return "Débil";
    case 1:
      return "Débil";
    case 2:
      return "Justa";
    case 3:
      return "Buena";
    case 4:
      return "Fuerte";
    default:
      return "Débil";
  }
};

const PasswordStrength = ({ password = "" }) => {
  const [strength, setStrength] = useState(null);

  useEffect(() => {

    // Recalculate and update the password strength on password change
    setStrength(zxcvbn(password));
  }, [password]);

  // Create the password color progress-bar feedback component
  return (
    <div className={`password-strength-indicator`}>
      {password && strength && (
        <progress
          className={`progress-bar strength-${createPasswordLabel(strength)}`}
          value={strength.score}
          max="4"
        />
      )}

      <label>
        {password && strength && (
          <div className="strength">{createPasswordLabel(strength)}</div>
        )}
      </label>
    </div>
  );
};

export default PasswordStrength;
