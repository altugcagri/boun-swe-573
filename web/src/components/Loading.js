import React, { Component } from 'react';
import loadingGif from '../img/loading.gif'
import { Col } from "react-bootstrap";


export default class Loading extends Component {

    render() {
        return (
            <Col sm={12} className="text-center mt-5">
                <img src={loadingGif} alt="" /> <br /> Loading...
            </Col>
        )
    }
}
