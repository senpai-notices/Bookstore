import React, { Component } from 'react'
import * as bs from 'react-bootstrap'

class BookItemThumbnail extends React.Component {

	constructor(props){
		super(props)
		this.state = { }
	}

	render() {
		const book = this.props.book
		return (
			<a href="#" onClick={this.props.onClick.bind(null, book)}>
				<bs.Thumbnail src={book.imgPath}>
					<h3>{book.title}</h3>
					<p>{book.author} ({book.publishYear})</p>
				</bs.Thumbnail>
			</a>
		)
	}
}

export default BookItemThumbnail