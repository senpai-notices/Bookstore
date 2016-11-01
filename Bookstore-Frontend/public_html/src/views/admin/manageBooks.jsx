import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { LoadingSpinner } from 'components'

class ManageBooksView extends BaseView {
	constructor(props){
		super(props)

		this.searchBook = this.searchBook.bind(this)
		this.state.bookList = []
	}

	searchBook(event){
		event.preventDefault()

		this.state.bookList = []
		this.state.searching = true
		this.setState(this.state)
		this.bookService.findFromGoogleAPI(this.state.isbn, this.state.title)
			.then((resp) => {
				let isbn10 = {}
				let isbn13 = {}
				if (resp.items){
					resp.items.forEach((item) => {

						// check duplicate books based on isbn
						let matched = false
						if (item.volumeInfo.industryIdentifiers){
							item.volumeInfo.industryIdentifiers.forEach((isbn) => {
								if (isbn.type === "ISBN_10"){
									matched = matched || isbn10[isbn.identifier]
									isbn10[isbn.identifier] = true
								} else if (isbn.type === "ISBN_13"){
									matched = matched || isbn13[isbn.identifier]
									isbn13[isbn.identifier] = true
								}
							})

						} else {
							// do not add books without isbn
							return
						}

						if (matched){
							return
						}

						let isbn = []
						isbn.push(item.volumeInfo.industryIdentifiers[0].identifier.match(/\d+X?/)[0])
						if (item.volumeInfo.industryIdentifiers[1]){
							isbn.push(item.volumeInfo.industryIdentifiers[1].identifier.match(/\d+X?/)[0])
						}

						let book = {
							title: item.volumeInfo.title,
							authors: item.volumeInfo.authors,
							categories: item.volumeInfo.categories,
							imgLinks: item.volumeInfo.imageLinks,
							isbn: item.volumeInfo.industryIdentifiers,
							publisher: item.volumeInfo.publisher,
							publishYear: item.volumeInfo.publishYear
						}

						this.state.bookList.push(book)
					})
				}
			})
			.always(() =>{
				this.state.searching = false
				this.setState(this.state)
			})
	}

	render(){

		let searchResult = ""
		const tdStyle = {
			verticalAlign: "middle"
		}
		if (this.state.bookList.length > 0){
			let bookListView = []
			let key = 0;
			this.state.bookList.forEach((book) => {
				key++
				let isbn = []
				if (book.isbn[0]) {
					isbn.push(<div>{book.isbn[0].identifier.match(/\d+X?/)[0]}</div>)
				}
				if (book.isbn[1]) {
					isbn.push(<div>{book.isbn[1].identifier.match(/\d+X?/)[0]}</div>)	
				}

				let author = book.authors?book.authors.join("; ") : "Unknown"
				bookListView.push(
					<tr key={key} onClick={() => this.state.selectedBook = book}>
						<td style={tdStyle}>{isbn}</td>
						<td style={tdStyle}>{book.title}</td>
						<td style={tdStyle}>{author}</td>
					</tr>
				)
			})

			searchResult = (
				<bs.Table striped bordered condensed hover>
					<thead>
						<tr>
							<th>ISBN</th>
							<th>Title</th>
							<th>Author(s)</th>
						</tr>
					</thead>
					<tbody>
					{bookListView}
					</tbody>
				</bs.Table>
			)
		}

		const searchLoading = <LoadingSpinner visible={this.state.searching}/>
		let bookDetail = (<h3>Book detail here</h3>)

		return(
			<div>
				<h2>Add or edit books</h2>
				<hr/>

				<h3>Enter book title or ISBN</h3>
				<bs.Form horizontal onSubmit={this.searchBook}>
					<bs.Row>

						<bs.Col xs={6}>
							<bs.ControlLabel>Title</bs.ControlLabel>
							<bs.FormControl type="text" name="title" placeholder="Title" onChange={this.handleChange}
								required={!this.state.isbn && !this.state.title}/>
						</bs.Col>

						<bs.Col xs={6}>
							<bs.ControlLabel>ISBN</bs.ControlLabel>
							<bs.FormControl type="text" name="isbn" placeholder="ISBN" onChange={this.handleChange}
								required={!this.state.isbn && !this.state.title}/>
						</bs.Col>
					
					</bs.Row>
					<br/>
					<bs.Button bsStyle="success" type="submit" disabled={this.state.searching}>
						{(this.state.searching && "Searching") || "Search"}
					</bs.Button>
				</bs.Form>

				<br/>

				<bs.Row>
					<bs.Col xs={6}>
						{searchLoading}
						{searchResult}
					</bs.Col>
					<bs.Col xs={6}>
						{bookDetail}
					</bs.Col>
				</bs.Row>
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageBooksView)