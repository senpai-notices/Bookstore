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
			<bs.Thumbnail src={book.imgPath}>
				<h3>{book.title}</h3>
				<p>{book.author}</p>
			</bs.Thumbnail>
		)
	}
}

export default BookItemThumbnail