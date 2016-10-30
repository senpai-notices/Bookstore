import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'

class ManageUsersView extends BaseView {
	render(){
		return(
			<div>Manage account</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageUsersView)