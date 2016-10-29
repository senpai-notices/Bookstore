const defaultShoppingCartState = {
	items: []
}

export const ShoppingCartReducer = (state, action) => {
	return state === undefined ? defaultShoppingCartState : state
}