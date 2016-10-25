// sample validate message structure
// const sampleValidateMessage = {
// 	errors: ['error message1', 'error message 2'],
// 	form_errors: {
// 		formname1: 'form error 1',
// 		formname2: 'form error 2'
// 	}
// }

const defaultValidationState = {
	errors: Array(),
	form_errors: {}
}

export const ValidationReducer = (state, action) => {
	let newState = Object.assign({}, state)
	
	switch (action.type){
		case "SET_VALIDATION_MESSAGE":
			return action.validationMessage
		case "REMOVE_VALIDATION_MESSAGE":
			newState.errors = Array()
			newState.form_errors = {}
			return newState
		case "ADD_ERROR_MESSAGE":		
			newState.errors = [...state.errors, action.errorMessage]
			return newState
		case "SET_FORM_ERROR_MESSAGE":
			newState.form_errors[action.formName] = action.errorMessage
			return newState
		default:
			return state === undefined ? defaultValidationState : state		

	}
}