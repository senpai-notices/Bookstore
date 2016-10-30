import React, { Component } from 'react'
import * as bs from 'react-bootstrap'
import { BooksRow } from 'components'

class BooksGrid extends React.Component {

	constructor(props){
		super(props)
		this.state = { }
	}

	render() {
		const books = this.props.books
		let booksGridView = []
		let i = 0
		for (i =0; i<books.length; i+=4){
			const booksRow = books.slice(i, i+4)
			let booksRowView = (
				<BooksRow books={booksRow} key={i} onItemClick={this.props.onItemClick} />
			)
			booksGridView.push(booksRowView)
		}

		return (
			<bs.Grid fluid>
				{booksGridView}
			</bs.Grid>
		)
	}
}

export default BooksGrid