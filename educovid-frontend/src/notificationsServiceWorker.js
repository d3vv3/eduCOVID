import { backUrl } from "./constants/constants";

export const isPushNotificationSupported = () => {
  return "serviceWorker" in navigator && "PushManager" in window;
};

export const registerServiceWorker = async () => {
  return await navigator.serviceWorker.register(
    "../sw.js"
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

export const createNotificationSubscription = async () => {
  // wait for service worker installation to be ready
  const serviceWorker = await navigator.serviceWorker.ready;

  const publicSigningKey = await (await fetch(backUrl + "/notification/publicSigningKey")).arrayBuffer();

  // subscribe and return subscription
  return await serviceWorker.pushManager.subscribe({
    userVisibleOnly: true,
    applicationServerKey: publicSigningKey
  });
};