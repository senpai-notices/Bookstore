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
		case "SET_VALIDATE_MESSAGE":
			return action.validateMessage
		case "REMOVE_VALIDATE_MESSAGE":
			newState.errors = Array()
			newState.form_errors = {}
			return newState
		case "ADD_ERROR_MESSAGE":		
			newState.errors = [...state.errors, action.error]
			return newState
		case "ADD_FORM_ERROR":
			newState.form_errors[action.formName] = action.formError
			return newState
		default:
			return state === undefined ? defaultValidationState : state		

	}
}