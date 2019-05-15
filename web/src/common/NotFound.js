import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Button } from 'react-bootstrap';

class NotFound extends Component {
    render() {
        return (
            <div className="page-not-found">
                <h1 className="title">
                    404
                </h1>
                <div className="desc">
                    The Page you're looking for was not found.
                </div>
                <Link to="/"><Button variant="info" size="sm" type="submit" block className="mb-2">Go Back</Button></Link>
            </div>
        );
    }
}

export default NotFound;