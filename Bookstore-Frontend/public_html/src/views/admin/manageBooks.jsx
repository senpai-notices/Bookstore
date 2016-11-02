import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { LoadingSpinner, BookItemThumbnail } from 'components'

class ManageBooksView extends BaseView {
	constructor(props){
		super(props)

		this.searchBook = this.searchBook.bind(this)
		this.getBookDetail = this.getBookDetail.bind(this)
		this.state.bookList = []
	}

	getBookDetail(book){

		this.setState({loadingBookDetail: true})
		this.bookService.getBookDetail(book.isbn10, book.isbn13, book.title)
			.then((bookDetail) => {
				this.state.selectedBook = bookDetail
			})
			.fail((err) => {
				this.state.selectedBook = book
			})
			.always(() =>{
				this.state.loadingBookDetail = false;
				this.setState(this.state)
			})
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
						let currentIsbn10 = ''
						let currentIsbn13 = ''
						if (item.volumeInfo.industryIdentifiers){
							item.volumeInfo.industryIdentifiers.forEach((isbn) => {
								if (isbn.type === "ISBN_10"){
									matched = matched || isbn10[isbn.identifier]
									isbn10[isbn.identifier] = true
									currentIsbn10 = isbn.identifier
								} else if (isbn.type === "ISBN_13"){
									matched = matched || isbn13[isbn.identifier]
									isbn13[isbn.identifier] = true
									currentIsbn13 = isbn.identifier
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

						let author = ''
						if (item.volumeInfo.authors){
							author = item.volumeInfo.authors[0]
						}

						let imgPath = ''
						if (item.volumeInfo.imageLinks){
							imgPath = item.volumeInfo.imageLinks.thumbnail
						}

						let publishYear = ''
						if (item.volumeInfo.publishedDate){
							publishYear = item.volumeInfo.publishedDate.substring(0, 4)
						}

						let book = {
							title: item.volumeInfo.title,
							author: author,
							imgPath: imgPath,
							isbn10: currentIsbn10,
							isbn13: currentIsbn13,
							publisher: item.volumeInfo.publisher,
							publishYear: publishYear
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
				if (book.isbn10) {
					isbn.push(<div>{book.isbn10}</div>)
				}
				if (book.isbn13) {
					isbn.push(<div>{book.isbn13}</div>)	
				}

				bookListView.push(
					<tr key={key} style={{cursor: "pointer"}}
						onClick={() => this.getBookDetail(book)}>
						<td style={tdStyle}>{isbn}</td>
						<td style={tdStyle}>{book.title}</td>
						<td style={tdStyle}>{book.author}</td>
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

		const searchLoader = <LoadingSpinner visible={this.state.searching}/>

		const detailLoader = <LoadingSpinner visible={this.state.loadingBookDetail}/>
		let bookDetail = ""
		if (this.state.selectedBook && !this.state.loadingBookDetail){
			bookDetail = (
				<BookItemThumbnail book={this.state.selectedBook} onClick={(book) => {console.log(book)}} />
			)
		}

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
						{searchLoader}
						{searchResult}
					</bs.Col>
					<bs.Col xs={6}>
						{detailLoader}
						{bookDetail}
					</bs.Col>
				</bs.Row>
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageBooksView)