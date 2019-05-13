import React, { Component } from 'react';
import { Col, ListGroup, Tab, Button } from "react-bootstrap";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { createOption } from "../util/APIUtils";
import { Link } from "react-router-dom";
import { faChevronRight, faTrash, faEdit } from '@fortawesome/free-solid-svg-icons'
import QuestionModal from "./QuestionModal";
import OptionModal from "./OptionModal";
import { API_BASE_URL, REQUEST_HEADERS } from "../constants";
import axios from "axios";
import toast from "toasted-notes";

export class PathNavigator extends Component {

    render() {
        const contentList = this.props.contents;
        return (

            <Col sm={3}>
                <ListGroup>
                    {contentList.map((content, contentId) => {
                        return (
                            <ListGroup.Item key={contentId} action eventKey={content.id}>
                                {contentId + 1} - {content.title} <FontAwesomeIcon icon={faChevronRight} />
                            </ListGroup.Item>
                        )
                    })}
                </ListGroup>
            </Col>
        )

    }
}

export class PathTabs extends Component {

    render() {
        const { contents, editable, handleRefresh } = this.props;
        return (
            <Col sm={9}>
                <Tab.Content>
                    {contents.map((content, contentId) => {
                        return (
                            <Tab.Pane key={contentId} eventKey={content.id}>
                                <PathElement
                                    content={content}
                                    questions={content.questionList}
                                    editable={editable}
                                    handleRefresh={handleRefresh}
                                />
                            </Tab.Pane>
                        )
                    })}
                </Tab.Content>
            </Col>
        )
    }
}

export class PathElement extends Component {

    handleDeleteContentById(contentId) {
        const url = API_BASE_URL + `/contents/${contentId}`;
        axios.delete(url, REQUEST_HEADERS)
            .then(res => {
                toast.notify("Material deleted successfully.", { position: "top-right" });
                this.props.handleRefresh()
            }).catch(err => {
                console.log(err)
            });
    }

    render() {
        const { content, questions, editable, handleRefresh } = this.props
        return (
            <div className=" bg-alt materialBody">
                <h4 className="mb-4 fontMedium">
                    {content.title}
                    {editable && (
                        <React.Fragment>
                            <QuestionModal handleRefresh={() => handleRefresh()} contentId={content.id} />
                            <Button className="ml-2 btn-sm inlineBtn" variant="outline-danger" onClick={() => this.handleDeleteContentById(content.id)}><FontAwesomeIcon icon={faTrash} /></Button>
                            <Link className="btn  btn-outline-primary btn-sm ml-2 inlineBtn" to={`/content/${content.id}`}><FontAwesomeIcon icon={faEdit} /></Link>
                        </React.Fragment>
                    )}
                </h4>
                <div className="text-left" dangerouslySetInnerHTML={{ __html: content.text }} ></div>
                {
                    questions.length > 0 && (
                        <React.Fragment>
                            <hr />
                            {
                                questions.map((question, idx) => {
                                    return (
                                        <Question
                                            key={idx}
                                            order={idx + 1}
                                            question={question}
                                            editable={editable}
                                            handleRefresh={handleRefresh}
                                            answered={false}
                                        />
                                    )
                                })
                            }
                        </React.Fragment>
                    )
                }
            </div>
        )
    }
}

export class Question extends Component {
    constructor(props) {
        super(props);
        this.state = {
            disabled: false
        };
    }

    componentDidMount() {
        this.setState({
            disabled: this.props.answered
        })
    }

    handleDeleteQuestionById(questionIdToDelete) {
        const url = API_BASE_URL + `/questions/${questionIdToDelete}`;

        axios.delete(url, REQUEST_HEADERS)
            .then(res => {
                toast.notify("Question deleted successfully.", { position: "top-right" });
                this.props.handleRefresh()
            }).catch(err => {
                console.log(err)
            });
    }

    render() {
        const { question, order, editable, handleRefresh } = this.props
        const { disabled } = this.state;
        return (
            <div id={`questionDiv${question.id}`}>
                <p>
                    <strong>Q{order}:</strong> {question.text}
                    {editable && (
                        <React.Fragment>
                            <OptionModal handleRefresh={() => handleRefresh()} questionId={question.id} />
                            <Button
                                className="ml-2 btn-sm inlineBtn"
                                variant="outline-danger"
                                onClick={() => this.handleDeleteQuestionById(question.id)}>
                                <FontAwesomeIcon icon={faTrash} />
                            </Button>
                        </React.Fragment>
                    )}
                </p>
                {
                    question.choiceList.length > 0 && (

                        <Formik
                            initialValues={{ choice: '' }}
                            validate={values => {
                                let errors = {};
                                if (!values.choice) {
                                    errors.choice = 'You must select a choice';
                                }
                                return errors;
                            }}
                            onSubmit={(values, { setSubmitting }) => {
                                setTimeout(() => {
                                    const newOption = {
                                        choice: values.choice,
                                        question: question.id
                                    };
                                    this.setState({ disabled: true })
                                    console.log(newOption)
                                    /* createOption(newOption)
                                        .then(res => {
                                            toast.notify("Answer given.", { position: "top-right" });

                                        }).catch(err => {
                                            toast.notify("Something went wrong!", { position: "top-right" });
                                        }); */
                                    setSubmitting(false);
                                }, 400);
                            }}
                        >
                            {() => (
                                <Form>
                                    <ul className={!editable ? 'questionUl' : ''}>
                                        {
                                            question.choiceList.map((choice, choiceId) => {
                                                return (
                                                    <li key={choiceId}>
                                                        {!editable &&
                                                            <Field
                                                                type="radio"
                                                                name="choice"
                                                                className="choices"
                                                                disabled={disabled}
                                                                value={choice.id}
                                                            />
                                                        } {choice.text}
                                                        {editable && (
                                                            <span>
                                                                {choice.isCorrect && " (correct)"}
                                                            </span>
                                                        )}


                                                    </li>
                                                )
                                            })
                                        }
                                        <ErrorMessage name="choice" className="errorMessage" component="span" />

                                        {!editable && (
                                            <div className="mt-3 text-right">
                                                <Button
                                                    className="ml-2 btn-sm inlineBtn"
                                                    variant="outline-success"
                                                    type="submit"
                                                    disabled={disabled}
                                                    id={`question${question.id}`} >
                                                    {disabled ? 'Answered' : 'Give Answer'}
                                                </Button>
                                            </div>
                                        )}

                                    </ul>

                                </Form>
                            )}
                        </Formik>
                    )
                }
                <hr />
            </div>

        )
    }
}