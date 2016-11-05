// user actions
export const setUser = (key, value) => ({
	type: "SET_USER",
	key,
	value
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
export const addItem = (sale) => ({
	type: "ADD_ITEM",
	sale
})

export const removeItem = (saleId) => ({
	type: "REMOVE_ITEM",
	saleId
})

export const clearCart = () => ({
	type: "CLEAR_CART"
})

export const updateCart = (items) => ({
	type: "UPDATE_CART",
	items
})

export const updateItemQuantity = (id, quantity) => ({
	type: "UPDATE_QUANTITY",
	id,
	quantity
})