import * as localStorage from 'localStorage'

const defaultShoppingCartState = {
	items: []
}

export const ShoppingCartReducer = (state, action) => {
	let newState = Object.assign({}, state)

	switch (action.type){
		case 'ADD_ITEM':
			newState.items = [...state.items, {'itemId': action.itemId, 'sellerId': action.sellerId}]
			localStorage.save('shoppingCart', newState)
			return newState
		case 'REMOVE_ITEM':
			filteredItems = newState.items.filter( (item) => {
				return (item.itemId !== action.itemId && item.sellerId !== action.sellerId)
			})
			newState.items = [...filteredItems]
			localStorage.save('shoppingCart', newState)
			return newState
		case 'CLEAR_CART':
			newState.items = []
			localStorage.save('shoppingCart', newState)
			return newState
	}

	return state === undefined ? localStorage.load('shoppingCart', defaultShoppingCartState) : state
}