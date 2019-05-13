import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faHeart } from '@fortawesome/free-solid-svg-icons'

class Footer extends Component {

    render() {
        return (
            <React.Fragment>
                <div className="myFooter">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-6 text-right">
                                Bogazici University &copy; 2019
                            </div>
                        </div>
                    </div>
                </div>

            </React.Fragment>
        )
    }
}

export default Footer;