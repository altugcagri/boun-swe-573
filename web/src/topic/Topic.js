import React, { Component } from 'react';
import { REQUEST_HEADERS } from "../constants";
import axios from "axios";
import toast from "toasted-notes";
import { Row, Tab, Button } from "react-bootstrap";
import { Link, withRouter } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPlus, faEdit,faEye, faCheck, faCogs } from '@fortawesome/free-solid-svg-icons'
import PageHeader from "../components/PageHeader";
import { PathNavigator, PathTabs } from "../components/LearningPath";
import { resolveEndpoint } from "../util/Helpers";
import Loading from '../components/Loading';
import { WikiLabels } from "../components/Wiki";

class Topic extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topic: {
                contentList: []
            },
            activeTab: '',
            loading: true,
            achieved: false
        };
        this.togglePublish = this.togglePublish.bind(this);
        this.setAchieved = this.setAchieved.bind(this);
    }

    setAchieved(status) {
        this.setState({ achieved: status })
    }

    loadTopicById() {
        let url = resolveEndpoint('getTopicById', [{ "slug1": this.props.match.params.topicId }]);
        axios.get(url, REQUEST_HEADERS)
        .then(res => {
            this.setAchieved(true);
            for (let k = 0; k < res.data.contentList.length; k++) {
                let url = resolveEndpoint('getQuestionsByContentId', [{ "slug1": res.data.contentList[k].id }]);

                axios.get(url, REQUEST_HEADERS)
                .then(res => {
                    for (let i = 0; i < res.data.questions.length; i++) {
                        if (res.data.questions[i].userAnswer === null) {
                            this.setAchieved(false);
                        }
                    }
                    this.setState({ content: res.data, loading: false })
                }).catch(err => {
                    toast.notify("Something went wrong!", { position: "top-right" });
                    console.log(err)
                });
            }
            this.setState({
                topic: res.data,
                activeTab: res.data.contentList.length > 0 ? res.data.contentList[0].id : '',
                loading: false
            })



        }).catch(err => {
            toast.notify("Something went wrong!", { position: "top-right" });
            console.log(err)
        });


    }

    togglePublish(topicId, publish) {

        let url = resolveEndpoint('toggleTopicPublish', []);
        let reqObj = {
            topicId: topicId,
            publish: publish
        }

        this.setState({ loading: true })

        axios.post(url, reqObj, REQUEST_HEADERS)
            .then(res => {
                this.setState({ loading: false })
                toast.notify("Status changed.", { position: "top-right" });
                this.loadTopicById()
            }).catch(err => {
                this.setState({ loading: false })
                toast.notify(<span className="text-danger">{err.response.data.message}</span>, { position: "top-right" });
            });
    }

    componentDidMount() {
        this.loadTopicById();
    }


    render() {

        const { topic, activeTab, loading,achieved } = this.state;
        const { editable } = this.props

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <PageHeader title={topic.title}>
                            {editable ? (
                                <Link to={`/${this.props.currentUser.username}/topics/created`} className="breadcrumbLink">
                                    <span>My Topics</span>
                                </Link>
                            ) : (
                                    <Link to={`/explore`} className="breadcrumbLink">
                                        <span>Explore</span>
                                    </Link>
                                )}
                        </PageHeader>

                        {
                            editable && (
                                <Button
                                    className="btn btn-success fullWidth"
                                    variant={topic.published ? 'danger' : 'warning'}
                                    onClick={() => this.togglePublish(topic.id, !topic.published)}>
                                    {topic.published ? 'Unpublish' : 'Publish This Topic'}
                                </Button>
                            )
                        }

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
                                        {
                                            !editable && (
                                                <React.Fragment>
                                                    {
                                                        achieved ? (
                                                            <div>
                                                                <span className="badge badge-pill badge-success font-14">
                                                                    <FontAwesomeIcon icon={faCheck} /> Completed
                                                            </span><br /><br />
                                                            </div>
                                                        ) : (
                                                            <div>
                                                                    <span className="badge badge-pill badge-warning font-14">
                                                                        <FontAwesomeIcon icon={faCogs} /> Ongoing
                                                                </span><br /><br />
                                                            </div>
                                                        )
                                                    }
                                                </React.Fragment>
                                            )
                                        }
                                        <p>
                                            {topic.description}
                                        </p>
                                        <WikiLabels wikis={topic.wikiData} />

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
                                    {editable && (
                                        <Link className="btn btn-success btn-sm ml-2 inlineBtn" to={`/topic/${topic.id}/content`}>
                                            <FontAwesomeIcon icon={faPlus} /> Material
                                </Link>)}

                                </h4>
                            </div>
                        </div>
                        {
                            activeTab && (
                                <Tab.Container id="list-group-tabs-example" defaultActiveKey={activeTab}>
                                    <div className="container mt-5 text-left" >
                                        <Row>
                                            <PathNavigator contents={topic.contentList} linkable={!editable} />
                                            {editable && (
                                                <PathTabs contents={topic.contentList} editable={editable} handleRefresh={() => this.loadTopicById()} />
                                            )}


                                        </Row>
                                    </div>
                                </Tab.Container>
                            )
                        }
                    </React.Fragment>
                )}
            </React.Fragment>
        )
    }
}

export default withRouter(Topic);