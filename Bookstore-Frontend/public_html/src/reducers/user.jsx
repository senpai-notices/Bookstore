export const UserReducer = (state, action) => {

	if (action.type == "SET_USER"){
		return action.user
	} else if (action.type == "REMOVE_USER"){
		return null
	}

	return state === undefined ? null : state
}