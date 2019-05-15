import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Nav, Navbar, NavDropdown, Container } from "react-bootstrap";

class AppHeader extends Component {

    constructor(props) {
        super(props);
        this.toggle = this.toggle.bind(this);
        this.state = {
            isOpen: false
        };
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    render() {
        let menuItems;
        if (!this.props.currentUser) {
            menuItems =
                <Nav className="ml-auto">
                    <Nav.Link as={Link} className="ml-2" to="/explore">Around the World</Nav.Link>
                    <Nav.Link as={Link} className="ml-2" to="/login">Login</Nav.Link>
                    <Nav.Link as={Link} className="ml-2 btn btn-sm btn-outline-primary" to="/signup">Sign Up</Nav.Link>
                </Nav>
        } else {
            menuItems =
                <Nav className="ml-auto ">
                    <Nav.Link className="ml-2" as={Link} to="/explore">Around the World</Nav.Link>
                    <NavDropdown title={this.props.currentUser.username} id="basic-nav-dropdown">
                        {/* <NavDropdown.Item as={Link} to={`/${this.props.currentUser.username}`}>Profile</NavDropdown.Item> */}
                        <NavDropdown.Item as={Link} to={`/${this.props.currentUser.username}/topics/created`}>Created Topics</NavDropdown.Item>
                        <NavDropdown.Item as={Link} to={`/${this.props.currentUser.username}/topics/enrolled`}>Enrolled Topics</NavDropdown.Item>
                        <NavDropdown.Item as={Link} onClick={this.props.onLogout} to="/" >Logout</NavDropdown.Item>
                    </NavDropdown>
                </Nav>
        }

        return (
            <Navbar bg="light" className="myNavbar" variant="dark" expand="lg">
                <Container>
                    <Navbar.Brand>
                        <Link to="/" style={{ textDecoration: 'none', color: 'black', fontWeight: '400' }}>
                            <strong style={{ fontWeight: '700' }}>SMEP</strong>
                        </Link>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        {menuItems}
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        );
    }
}

export default withRouter(AppHeader);