import React, { Component } from 'react';
import { API_BASE_URL, REQUEST_HEADERS } from "../constants";
import axios from "axios";
import { Link } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import { WikiLabels } from "../components/Wiki";

class UserEnrolledTopicList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topics: [],
            isLoading: false,
            input: ''
        };
        this.loadUserEnrolledTopics = this.loadUserEnrolledTopics.bind(this);

    }

    loadUserEnrolledTopics() {

        const url = API_BASE_URL + `/topics/enrolled/${this.props.currentUser.id}`;

        axios.get(url, REQUEST_HEADERS).then(res => {

            this.setState({
                topics: res.data,
                isLoading: false
            })
        }).catch(err => {
            this.setState({ isLoading: false })
        });
    }

    componentDidMount() {
        this.loadUserEnrolledTopics()
    }

    render() {

        const topics = this.state.topics;

        return (
            <React.Fragment>
                <PageHeader title="Enrolled Topics" />

                <div className="container">

                    <div className="card-columns mt-5">
                        {
                            topics.map((topic, topicIndex) => {
                                return (
                                    <div  key={topicIndex}>
                                        <div className="card" style={{ padding: '20px' }}>
                                            <div className="card-bod">
                                                <img src={topic.imageUrl} className="img-fluid mb-2" alt={topic.title} />
                                                <h4>{topic.title}</h4>
                                                <div className="topicCaption">{topic.description}</div>
                                                <WikiLabels wikis={topic.wikiData} />
                                                <hr />
                                                <Link className="btn btn-sm btn-outline-primary" to={`/topic/view/${topic.id}`}>Details</Link>
                                            </div>
                                        </div>
                                    </div>
                                )
                            })
                        }
                    </div>
                </div>
            </React.Fragment>

        )
    }
}

export default UserEnrolledTopicList;