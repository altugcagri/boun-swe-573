import React, { Component } from 'react';
import { REQUEST_HEADERS } from "../constants";
import axios from "axios";
import toast from "toasted-notes";
import { Link, withRouter } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import { Question } from "../components/LearningPath";
import { resolveEndpoint } from "../util/Helpers";
import Loading from '../components/Loading';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronRight, faCheck } from '@fortawesome/free-solid-svg-icons'

class ContentQuiz extends Component {
    constructor(props) {
        super(props);
        this.state = {
            content: false,
            loading: true
        };
        this.loadContentById = this.loadContentById.bind(this);
    }


    loadContentById() {
        let url = resolveEndpoint('getQuestionsByContentId', [{ "slug1": this.props.match.params.contentId }]);

        axios.get(url, REQUEST_HEADERS)
            .then(res => {
                this.setState({ content: res.data, loading: false })
            }).catch(err => {
                toast.notify("Something went wrong!", { position: "top-right" });
                console.log(err)
            });
    }

    componentDidMount() {
        this.loadContentById();
    }


    render() {

        const { content, loading } = this.state;
        const { editable } = this.props

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <PageHeader title="Content Quiz">
                            <Link to={`/topic/view/${content.topicId}`} className="breadcrumbLink">
                                <span>{content.topicTitle}</span>
                            </Link>
                        </PageHeader>

                        {
                            content && (
                                <div className="bg-alt sectionPadding text-left">
                                    <div className="container">
                                        <div className="row">
                                            <div className="col-md-8">
                                                <h4 className="mb-4">Quiz: <strong>{content.contentTitle}</strong>

                                                </h4>
                                            </div>
                                            <div className="col-md-12">
                                                {content.questions.length > 0 && (
                                                    <React.Fragment>
                                                        <hr />
                                                        {
                                                            content.questions.map((question, idx) => {
                                                                return (
                                                                    <Question
                                                                        key={idx}
                                                                        order={idx + 1}
                                                                        question={question}
                                                                        editable={editable}
                                                                        answered={question.userAnswer && (question.userAnswer.id ? true : false)}
                                                                    />
                                                                )
                                                            })
                                                        }
                                                    </React.Fragment>
                                                )}
                                                {content.nextContentId === null ? (
                                                    <div className="text-right mt-5">

                                                        <Link className="btn btn-success btn-sm ml-2 inlineBtn" to={`/topic/view/${content.topicId}`}><FontAwesomeIcon icon={faCheck} /> Finalize</Link>
                                                    </div>
                                                ) : (
                                                        <div className="text-right mt-5">

                                                            <Link className="btn btn-success btn-sm ml-2 inlineBtn" to={`/content/view/${content.nextContentId}`}><FontAwesomeIcon icon={faChevronRight} /> Start Next Content</Link>
                                                        </div>
                                                    )}

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )
                        }
                    </React.Fragment>
                )}
            </React.Fragment>
        )
    }
}

export default withRouter(ContentQuiz);