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

export const updateShippingType = (id, shippingType) => ({
	type: "UPDATE_SHIPPING_TYPE",
	id,
	shippingType
})