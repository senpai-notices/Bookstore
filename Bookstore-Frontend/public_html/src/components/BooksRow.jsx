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
				<bs.Col xs={12 / books.length}>
					<BookItemThumbnail book={book}/>
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