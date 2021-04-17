import React, { useEffect } from "react";

// Local imports
import {
  isPushNotificationSupported,
  showLocalNotification,
  askUserPermission,
  registerServiceWorker,
  createNotificationSubscription
} from "../notificationsServiceWorker";

import { backUrl } from "../constants/constants";

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
        try {
          let utf8decoder = new TextDecoder();
          let pushSubscription = await createNotificationSubscription();
          const details = {
            subscriptionEndpoint: pushSubscription.endpoint,
            p256dh: pushSubscription.getKey("p256dh"),
            auth: pushSubscription.getKey("auth")
          };
          let formBody = [];
          for (var property in details) {
            const encodedKey = encodeURIComponent(property);
            const encodedValue = encodeURIComponent(details[property]);
            formBody.push(encodedKey + "=" + encodedValue);
          }
          formBody = formBody.join("&");
          const subscriptionRes = await fetch(backUrl + `/notification/subscription/${props.role}/` + props.userId, { // Testing alumno first
            method: "POST",
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: JSON.stringify(pushSubscription),
          });
          if (!subscriptionRes.ok) {
            console.log("Subscription error");
            return null;
          }
        } catch (e) {
          console.log("Error subscribing. Probably already subscribed");
        }
        try {
          const notificationRes = await fetch(backUrl + `/notification/subscription/${props.role}/` + props.userId);
        } catch (e) {
          console.log("Error pushing notification");
        }
        
      }
    }
  }, []);

  return <div className="notification"></div>;
}

export default Notifications;
