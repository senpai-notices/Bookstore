import reqwest from 'reqwest'
import * as config from 'config'
import {store} from 'index'

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
}

export default BookService