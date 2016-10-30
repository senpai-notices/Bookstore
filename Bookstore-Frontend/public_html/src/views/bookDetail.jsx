import React, { Component } from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'

class BookDetailView extends BaseView {

	render(){
		return (
			<div>{this.props.book.title}</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(BookDetailView)