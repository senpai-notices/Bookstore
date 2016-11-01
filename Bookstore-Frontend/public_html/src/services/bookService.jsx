import reqwest from 'reqwest'
import * as config from 'config'

class BookService {
	getLatestBook(offset, limit){
		return reqwest({
			url: config.getServerAddress() + '/book/latest',
			method: 'get',
			crossOrigin: true,
			data: {
				offset: offset,
				limit: limit
			}
		})
	}

	findFromGoogleAPI(isbn, title){
		let queries = []
		if (isbn) {
			queries.push(`isbn:${isbn}`)
		}
		if (title){
			queries.push(`title:${title}`)
		}
		const queryString = queries.join("&")
		return reqwest({
			url: `https://www.googleapis.com/books/v1/volumes?q=${queryString}`,
			method: 'get',
			crossOrigin: true
		})
	}
}

export default BookService