async function createNotificationSubscription() {
  // Wait for service worker installation to be ready
  const serviceWorker = await navigator.serviceWorker.ready;
  // Subscribe and return the subscription
  return await serviceWorker.pushManager.subscribe({
    userVisibleOnly: true,
    applicationServerKey: 'pushServerPublicKey'
  });
}

async function postSubscription(subscription) {
  const response = await fetch(`https://push-notification-demo-server.herokuapp.com/subscription`, {
    credentials: "omit",
    headers: { "content-type": "application/json;charset=UTF-8", "sec-fetch-mode": "cors" },
    body: JSON.stringify(subscription),
    method: "POST",
    mode: "cors"
  });
  return await response.json();
}
