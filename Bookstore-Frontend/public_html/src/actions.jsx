// user actions
export const setUser = (user) => ({
	type: "SET_USER",
	user
})

export const removeUser = () => ({
	type: "REMOVE_USER"
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
