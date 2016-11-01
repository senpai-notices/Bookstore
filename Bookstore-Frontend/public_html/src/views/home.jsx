import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { BooksGrid, LoadingSpinner } from 'components'
import InfiniteScroll from 'react-infinite-scroller'

class HomeView extends BaseView {

	constructor(props){
		super(props)

		this.state.books = []
		this.state.hasMore = true
		this.loadMoreBooks = this.loadMoreBooks.bind(this)
		this.viewBookDetail = this.viewBookDetail.bind(this)
	}

	loadMoreBooks(){
		this.bookService.getLatestBook(this.state.books.length, 20)
			.then( (resp) => {
				this.state.books = this.state.books.concat(resp)
				if (resp.length < 20){
					this.state.hasMore = false
				}
				this.setState(this.state)
			})
	}

	viewBookDetail(book, event){
		event.preventDefault();
		
	}

	render(){
		const loading = <LoadingSpinner visible/>

		return (
			<InfiniteScroll pageStart={0} loadMore={this.loadMoreBooks} hasMore={this.state.hasMore} loader={loading}>
				<BooksGrid books={this.state.books} onItemClick={this.viewBookDetail}/>
			</InfiniteScroll>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(HomeView)