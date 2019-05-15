import React, { Component } from 'react';
import { API_BASE_URL, REQUEST_HEADERS } from "../constants";
import axios from "axios";
import { Col, Row, Tab, Button } from "react-bootstrap";
import { Link, withRouter } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPlus, faTrash, faEdit } from '@fortawesome/free-solid-svg-icons'
import toast from "toasted-notes";
import PageHeader from "../components/PageHeader";
import { PathNavigator } from "../components/LearningPath";
import QuestionModal from "../components/QuestionModal";
import OptionModal from "../components/OptionModal";

class Topic extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topic: {
                contentList: []
            },
            activeTab: '',
            isLoading: false,

        };
        this.loadTopicById = this.loadTopicById.bind(this);
        this.handleDeleteQuestionById = this.handleDeleteQuestionById.bind(this)
        this.handleDeleteContentById = this.handleDeleteContentById.bind(this)
    }


    handleDeleteQuestionById(questionIdToDelete) {
        const url = API_BASE_URL + `/questions/${questionIdToDelete}`;

        axios.delete(url, REQUEST_HEADERS)
            .then(res => {
                toast.notify("Question deleted successfully.", { position: "top-right" });
                this.loadTopicById()
            }).catch(err => {
                console.log(err)
            });
    }

    handleDeleteContentById(contentIdToDelete) {
        const url = API_BASE_URL + `/contents/${contentIdToDelete}`;
        axios.delete(url, REQUEST_HEADERS)
            .then(res => {
                toast.notify("Material deleted successfully.", { position: "top-right" });
                this.loadTopicById()
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

    componentDidMount() {
        this.loadTopicById();
    }


    render() {

        const { topic, activeTab } = this.state;

        return (
            <React.Fragment>
                <PageHeader title="Details">
                    <Link to={`/${this.props.currentUser.username}/topics/created`} className="breadcrumbLink">
                        <span>Created Topics</span>
                    </Link>
                </PageHeader>

                <div className="bg-alt sectionPadding text-left">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8">
                                <h4 className="mb-4">Explore <strong>{topic.title}</strong> <Link className="btn  btn-outline-primary btn-sm ml-2" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-3px' }} to={`/topic/${topic.id}/edit`}><FontAwesomeIcon icon={faEdit} /></Link></h4>

                                <p>
                                    {topic.description}
                                </p>
                            </div>
                            <div className="col-md-4">
                                <img src={topic.imageUrl} className="img-fluid" alt="" />
                            </div>
                        </div>
                    </div>
                </div>

                <React.Fragment>
                    <div className="container mt-5">
                        <div className="row col-md-12 text-left">
                            <h4>
                                Learning <strong>Path</strong>
                                <Link className="btn btn-success btn-sm ml-2" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-7px' }} to={`/topic/${topic.id}/content`}><FontAwesomeIcon icon={faPlus} /> Material</Link>
                            </h4>
                        </div>
                    </div>
                    {
                        activeTab && (
                            <Tab.Container id="list-group-tabs-example" defaultActiveKey={activeTab}>
                                <div className="container mt-5 text-left" >
                                    <Row>
                                        <PathNavigator contents={topic.contentList} />

                                        <Col sm={9}>
                                            <Tab.Content>
                                                {topic.contentList.map((content, contentId) => {
                                                    const questions = content.questionList
                                                    return (
                                                        <Tab.Pane key={contentId} eventKey={content.id}>
                                                            <div className=" bg-alt materialBody">
                                                                <h4 className="mb-4 fontMedium">{content.title}
                                                                    <QuestionModal handleRefresh={() => this.loadTopicById()} contentId={content.id} />
                                                                    <Button className="ml-2 btn-sm" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-3px' }} variant="outline-danger" onClick={() => this.handleDeleteContentById(content.id)}><FontAwesomeIcon icon={faTrash} /></Button>
                                                                    <Link className="btn  btn-outline-primary btn-sm ml-2" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-3px' }} to={`/content/${content.id}`}><FontAwesomeIcon icon={faEdit} /></Link>
                                                                </h4>

                                                                <div className="text-left" dangerouslySetInnerHTML={{ __html: content.text }} ></div>
                                                                {
                                                                    questions.length > 0 && (
                                                                        <React.Fragment>
                                                                            <hr />
                                                                            {
                                                                                questions.map((question, questionId) => {
                                                                                    const choices = question.choiceList
                                                                                    return (
                                                                                        <div key={questionId}>
                                                                                            <p><strong>Q{questionId + 1}:</strong> {question.text}

                                                                                                <OptionModal handleRefresh={() => this.loadTopicById()} questionId={question.id} />
                                                                                                <Button className="ml-2 btn-sm" style={{ paddingTop: '0', paddingBottom: '0', marginTop: '-3px' }} variant="outline-danger" onClick={() => this.handleDeleteQuestionById(question.id)}><FontAwesomeIcon icon={faTrash} /></Button>
                                                                                            </p>

                                                                                            {
                                                                                                choices.length > 0 && (
                                                                                                    <ul>
                                                                                                        {
                                                                                                            choices.map((choice, choiceId) => {
                                                                                                                return (
                                                                                                                    <li key={choiceId} style={{ paddingLeft: '20px' }}>
                                                                                                                        {choice.text}
                                                                                                                        {choice.isCorrect && " (correct)"}
                                                                                                                    </li>
                                                                                                                )
                                                                                                            })
                                                                                                        }
                                                                                                    </ul>
                                                                                                )
                                                                                            }
                                                                                            <hr />
                                                                                        </div>
                                                                                    )
                                                                                })
                                                                            }
                                                                        </React.Fragment>
                                                                    )
                                                                }

                                                            </div>
                                                        </Tab.Pane>
                                                    )
                                                })}
                                            </Tab.Content>
                                        </Col>

                                    </Row>
                                </div>
                            </Tab.Container>
                        )
                    }
                </React.Fragment>

            </React.Fragment>
        )
    }
}

export default withRouter(Topic);