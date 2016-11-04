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

	getBookDetail(isbn10, isbn13, title){
		return reqwest({
			url: config.getServerAddress() + '/book/',
			method: 'get',
			crossOrigin: true,
			data: {
				isbn10: isbn10,
				isbn13: isbn13,
				title: title
			}
		})
	}

	updateBookSale(salesData, bookId){
		salesData.forEach((sale) =>{
			sale.actions = undefined
		})

		return reqwest({
			url: config.getServerAddress() + '/book/sales',
			method: 'post',
			crossOrigin: true,
			withCredentials: true,
			data: JSON.stringify({
				id: bookId,
				sales: salesData
			}),
			headers: {
				'Authorization': config.getAuthHeader(),
				'Content-Type': 'application/json'
			}
		})
	}
}

export default BookService