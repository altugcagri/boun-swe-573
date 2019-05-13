import React, { Component } from 'react';
import { signup, checkUsernameAvailability, checkEmailAvailability } from '../util/APIUtils';
import { Link } from 'react-router-dom';
import {
    NAME_MIN_LENGTH, NAME_MAX_LENGTH,
    USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH,
    EMAIL_MAX_LENGTH,
    PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH
} from '../constants';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import toast from "toasted-notes";

class Signup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: {
                value: ''
            },
            username: {
                value: ''
            },
            email: {
                value: ''
            },
            password: {
                value: ''
            }
        }
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.validateUsernameAvailability = this.validateUsernameAvailability.bind(this);
        this.validateEmailAvailability = this.validateEmailAvailability.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    handleInputChange(event, validationFun) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName]: {
                value: inputValue,
                ...validationFun(inputValue)
            }
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        const signupRequest = {
            name: this.state.name.value,
            email: this.state.email.value,
            username: this.state.username.value,
            password: this.state.password.value
        };

        signup(signupRequest)
            .then(response => {
                toast.notify("Thank you! You're successfully registered. Please Login to continue!", { position: "top-right" });
                this.props.history.push("/login");
            }).catch(error => {
                toast.notify('Sorry! Something went wrong. Please try again!', { position: "top-right" });
            });
    }

    isFormInvalid() {
        return !(this.state.name.validateStatus === 'success' &&
            this.state.username.validateStatus === 'success' &&
            this.state.email.validateStatus === 'success' &&
            this.state.password.validateStatus === 'success'
        );
    }

    render() {
        return (
            <div className="sectionPadding bg-alt">
                <div className="container w-25 mt-5">
                    <h4 className="mt-5 mb-5 text-left">Create new account</h4>
                    <Form onSubmit={this.handleSubmit}>
                        <Form.Group className="row" controlId="formPlaintextFullName">

                            <Col sm="12">
                                <Form.Control
                                    name="name"
                                    autoComplete="off"
                                    placeholder="Full name"
                                    value={this.state.name.value}
                                    type="text"
                                    onChange={(event) => this.handleInputChange(event, this.validateName)}
                                />
                                {this.state.name.validateStatus && <p className="text-info">{this.state.name.errorMsg}</p>}
                            </Col>
                        </Form.Group>

                        <Form.Group className="row" controlId="formPlaintextUsername">

                            <Col sm="12">
                                <Form.Control
                                    name="username"
                                    autoComplete="off"
                                    placeholder="Username"
                                    value={this.state.username.value}
                                    onBlur={this.validateUsernameAvailability}
                                    onChange={(event) => this.handleInputChange(event, this.validateUsername)}
                                />
                                {this.state.username.validateStatus && <p className="text-info">{this.state.username.errorMsg}</p>}
                            </Col>
                        </Form.Group>

                        <Form.Group className="row" controlId="formPlaintextEmail">

                            <Col sm="12">
                                <Form.Control
                                    name="email"
                                    type="email"
                                    autoComplete="off"
                                    placeholder="E-mail"
                                    value={this.state.email.value}
                                    onBlur={this.validateEmailAvailability}
                                    onChange={(event) => this.handleInputChange(event, this.validateEmail)}
                                />
                                {this.state.email.validateStatus && <p className="text-info">{this.state.email.errorMsg}</p>}
                            </Col>
                        </Form.Group>

                        <Form.Group className="row" controlId="formPlaintextPassword">

                            <Col sm="12">
                                <Form.Control
                                    name="password"
                                    type="password"
                                    autoComplete="off"
                                    placeholder="Password"
                                    value={this.state.password.value}
                                    onChange={(event) => this.handleInputChange(event, this.validatePassword)}
                                />
                                {this.state.password.validateStatus && <p className="text-info">{this.state.password.errorMsg}</p>}
                            </Col>
                        </Form.Group>
                        <Button
                            className="mt-4"
                            variant="primary"
                            type="submit"
                            block
                            disabled={this.isFormInvalid()}
                        >
                            Sign up
                    </Button>
                        <br />
                        Already have an account? <Link to="/login">Login now!</Link>
                    </Form>
                </div>
            </div>
        );
    }

    validateName = (name) => {
        if (name.length < NAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Minimum ${NAME_MIN_LENGTH} characters needed.`
            }
        } else if (name.length > NAME_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Maximum ${NAME_MAX_LENGTH} characters allowed.`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };
        }
    };

    validateEmail = (email) => {
        if (!email) {
            return {
                validateStatus: 'error',
                errorMsg: 'Email may not be empty'
            }
        }

        const EMAIL_REGEX = RegExp('[^@ ]+@[^@ ]+\\.[^@ ]+');
        if (!EMAIL_REGEX.test(email)) {
            return {
                validateStatus: 'error',
                errorMsg: 'Email not valid'
            }
        }

        if (email.length > EMAIL_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Maximum ${EMAIL_MAX_LENGTH} characters allowed.`
            }
        }

        return {
            validateStatus: null,
            errorMsg: null
        }
    };

    validateUsername = (username) => {
        if (username.length < USERNAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Minimum ${USERNAME_MIN_LENGTH} characters needed.`
            }
        } else if (username.length > USERNAME_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Maximum ${USERNAME_MAX_LENGTH} characters allowed.`
            }
        } else {
            return {
                validateStatus: null,
                errorMsg: null
            }
        }
    };

    validateUsernameAvailability() {
        // First check for client side errors in username
        const usernameValue = this.state.username.value;
        const usernameValidation = this.validateUsername(usernameValue);

        if (usernameValidation.validateStatus === 'error') {
            this.setState({
                username: {
                    value: usernameValue,
                    ...usernameValidation
                }
            });
            return;
        }

        this.setState({
            username: {
                value: usernameValue,
                validateStatus: 'validating',
                errorMsg: null
            }
        });

        checkUsernameAvailability(usernameValue)
            .then(response => {
                if (response.available) {
                    this.setState({
                        username: {
                            value: usernameValue,
                            validateStatus: 'success',
                            errorMsg: null
                        }
                    });
                } else {
                    this.setState({
                        username: {
                            value: usernameValue,
                            validateStatus: 'error',
                            errorMsg: 'This username is already taken'
                        }
                    });
                }
            }).catch(error => {
                // Marking validateStatus as success, Form will be recchecked at server
                this.setState({
                    username: {
                        value: usernameValue,
                        validateStatus: 'success',
                        errorMsg: null
                    }
                });
            });
    }

    validateEmailAvailability() {
        // First check for client side errors in email
        const emailValue = this.state.email.value;
        const emailValidation = this.validateEmail(emailValue);

        if (emailValidation.validateStatus === 'error') {
            this.setState({
                email: {
                    value: emailValue,
                    ...emailValidation
                }
            });
            return;
        }

        this.setState({
            email: {
                value: emailValue,
                validateStatus: 'validating',
                errorMsg: null
            }
        });

        checkEmailAvailability(emailValue)
            .then(response => {
                if (response.available) {
                    this.setState({
                        email: {
                            value: emailValue,
                            validateStatus: 'success',
                            errorMsg: null
                        }
                    });
                } else {
                    this.setState({
                        email: {
                            value: emailValue,
                            validateStatus: 'error',
                            errorMsg: 'This Email is already registered'
                        }
                    });
                }
            }).catch(error => {
                // Marking validateStatus as success, Form will be recchecked at server
                this.setState({
                    email: {
                        value: emailValue,
                        validateStatus: 'success',
                        errorMsg: null
                    }
                });
            });
    }

    validatePassword = (password) => {
        if (password.length < PASSWORD_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Minimum ${PASSWORD_MIN_LENGTH} characters needed.`
            }
        } else if (password.length > PASSWORD_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Maximum ${PASSWORD_MAX_LENGTH} characters allowed.`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };
        }
    }

}

export default Signup;