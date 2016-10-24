export const UserReducer = (state, action) => {
	switch (action.type){
		case "SET_USER":
			return action.user
		case "REMOVE_USER":
			return null
		default:
			return state === undefined ? null : state
	}
}