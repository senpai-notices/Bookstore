export const load = (key, defaultValue) => {
	try {
		const serializedState = localStorage.getItem(key)

		if (serializedState === null) {
			return defaultValue
		}

		return JSON.parse(serializedState)
	} catch (err) {
		return undefined
	}
}

export const save = (key, value) => {
	try {
		const serializedState = JSON.stringify(value)
		localStorage.setItem(key, serializedState)
	} catch (err) {
	}
}