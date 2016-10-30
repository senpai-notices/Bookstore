const defaultUserState = { status: "loggedOut" }
export const UserReducer = (state, action) => {
	let newState = Object.assign({}, state)

	switch (action.type){
		case "SET_USER":
			// do not allow changing user state when logged in
			if (newState.status !== "loggedOut"){
				return newState
			}
			newState[action.event.target.name] = action.event.target.value
			return newState
		case "LOGOUT":
			newState.password = ""
			newState.status = "loggedOut"
			return newState
		case "LOGGING_IN":
			newState.status = "loggingIn"
			return newState
		case "LOGIN":
			newState = action.user
			newState.status = "loggedIn"
			return newState
		default:
			return state === undefined ? defaultUserState : state
	}
}