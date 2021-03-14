const isPushNotificationSupported = () => {
  return "serviceWorker" in navigator && "PushManager" in window;
};

const registerServiceWorker = async () => {
  return await navigator.serviceWorker.register("/service-worker.js");
};

const askUserPermission = async () => {
  return await Notification.requestPermission();
};

const showLocalNotification = (title, body, swRegistration) => {
  const options = {
    body
    // here you can add more properties like icon, image, vibrate, etc.
  };
  swRegistration.showNotification(title, options);
};
