import React, { Component } from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import { FormInputText, FormAddressInput, ErrorDisplay } from 'components'
import { SalesListView } from 'views'
import * as bs from 'react-bootstrap'
import { browserHistory } from 'react-router'

class CheckoutView extends BaseView {
	constructor(props){
		super(props)

		this.calculateTotalPrice = this.calculateTotalPrice.bind(this)
		this.onAddressSelected = this.onAddressSelected.bind(this)
		this.submitPayment = this.submitPayment.bind(this)
	}

	componentWillMount(){
		// if (nextProps.user.status !== 'loggedIn' 
		// 	|| nextProps.shoppingCart.items.length === 0){
		// 	browserHistory.replace("/")
		// }
	}

	componentWillReceiveProps(nextProps){
		// if (nextProps.user.status !== 'loggedIn' 
		// 	|| nextProps.shoppingCart.items.length === 0){
		// 	browserHistory.replace("/")
		// }
	}

	calculateTotalPrice(){
		let totalPrice = 0
		this.props.shoppingCart.items.forEach((item) =>{
			totalPrice += item.totalPrice
		})

		return totalPrice
	}

	onAddressSelected(event){
		let selectedAddress = event.target.value
		const addressParts = selectedAddress.split(',')

		this.state['card.address_line1']  = addressParts[0]
		this.state['card.address_city'] = addressParts[1]
		this.state['card.address_state'] = addressParts[2]
		this.state['card.address_country'] = addressParts[3]
		this.setState(this.state)

		this.addressSuggest.setValue(this.state['card.address_line1'])
	}

	submitPayment(event){
		event.preventDefault()

		let data = {}
		data.email = this.props.user.email
		data.card = {}
		data.card.number = this.state['card.number']
		data.card.expiry_month = this.state['card.expiry_month']
		data.card.expiry_year = this.state['card.expiry_year']
		data.card.cvc = this.state['card.cvc']
		data.card.name = this.state['card.name']
		data.card.address_line1 = this.state['card.address_line1']
		data.card.address_line2 = this.state['card.address_line2']
		data.card.address_city = this.state['card.address_city']
		data.card.address_postcode = this.state['card.address_postcode']
		data.card.address_state = this.state['card.address_state']
		data.card.address_country = this.state['card.address_country']

		this.bookService.getPinToken(data)
			.then((resp) => {
				data.customer_token = resp.response.token
				data.amount = "100"
				data.description = "Description"
				this.bookService.chargeMoney(data)
					.then((resp) => {
						console.log(resp)
					})
					.fail((err) => {
						const validationResult = JSON.parse(err.response)
						this.state.formErrors = validationResult.formErrors
						this.state.errors = validationResult.errors	
					})
					.always(() => {
						this.setState(this.state)
					})
			})
			.fail((err) => {
				console.log(err)
				const validationResult = JSON.parse(err.response)
				this.state.formErrors = validationResult.formErrors
				this.state.errors = validationResult.errors
			})
			.always(() => {
				this.setState(this.state)
			})
	}

	render(){
		const temp = (<bs.Row>
					<bs.Col xsOffset={8} xs={4}>
						<h4>Total price: AU$ {this.calculateTotalPrice()}</h4>
					</bs.Col>
				</bs.Row>)

		const {errors, formErrors} = this.state

		const checkoutView = (
			<div>
				<SalesListView />

				<bs.Form style={{height: "500px"}} onSubmit={this.submitPayment}>

				<ErrorDisplay errors={this.state.errors} onRemove={(index) => this.removeErrorMessage(index)}/>

				<bs.Col xs={5}>
					<h3>Online payment</h3>
					<bs.Row>
						<bs.Col xs={12}>
							<FormInputText label="Card Number" name="card.number"
											value={this.state['card.number']} errorMessage={formErrors['card.number']}
											onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('card.number')}
											required hideAsterisk/>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Col xs={12}>
							<FormInputText label="Card Name" name="card.name"
											value={this.state['card.name']} errorMessage={formErrors['card.name']}
											onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('card.name')}
											required hideAsterisk/>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Col xs={3}>
							<FormInputText label="Expiry Month" name="card.expiry_month" type="number"
											value={this.state['card.expiry_month']} errorMessage={formErrors['card.expiry_month']}
											onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('card.expiry_month')}
											required hideAsterisk/>
						</bs.Col>

						<bs.Col xs={3}>
							<FormInputText label="Expiry Year" name="card.expiry_year" type="number"
											value={this.state['card.expiry_year']} errorMessage={formErrors['card.expiry_year']}
											onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('card.expiry_year')}
											required hideAsterisk/>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Col xs={3}>
							<FormInputText label="CVC" name="card.cvc"
											value={this.state['card.cvc']} errorMessage={formErrors['card.cvc']}
											onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('card.cvc')}
											required hideAsterisk/>
						</bs.Col>
					</bs.Row>

					<bs.Button type="submit" bsStyle="primary">Submit</bs.Button>
				</bs.Col>

				<bs.Col xs={6}>
				<h3>Shipping detail</h3>
					<bs.Row>
						<bs.Col xs={12}>
							<FormAddressInput label="Address line 1" name="card.address_line1"
												value={this.state['card.address_line1']} errorMessage={formErrors['card.address_line1']}
												onAddressSelected={this.onAddressSelected}
												onFocus={() => this.removeFormErrorMessage('card.address_line1')}
												onChange={this.handleChange} ref={(input) => this.addressSuggest = input}
												required/>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Col xs={12}>
							<FormInputText label="Address line 2" name="card.address_line2"
											value={this.state['card.address_line2']} errorMessage={formErrors['card.address_line2']}
											onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('card.address_line2')}
											style={{width: "203%"}}/>
						</bs.Col>
					</bs.Row>

					<bs.Row>
						<bs.Col xs={6}>
							<FormInputText label="City" name="card.address_city"
											value={this.state['card.address_city']} errorMessage={formErrors['card.address_city']}
											onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('card.address_city')}
											required/>
						</bs.Col>
						<bs.Col xs={6}>
							<FormInputText label="State" name="card.address_state"
											value={this.state['card.address_state']} errorMessage={formErrors['card.address_state']}
											onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('card.address_state')}
											required/>
						</bs.Col>
					</bs.Row>

					<bs.Row>
						<bs.Col xs={6}>
							<FormInputText label="Postcode" name="card.address_postcode"
											value={this.state['card.address_postcode']} errorMessage={formErrors['card.address_postcode']}
											onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('card.address_postcode')}
											required/>
						</bs.Col>
						<bs.Col xs={6}>
							<FormInputText label="Country" name="card.address_country"
											value={this.state['card.address_country']} errorMessage={formErrors['card.address_country']}
											onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('card.address_country')}
											required/>
						</bs.Col>

						<bs.Button type="submit" bsStyle="primary">Calculate cost</bs.Button>
					</bs.Row>
				</bs.Col>
				</bs.Form>
			</div>
		)

		return (
			<div>
				{checkoutView}
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(CheckoutView)