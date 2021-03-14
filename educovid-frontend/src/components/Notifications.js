import React, { useEffect } from "react";

function App() {

  const isPushNotificationSupported = () => {
    return "serviceWorker" in navigator && "PushManager" in window;
  }

  const registerServiceWorker = () => {
    return navigator.serviceWorker.register("../serviceWorker.js");
  }

  const askUserPermission = async () => {
    return await Notification.requestPermission();
}

  useEffect(async () => {
      if (isPushNotificationSupported()) {
          registerServiceWorker()
          let response = await askUserPermission()
          console.log("NOTIFICATIONS:", response)
      }
  }, [])

  return (
    <div className="notification">
    </div>
  );
}

export default App;
