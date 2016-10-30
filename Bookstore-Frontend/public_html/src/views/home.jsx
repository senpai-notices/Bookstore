import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { BooksGrid } from 'components'
import InfiniteScroll from 'react-infinite-scroller'
import Halogen from 'halogen'

class HomeView extends BaseView {

	constructor(props){
		super(props)

		this.state.books = []
		this.state.hasMore = true
		this.loadMoreBooks = this.loadMoreBooks.bind(this)
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

	render(){
		const loading = (
			<div>
				<bs.Row>
					<bs.Col xs={2} xsOffset={5}>
						<Halogen.RingLoader color="#000" size="100%"/>
					</bs.Col>
				</bs.Row>
				<bs.Row>
					<bs.Col className={'text-center'}>
						<h2>Loading...</h2>
					</bs.Col>
				</bs.Row>
			</div>
		)

		return (
			<InfiniteScroll pageStart={0} loadMore={this.loadMoreBooks} hasMore={this.state.hasMore} loader={loading}>
				<BooksGrid books={this.state.books}/>
			</InfiniteScroll>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(HomeView)