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
		const itemsPerRow = this.props.itemsPerRow

		let booksGridView = []
		let i = 0
		for (i =0; i<books.length; i+=itemsPerRow){
			const booksRow = books.slice(i, i+itemsPerRow)
			let booksRowView = (
				<BooksRow books={booksRow} />
			)
			booksGridView.push(booksRowView)
		}

		return (
			<bs.Grid>
				{booksGridView}
			</bs.Grid>
		)
	}
}

export default BooksGrid