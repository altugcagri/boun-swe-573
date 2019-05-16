import React, { Component } from 'react';
import { createTopic } from '../util/APIUtils';
import toast from "toasted-notes";
import wdk from "wikidata-sdk";
import axios from "axios";
import { Row, Form, Col, Button } from "react-bootstrap";
import { Link, withRouter } from "react-router-dom";
import PageHeader from "../components/PageHeader";

class CreateTopic extends Component {
    constructor(props) {
        super(props);
        this.timer = null;
        this.state = {
            title: '',
            description: '',
            imageUrl: '',
            wikiDataSearch: [],
            selectedWikis: []
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleTitleChange = this.handleTitleChange.bind(this);
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
        this.handleKeywordChange = this.handleKeywordChange.bind(this);
        this.handleImageUrlChange = this.handleImageUrlChange.bind(this);
        this.addWiki = this.addWiki.bind(this);
        this.removeWiki = this.removeWiki.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();

        const newTopic = {
            title: this.state.title,
            description: this.state.description,
            wikiData: this.state.selectedWikis,
            imageUrl: this.state.imageUrl
        };

        createTopic(newTopic)
            .then(response => {
                toast.notify("Topic created successfully.", { position: "top-right" });
                this.props.history.push(`/${this.props.currentUser.username}/topics/created`);
            }).catch(error => {
                if (error.status === 401) {
                    this.props.handleLogout();
                } else {
                    toast.notify('Sorry! Something went wrong. Please try again!', { position: "top-right" });
                }
            });

    }

    handleTitleChange(event) {
        const value = event.target.value;
        this.setState({ title: value })
    }

    handleDescriptionChange(event) {
        const value = event.target.value;
        this.setState({ description: value })
    }

    handleImageUrlChange(event) {
        const value = event.target.value;
        this.setState({ imageUrl: value })
    }

    handleKeywordChange(event) {
        clearTimeout(this.timer);

        const value = event.target.value;
        if (value !== '') {
            this.timer = setTimeout(() => {
                const url = wdk.searchEntities(value, 'en', 15, 'json');
                axios.get(url)
                    .then(response => {
                        if (response.data.search.length > 0) {
                            this.setState({ wikiDataSearch: response.data.search })
                            toast.notify("Found in WikiData!", { position: "top-right" })
                        } else {
                            toast.notify("Keyword can not found!", { position: "top-right" });
                        }
                    })
            }, 1000)
        } else {
            this.setState({ wikiDataSearch: [] })
        }
    }

    addWiki(wiki) {
        const newWiki = {
            conceptUri: wiki.concepturi,
            description: wiki.description,
            id: wiki.id,
            label: wiki.label
        }

        const { selectedWikis } = this.state;

        this.setState({
            selectedWikis: selectedWikis.concat(newWiki)
        });
    }

    removeWiki(wikiId) {
        const { selectedWikis } = this.state;

        let filteredWikis = selectedWikis.filter(
            obj => obj.id !== wikiId
        )

        this.setState({
            selectedWikis: filteredWikis
        });

    }

    render() {
        const { wikiDataSearch, selectedWikis } = this.state;

        return (
            <React.Fragment>
                <PageHeader title="Create a Topic">
                    <Link to={`/${this.props.currentUser.username}/topics/created`} className="breadcrumbLink">
                        <span>My Topics</span>
                    </Link>
                </PageHeader>

                <div className="sectionPadding">
                    <div className="container w-90">
                        <div className="row">
                            <div className="col-md-3">
                                <h4 style={{ fontSize: '20px' }}>Things to <strong>Consider</strong></h4>
                                <hr />
                                <p style={{ fontSize: '14px', textAlign: 'justify' }}>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Neque ipsam ut consectetur vel excepturi alias laboriosam totam
                                    fuga reprehenderit officiis, sed aliquam accusamus repellat laborum! Fuga cupiditate porro exercitationem quod.</p>
                            </div>
                            <div className="col-md-8 offset-md-1">
                                <Form onSubmit={this.handleSubmit}>
                                    <Form.Group className="row" >
                                        <Form.Label column sm="12">
                                            Title
                                        </Form.Label>
                                        <Col sm="12">
                                            <Form.Control
                                                type="text"
                                                placeholder="Topic Title"
                                                onChange={this.handleTitleChange}
                                            />
                                        </Col>
                                    </Form.Group>

                                    <Form.Group className="row" >
                                        <Form.Label column sm="12">
                                            Main Image Url
                                        </Form.Label>
                                        <Col sm="12">
                                            <Form.Control
                                                type="text"
                                                placeholder="Enter Image URL"
                                                onChange={this.handleImageUrlChange}
                                            />
                                        </Col>
                                    </Form.Group>

                                    <Form.Group className="row" >
                                        <Form.Label column sm="12">
                                            Description
                                        </Form.Label>
                                        <Col sm="12">
                                            <Form.Control
                                                as="textarea"
                                                rows="5"
                                                placeholder="Short Description"
                                                onChange={this.handleDescriptionChange}
                                            />
                                        </Col>
                                    </Form.Group>

                                    {selectedWikis.length > 0 && (
                                        <div>
                                            Added Wiki:
                                            <ul>
                                                {selectedWikis.map((wiki, idx) => {
                                                    return (
                                                        <li key={idx}>
                                                            {wiki.label} - {wiki.description} <span onClick={() => this.removeWiki(wiki.id)} className="ml-2 removeWikiLabel badge badge-pill badge-danger">Remove</span>
                                                        </li>
                                                    )
                                                })}

                                            </ul>
                                        </div>
                                    )}


                                    <Form.Group className="row"  >
                                        <Form.Label column sm="12">
                                            Keyword
                                        </Form.Label>
                                        <Col sm="12">
                                            <Form.Control
                                                type="text"
                                                placeholder="Wiki Keywords"
                                                onChange={this.handleKeywordChange}
                                            />
                                        </Col>
                                    </Form.Group>

                                    {wikiDataSearch.length > 0 && (
                                        wikiDataSearch.map((wiki, wikiIndex) => {
                                            return (
                                                <Row key={wikiIndex} className="border-bottom border-info p-1 m-1">
                                                    {wiki.description && (
                                                        <React.Fragment>
                                                            <Col md="1">
                                                                <Form.Check
                                                                    onChange={() => this.addWiki(wiki)}
                                                                    type="checkbox"
                                                                    id="default-checkbox"
                                                                    value={wiki}
                                                                />
                                                            </Col>
                                                            <Col md="9">{wiki.description}</Col>
                                                            <Col md="2">
                                                                <a href={wiki.concepturi} target="_blank" rel="noopener noreferrer">Visit</a>
                                                            </Col>
                                                        </React.Fragment>
                                                    )}
                                                </Row>
                                            )
                                        })
                                    )}

                                    <Button className="mt-4" variant="success" type="submit" block>
                                        Create Topic
                                    </Button>
                                </Form>
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default withRouter(CreateTopic);