import React, { Component } from 'react';
import image_1 from '../img/improve1.jpg'
import image_2 from '../img/improve2.jpg'
import { Link } from "react-router-dom";

export default class Home extends Component {

    render() {
        return (
            <React.Fragment>
                <div className="mt-5 mb-5 text-left sectionPadding">
                    <div className="container">
                        <div className="row align-items-center">
                            <div className="col-md-6">
                                <h4 className="mt-5 mb-5"><strong className="strong">What we share makes us who we are!</strong></h4>
                                <p>
                                    Share what you know, what you have and what you are. Let the World who you are. Learn things from others. Expand your World
                                <br /><br />
                                    <Link className="btn btn-outline-primary btn-xl" to="/explore">Get Started</Link>

                                </p>
                            </div>
                            <div className="col-md-1"></div>
                            <div className="col-md-5">
                                <img src={image_1} className="img-fluid" alt="" />
                            </div>
                        </div>
                    </div>
                </div>
                <div className="mt-5 text-left bg-alt sectionPadding">
                    <div className="container">
                        <div className="row align-items-center">
                            <div className="col-md-5">
                                <img src={image_2} className="img-fluid" alt="" />
                            </div>
                            <div className="col-md-1"></div>
                            <div className="col-md-6">
                                <h4 className="mt-5 mb-5"> <strong className="strong">Start know! Creat Your Learning Path</strong></h4>
                                <p>
                                    Create Learning Paths. Show what you know! <br />
                                    Enter a new path and get lost while exploring.
                                    <br /><br />
                                    <Link className="btn btn-outline-primary btn-xl" to="/explore">Get Started</Link>
                                </p>
                            </div>
                        </div>
                    </div>

                </div>
            </React.Fragment>
        )
    }
}
