// Actions
export const LOG_IN = "LOG_IN";
export const LOG_OUT = "LOG_OUT";
export const CONFINE_MESSAGE = "CONFINE_MESSAGE";
export const UNCONFINE_MESSAGE = "UNCONFINE_MESSAGE";

// Initial Redux State
export const initialState = {
    loggedIn: false,
    userData: null,
    confineMessage: "",
    unconfineMessage: ""
};

// Backend url
export const backUrl = process.env["BACKEND"] || "http://localhost:8080/educovid-backend/rest";
