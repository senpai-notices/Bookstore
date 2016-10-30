import React, { Component } from 'react'
import * as bs from 'react-bootstrap'
import { BookItemThumbnail } from 'components'

class BooksRow extends React.Component {

	constructor(props){
		super(props)
		this.state = { }
	}

	render() {
		const books = this.props.books
		let booksRowView = []
		books.forEach((book) => {
			let bookItemView = (
				<bs.Col sm={12} md={6} lg={3} key={book.id}>
					<BookItemThumbnail book={book} onClick={this.props.onItemClick}/>
				</bs.Col>
			)
			booksRowView.push(bookItemView)
		})

		return (
			<bs.Row>
				{booksRowView}
			</bs.Row>
		)
	}
}

export default BooksRow