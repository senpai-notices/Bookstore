// user actions
export const setUser = (event) => ({
	type: "SET_USER",
	event
})

export const logout = () => ({
	type: "LOGOUT"
})

export const loggingIn = () => ({
	type: "LOGGING_IN"
})

export const login = (user) => ({
	type: "LOGIN",
	user
})

// validation actions
export const setValidationMessage = (validationMessage) => ({
	type: "SET_VALIDATION_MESSAGE",
	validationMessage
})

export const removeValidationMessage = () => ({
	type: "REMOVE_VALIDATION_MESSAGE"
})

export const addErrorMessage = (errorMessage) => ({
	type: "ADD_ERROR_MESSAGE",
	errorMessage
})

export const setFormErrorMessage = (formName, errorMessage=undefined) => ({
	type: "SET_FORM_ERROR_MESSAGE",
	formName,
	errorMessage
})

// shopping cart actions
export const addItem = (itemId, sellerId) => ({
	type: "ADD_ITEM",
	itemId,
	sellerId
})

export const removeItem = (itemId, sellerId) => ({
	type: "REMOVE_ITEM",
	itemId,
	sellerId
})

export const clearCart = () => ({
	type: "CLEAR_CART"
})