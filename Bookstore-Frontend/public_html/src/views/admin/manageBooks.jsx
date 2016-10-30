import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'

class ManageBooksView extends BaseView {
	render(){
		return(
			<div>Manage book</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageBooksView)