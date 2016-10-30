import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { BooksGrid } from 'components'

class HomeView extends BaseView {

	constructor(props){
		super(props)

		this.state.books = []
		this.bookService.getLatestBook(0, 20)
			.then( (resp) => {
				this.state.books = resp
				this.setState(this.state)
			})
	}

	render(){

		return (
			<div>
				<BooksGrid books={this.state.books} itemsPerRow={4} />
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(HomeView)