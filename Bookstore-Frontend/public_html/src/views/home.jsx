import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'

class HomeView extends React.Component{

	constructor(props){
		super(props)
	}

	render(){
		return (
			<h1>Home View</h1>
		)
	}
}

export default HomeView