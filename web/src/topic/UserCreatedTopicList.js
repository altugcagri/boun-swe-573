import React, { Component } from 'react';
import { REQUEST_HEADERS } from "../constants";
import axios from "axios";
import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPlus } from '@fortawesome/free-solid-svg-icons'
import PageHeader from "../components/PageHeader";
import { WikiLabels } from "../components/Wiki";
import { resolveEndpoint } from "../util/Helpers";
import Loading from '../components/Loading';

class UserCreatedTopicList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topics: [],
            input: '',
            loading: true
        };
        this.loadUserCreatedTopics = this.loadUserCreatedTopics.bind(this);
        this.handleDeleteTopicById = this.handleDeleteTopicById.bind(this);
    }

    loadUserCreatedTopics() {
        let url = resolveEndpoint('getTopicsByUserId', [{ "slug1": this.props.currentUser.username }]);

        axios.get(url, REQUEST_HEADERS).then(res => {
            this.setState({
                topics: res.data,
                loading: false
            })
        }).catch(err => {
            console.log(err)
        });
    }

    handleDeleteTopicById(topicIdToDelete) {
        let url = resolveEndpoint('deleteTopicById', [{ "slug1": topicIdToDelete }]);

        axios.delete(url, REQUEST_HEADERS)
            .then(res => {
                this.loadUserCreatedTopics()
            }).catch(err => {
                console.log(err)
            });

    }

    componentDidMount() {
        this.loadUserCreatedTopics()
    }

    render() {

        const { topics, loading } = this.state;

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <PageHeader title="My Topics" />

                        <div className="container">
                            <div className="row mt-5">
                                <div className="col-md-12">
                                    <Link to="/topic/new" className="btn btn-success">
                                        <FontAwesomeIcon icon={faPlus} /> Create a Topic
                            </Link>
                                </div>
                            </div>

                            <div className="row mt-5">
                                {
                                    topics.map((topic, topicIndex) => {
                                        return (
                                            <div className="col-md-4" key={topicIndex}>
                                                <div className="card" style={{ padding: '20px' }}>
                                                    <div className="card-bod">
                                                        <img src={topic.imageUrl} className="img-fluid mb-2" alt={topic.title} />
                                                        <h4>{topic.title}</h4>
                                                        <div className="topicCaption">{topic.description}</div>
                                                        <WikiLabels wikis={topic.wikiData} />
                                                        <hr />
                                                        <Link className="btn btn-sm btn-outline-primary" to={`/topic/${topic.id}`}>Details</Link>
                                                        <Button className="ml-2 btn-sm" variant="outline-danger" onClick={() => this.handleDeleteTopicById(topic.id)}>Delete</Button>
                                                    </div>
                                                </div>
                                            </div>
                                        )
                                    })
                                }
                            </div>
                        </div>
                    </React.Fragment>)

                }
            </React.Fragment>

        )
    }
}

export default UserCreatedTopicList;