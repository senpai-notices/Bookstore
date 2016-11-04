import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { LoadingSpinner, EditableTable, StaticTable } from 'components'
import InlineEdit from 'react-edit-inline'

class ManageBooksView extends BaseView {
	constructor(props){
		super(props)

		this.searchBook = this.searchBook.bind(this)
		this.getBookDetail = this.getBookDetail.bind(this)
		this.removeSale = this.removeSale.bind(this)
		this.addSale = this.addSale.bind(this)
		this.saveSale = this.saveSale.bind(this)
		this.state.bookList = []
	}

	saveSale(){
		// TODO: client side validation

		let valid = true
		let priceList = []
		this.state.userSalesInfo.forEach((saleInfo) => {
			if (!saleInfo.quantity.toString().match(/^[1-9][0-9]*$/)) {
				alert("Quantity must be a positive integer")
				valid = false
				return
			}
			if (isNaN(saleInfo.price) || saleInfo.price < 0.01) {
				alert("Please enter a valid price (at least 0.01 $AUD)")
				valid = false
				return	
			}
			if (saleInfo.bookCondition.length < 3){
				alert("Condition must be a least 3 characters long")
				valid = false
				return
			}
		})

		if (!valid) return

		this.state.savingSales = true
		this.setState(this.state)
		this.bookService.updateBookSale(this.state.userSalesInfo, this.state.selectedBook)
			.then((resp) => {
				this.state.selectedBook = resp
				this.state.saveCompleted = true
			})
			.fail((err) =>{
				alert("Cannot update sales data")
			})
			.always(() =>{
				this.state.savingSales = false;
				this.setState(this.state)
			})
	}

	addSale(){
		let newSale = {
			bookCondition: "<<condition>>",
			price: "<<price>>",
			quantity: "<<quantity>>",
			index: this.state.userSalesInfo.length,
			actions: (<bs.Button bsStyle="danger" onClick={() => this.removeSale(newSale)}>Remove</bs.Button>)
		}

		this.state.userSalesInfo.push(newSale)
		this.setState(this.state)
	}

	removeSale(sale){
		this.state.userSalesInfo.splice(sale.index, 1)
		let saleIndex = 0
		this.state.userSalesInfo.forEach((saleInfo) =>{
			saleInfo.index = saleIndex
			saleIndex++
			saleInfo.actions = (<bs.Button bsStyle="danger" onClick={() => this.removeSale(saleInfo)}>Remove</bs.Button>)
		})
		this.setState(this.state)
	}

	getBookDetail(book){

		this.state.userSalesInfo = []
		this.state.loadingBookDetail = true
		this.state.saveCompleted = false
		this.setState(this.state)

		this.bookService.getBookDetail(book.isbn10, book.isbn13, book.title)
			.then((bookDetail) => {
				this.state.selectedBook = bookDetail

				let saleIndex = 0
				bookDetail.sales.forEach((sale) => {
					if (sale.sellerName === this.props.user.username){
						sale.index = saleIndex
						saleIndex++
						this.state.userSalesInfo.push({
							bookCondition: sale.bookCondition,
							price: sale.price,
							quantity: sale.quantity,
							actions: (<bs.Button bsStyle="danger" onClick={() => this.removeSale(sale)}>Remove</bs.Button>)
						})
					}
				})
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
		this.state.saveCompleted = false
		this.state.selectedBook = ""
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

			searchResult = (
				<StaticTable dataList={this.state.bookList}
					headers={['ISBN10', 'ISBN13', 'Title', 'Author']}
					columns={['isbn10', 'isbn13', 'title', 'author']}
					tdStyle={tdStyle} trStyle={{cursor: "pointer"}}
					onRowClick={(selectedBook) => this.getBookDetail(selectedBook)}/>
			)
		}

		const searchLoader = <LoadingSpinner visible={this.state.searching}/>

		const detailLoader = <LoadingSpinner visible={this.state.loadingBookDetail}/>
		let bookDetail = ""
		if (this.state.selectedBook && !this.state.loadingBookDetail){
			const selectedBook = this.state.selectedBook

			let userSalesView = ""
			if (this.state.userSalesInfo.length > 0){

				userSalesView = (
					<div>
						<EditableTable dataList={this.state.userSalesInfo}
							headers={['Quantity', 'Price (AUD)', 'Condition', 'Actions']}
							columns={['quantity', 'price', 'bookCondition', 'actions']}
							tdStyle={tdStyle}
							onChange={(resultList) => this.setState({userSalesInfo: resultList})} />

						<p><small>Click on the text to edit your sales</small></p>
					</div>
				)
			}

			let saveCompleted = ""
			if (!this.state.savingSales && this.state.saveCompleted){
				saveCompleted = (<bs.Glyphicon style={{color: "#5cb85c"}} glyph="ok"/>)
			}
			bookDetail = (
				<div>
					<bs.Row>
						<bs.Col xs={6}>
							<bs.Thumbnail src={selectedBook.imgPath}/>
						</bs.Col>
						<bs.Col xs={6}>
							<h3>{selectedBook.title}</h3>
							<h4>Author: {selectedBook.author}</h4>
							<h4>Published in: {selectedBook.publishYear}</h4>
							<h4>Pubisher: {selectedBook.publisher}</h4>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Button bsStyle="primary" onClick={() => this.addSale()}>Add Sale</bs.Button>
						&nbsp;
						<bs.Button bsStyle="success" onClick={() => this.saveSale()} disabled={this.state.savingSales}>
							{(this.state.savingSales && "Saving") || "Save"}
						</bs.Button>
						&nbsp;&nbsp;&nbsp;
						{saveCompleted}
						<br/>
						<br/>
						{userSalesView}
					</bs.Row>
				</div>
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