import React, { useEffect } from "react";

// Local imports
import {
  isPushNotificationSupported,
  showLocalNotification,
  askUserPermission,
  registerServiceWorker,
  createNotificationSubscription
} from "../notificationsServiceWorker";

function Notifications() {
  useEffect(async () => {
    if (isPushNotificationSupported()) {
      let swRegistration = await registerServiceWorker();
      let response = await askUserPermission(); // granted, default or denied
      console.log("NOTIFICATIONS:", response);
      if (response === "granted") {
        // showLocalNotification(
        //   "eduCOVID",
        //   "Has sido confinado. Entra en la aplicación para más detalles.",
        //   swRegistration
        // );
        let pushSubscription = await createNotificationSubscription();
      }
    }
  }, []);

  return <div className="notification"></div>;
}

export default Notifications;
