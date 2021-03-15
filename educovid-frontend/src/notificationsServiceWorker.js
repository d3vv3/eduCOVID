export const isPushNotificationSupported = () => {
  return "serviceWorker" in navigator && "PushManager" in window;
};

export const registerServiceWorker = async () => {
  return await navigator.serviceWorker.register(
    "../notificationsServiceWorker.js"
  );
};

export const askUserPermission = async () => {
  return await Notification.requestPermission();
};

export const showLocalNotification = (title, body, swRegistration) => {
  const options = {
    body,
    icon: "logo192.png"
    // here you can add more properties like icon, image, vibrate, etc.
  };
  swRegistration.showNotification(title, options);
};
