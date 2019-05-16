import React, { Component } from 'react';
import { Col } from "react-bootstrap";


export default class Loading extends Component {

    render() {
        return (
            <Col sm={12} className="text-center mt-5">
                Loading...
            </Col>
        )
    }
}
