import * as localStorage from 'localStorage'

const defaultShoppingCartState = {
	items: []
}

export const ShoppingCartReducer = (state, action) => {
	let newState = Object.assign({}, state)

	if (state === undefined){
		newState = localStorage.load('shoppingCart', defaultShoppingCartState)
		// remove items with incorrect format
		newState.items = newState.items.filter((item) => {
			return (item.id && item.book instanceof Object && item.seller instanceof Object)
		})
	}

	switch (action.type){
		case 'ADD_ITEM':
			newState.items = [...state.items, {'id': action.sale.id, 
												'book': action.sale.book, 
												'seller': action.sale.seller,
												'bookCondition': action.sale.bookCondition,
												'maxQuantity': action.sale.quantity,
												'quantity': 1,
												'price': action.sale.price,
												'totalPrice': action.sale.price}]
			break
		case 'REMOVE_ITEM':
			let filteredItems = newState.items.filter((item) => {
				return (item.id !== action.saleId)
			})
			newState.items = [...filteredItems]
			break
		case 'UPDATE_QUANTITY':
			newState.items.forEach((cartItem) => {
				if (cartItem.id === action.id){
					cartItem.quantity = Math.min(cartItem.maxQuantity, Math.max(action.quantity, 1))
					cartItem.totalPrice = cartItem.quantity * cartItem.price
				}
			})
			break
		case 'CLEAR_CART':
			newState.items = []
			break
		case 'UPDATE_CART':
			newState.items.forEach((cartItem) => {
				let matched = action.items.filter((updateItem) => {
					return (cartItem.id === updateItem.id)
				})

				if (matched.length > 0){
					cartItem['book'] = matched[0].book
					cartItem['seller'] = matched[0].seller
					cartItem['maxQuantity'] = matched[0].quantity
					cartItem['bookCondition'] = matched[0].bookCondition
					cartItem['quantity'] = Math.min(cartItem['quantity'], cartItem['maxQuantity'])
					cartItem['price'] = matched[0].price
					cartItem['totalPrice'] = cartItem['price'] * cartItem['quantity']

				} else {
					cartItem['quantity'] = 0
					cartItem['maxQuantity'] = 0
					cartItem['price'] = 0
					cartItem['totalPrice'] = 0
				}
			})
			break
	}

	localStorage.save('shoppingCart', newState)
	return newState
}