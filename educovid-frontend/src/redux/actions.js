// Constants
import { LOG_IN, LOG_OUT } from "../constants/constants";

export function logIn(userData) {
  return { type: LOG_IN, payload: { userData } };
}

export function logOut() {
  return { type: LOG_OUT };
}
