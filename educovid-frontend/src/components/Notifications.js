import React, { useEffect } from "react";

// Local imports
import {
  isPushNotificationSupported,
  showLocalNotification,
  askUserPermission,
  registerServiceWorker,
  createNotificationSubscription
} from "../notificationsServiceWorker";

function Notifications(props) {
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
        const details = {
          subscriptionEndpoint: pushSubscription.endpoint,
        };
        let formBody = [];
        for (var property in details) {
          const encodedKey = encodeURIComponent(property);
          const encodedValue = encodeURIComponent(details[property]);
          formBody.push(encodedKey + "=" + encodedValue);
        }
        formBody = formBody.join("&");
      }
      const subscriptionRes = await fetch(backUrl + "/notification/subscription/" + props.userId, {
        method: "POST",
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: formBody
      });
      if (!subscriptionRes.ok) {
        console.log("Subscription error");
        return null;
      }
    }
  }, []);

  return <div className="notification"></div>;
}

export default Notifications;
