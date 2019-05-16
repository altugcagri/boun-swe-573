import React, { Component } from 'react';
import { API_BASE_URL, REQUEST_HEADERS } from "../constants";
import axios from "axios";
import { Row, Tab } from "react-bootstrap";
import { Link, withRouter } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPlus, faEdit } from '@fortawesome/free-solid-svg-icons'
import PageHeader from "../components/PageHeader";
import { Question } from "../components/LearningPath";

class ContentQuiz extends Component {
    constructor(props) {
        super(props);
        this.state = {
            content: false,
        };
        this.loadContentById = this.loadContentById.bind(this);
    }


    loadContentById() {
        // let url = API_BASE_URL + `/contents/${this.props.match.params.contentId}`;
        let url = `/dummy/content.json`;

        axios.get(url, REQUEST_HEADERS)
            .then(res => {
                this.setState({ content: res.data })
            }).catch(err => {
                this.setState({ isLoading: false })
            });
    }

    componentDidMount() {
        this.loadContentById();
    }


    render() {

        const { content } = this.state;
        const { editable } = this.props

        return (
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
                                        <h4 className="mb-4">Quiz: <strong>{content.title}</strong>

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
                                                                handleRefresh={() => this.loadContentById}
                                                                answered={false}
                                                            />
                                                        )
                                                    })
                                                }
                                            </React.Fragment>
                                        )}

                                    </div>
                                </div>
                            </div>
                        </div>
                    )
                }



            </React.Fragment>
        )
    }
}

export default withRouter(ContentQuiz);