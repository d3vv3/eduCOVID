// Constants
import { LOG_IN, LOG_OUT, CONFINE_MESSAGE, UNCONFINE_MESSAGE } from "../constants/constants";

export function logIn(userData) {
  return { type: LOG_IN, payload: { userData } };
}

export function logOut() {
  return { type: LOG_OUT };
}

export function changeConfineMessage(confineMessage) {
  return { type: CONFINE_MESSAGE, payload: {confineMessage} };
}

export function changeUnconfineMessage(unconfineMessage) {
  return { type: UNCONFINE_MESSAGE, payload: {unconfineMessage} };
}
