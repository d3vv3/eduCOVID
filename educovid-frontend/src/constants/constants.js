// Actions
export const LOG_IN = "LOG_IN";
export const LOG_OUT = "LOG_OUT";

// Initial Redux State
export const initialState = {
    loggedIn: false,
    userData: null
};

// Backend url
export const backUrl = process.env["BACKEND"] || "http://localhost:8080/educovid-backend/rest";