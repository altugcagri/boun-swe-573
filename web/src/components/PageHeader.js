import React, { Component } from 'react'
import { Link } from "react-router-dom";

class PageHeader extends Component {

    render() {
        const props = this.props;
        return (

            <div className="pageHeader text-left">
                <div className="container">
                    <div className="row">
                        <div className="col-md-6">
                            {props.title}
                        </div>
                        <div className="col-md-6 text-right">
                            <Link to={`/`} className="breadcrumbLink">
                                <span>Home</span>
                            </Link>
                            {props.children}
                            <span className="breadcrumbLink">{props.title}</span>
                        </div>
                    </div>
                </div>
            </div>
        )

    }
}

export default PageHeader;