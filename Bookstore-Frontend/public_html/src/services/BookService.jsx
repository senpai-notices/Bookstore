import reqwest from 'reqwest'
import * as config from 'config'
import {store} from 'index'

class BookService {
	getLatestBook(offSet, limit){
		return reqwest({
			url: config.getServerAddress() + '/book/latest',
			method: 'get',
			crossOrigin: true,
			data: {
				offSet: offSet,
				limit: limit
			}
		})
	}
}

export default BookService