import React, { Component } from 'react';
import { API_BASE_URL, REQUEST_HEADERS } from "../constants";
import axios from "axios";
import { Row, Tab, Button } from "react-bootstrap";
import { Link, withRouter } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEdit } from '@fortawesome/free-solid-svg-icons'
import PageHeader from "../components/PageHeader";
import toast from "toasted-notes";
import { PathNavigator } from "../components/LearningPath";

class TopicPreview extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topic: {
                contentList: []
            },
            enrolled: [],
            activeTab: ''
        };
        this.loadTopicById = this.loadTopicById.bind(this);
        this.getEnrolledTopicsByUserId = this.getEnrolledTopicsByUserId.bind(this);
        this.search = this.search.bind(this);
    }

    enrollUserToTopic(topicId) {

        const reqObj = {
            topicId: topicId,
            username: this.props.currentUser.username
        };

        const url = API_BASE_URL + `/topics/enroll/`;
        axios.post(url, reqObj, REQUEST_HEADERS)
            .then(res => {
                toast.notify("Enrolled successfully.", { position: "top-right" });
                this.props.history.push(`/topic/view/${topicId}`)
            }).catch(err => {
                console.log(err)
            });
    }

    loadTopicById() {
        const url = API_BASE_URL + `/topics/topic/${this.props.match.params.topicId}`;

        axios.get(url, REQUEST_HEADERS)
            .then(res => {
                this.setState({
                    topic: res.data,
                    activeTab: res.data.contentList.length > 0 ? res.data.contentList[0].id : ''
                })
            }).catch(err => {
                console.log(err)
            });
    }

    getEnrolledTopicsByUserId() {
        const url = API_BASE_URL + `/topics/enrolled/${this.props.currentUser.id}`;

        axios.get(url, REQUEST_HEADERS)
            .then(res => {
                this.setState({
                    enrolled: res.data
                })
                this.resolveEnrollment()
            }).catch(err => {
                console.log(err)
            });
    }

    componentDidMount() {
        this.loadTopicById();
        this.getEnrolledTopicsByUserId();
    }

    search(topicId, enrolled) {
        for (var i = 0; i < enrolled.length; i++) {
            if (enrolled[i].id === topicId) {
                return true;
            }
        }
    }

    resolveEnrollment() {
        const { topic, enrolled } = this.state;
        const result = this.search(topic.id, enrolled);
        if (result === true) {
            toast.notify("Welcome back!", { position: "top-right" });
            this.props.history.push(`/topic/view/${topic.id}`)
        }
    }

    render() {

        const { topic, activeTab } = this.state;
        const { editable } = this.props

        return (
            <React.Fragment>
                <PageHeader title="Details">
                    <Link to={`/explore`} className="breadcrumbLink">
                        <span>Explore</span>
                    </Link>
                </PageHeader>

                <Button
                    className="btn btn-success fullWidth"
                    variant="primary"
                    onClick={() => this.enrollUserToTopic(topic.id)}>
                    Enroll To This Topic
                </Button>

                <div className="bg-alt sectionPadding text-left">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8">
                                <h4 className="mb-4">Explore <strong>{topic.title}</strong>
                                    {editable && (
                                        <Link className="btn btn-outline-primary btn-sm ml-2 inlineBtn" to={`/topic/${topic.id}/edit`}>
                                            <FontAwesomeIcon icon={faEdit} />
                                        </Link>
                                    )}
                                </h4>
                                <p>
                                    {topic.description}
                                </p>
                            </div>
                            <div className="col-md-4">
                                <img src={topic.imageUrl} className="img-fluid" alt={topic.title} />
                            </div>
                        </div>
                    </div>
                </div>

                <div className="container mt-5">
                    <div className="row col-md-12 text-left">
                        <h4>
                            Learning <strong>Path</strong>
                        </h4>
                    </div>
                </div>
                {
                    activeTab && (
                        <Tab.Container id="list-group-tabs-example" defaultActiveKey={activeTab}>
                            <div className="container mt-5 text-left" >
                                <Row>
                                    <PathNavigator contents={topic.contentList} />


                                </Row>
                            </div>
                        </Tab.Container>
                    )
                }

            </React.Fragment>
        )
    }
}

export default withRouter(TopicPreview);