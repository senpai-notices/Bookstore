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
		//queries.push('maxResults=40')
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

	updateBookSale(salesData, book){
		let refinedSalesData = []
		salesData.forEach((sale) =>{
			refinedSalesData.push({
				id: sale.id,
				quantity: sale.quantity,
				price: sale.price,
				bookCondition: sale.bookCondition
			})
		})

		book['sales'] = refinedSalesData

		return reqwest({
			url: config.getServerAddress() + '/book/sales/',
			method: 'post',
			crossOrigin: true,
			withCredentials: true,
			data: JSON.stringify(book),
			headers: {
				'Authorization': config.getAuthHeader(),
				'Content-Type': 'application/json'
			}
		})
	}

	getBookSales(salesRequest){
		let data = []
		salesRequest.forEach((req) =>{
			data.push(req.id)
		})
		return reqwest({
			url: config.getServerAddress() + '/book/sales/',
			method: 'get',
			crossOrigin: true,
			data: {
				saleIds: data.join()
			}
		})
	}

	getPinToken(data){
		return reqwest({
			url: config.getServerAddress() + '/payment/customer/create2',
			method: 'post',
			crossOrigin: true,
			withCredentials: true,
			data: JSON.stringify(data),
			headers: {
				'Authorization': config.getAuthHeader(),
				'Content-Type': 'application/json'
			}
		})
	}

	calculateShippingCost(quantity, fromPostcode, toPostcode, type){
		return reqwest({
			url: config.getServerAddress() + '/postal/calculate',
			method: 'post',
			crossOrigin: true,
			data: {
				quantity: quantity,
				from: fromPostcode,
				to: toPostcode,
				type: type
			},
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		})
	}

	checkout(data){
		return reqwest({
			url: config.getServerAddress() + '/order/checkout',
			method: 'post',
			crossOrigin: true,
			withCredentials: true,
			data: JSON.stringify(data),
			headers: {
				'Authorization': config.getAuthHeader(),
				'Content-Type': 'application/json'
			}
		})
	}

	getBuyOrders(){
		return reqwest({
			url: config.getServerAddress() + '/order/buy',
			method: 'get',
			crossOrigin: true,
			withCredentials: true,
			headers: {
				'Authorization': config.getAuthHeader()
			}
		})
	}

	getSoldBooks(){
		return reqwest({
			url: config.getServerAddress() + '/order/sold',
			method: 'get',
			crossOrigin: true,
			withCredentials: true,
			headers: {
				'Authorization': config.getAuthHeader()
			}
		})
	}

	getSellingBooks(){
		return reqwest({
			url: config.getServerAddress() + '/order/selling',
			method: 'get',
			crossOrigin: true,
			withCredentials: true,
			headers: {
				'Authorization': config.getAuthHeader()
			}
		})
	}

	sendCheckoutCompleteEmail(orderId){
		return reqwest({
			url: config.getServerAddress() + `/email/order/${orderId}/complete`,
			method: 'post',
			crossOrigin: true,
			withCredentials: true,
			headers: {
				'Authorization': config.getAuthHeader()
			}
		})
	}
}

export default BookService