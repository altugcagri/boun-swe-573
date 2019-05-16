import React, {Component} from 'react';
import {ACCESS_TOKEN, API_BASE_URL, REQUEST_HEADERS } from "../constants";
import axios from "axios";
import {ErrorMessage, Field, Form, Formik} from 'formik';
import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import {createContent} from "../util/APIUtils";
import toast from "toasted-notes";
import EditorField from '../components/EditorField'

class EditContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            content: false,
        };
        this.loadContentById = this.loadContentById.bind(this);

    }

    loadContentById() {
        let url = API_BASE_URL + `/contents/${this.props.match.params.contentId}`;

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
        const props = this.props;
        const vm = this.state;
        return (
            vm.content && (

                <div>
                    <div className="pageHeader text-left">
                        <div className="container">
                            <div className="row">
                                <div className="col-md-12">
                                    <Link to={`/topic/${vm.content.topicId}`}>Back</Link> / Edit Material
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="sectionPadding">
                        <div className="container text-left ">
                            <div className="row">
                                <div className="col-md-3">
                                    <h4 style={{ fontSize: '20px' }}>Things to <strong>Consider</strong></h4>
                                    <hr />
                                    <p style={{ fontSize: '14px', textAlign: 'justify' }}>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Neque ipsam ut consectetur vel excepturi alias laboriosam totam
                    fuga reprehenderit officiis, sed aliquam accusamus repellat laborum! Fuga cupiditate porro exercitationem quod.</p>
                                </div>
                                <div className="col-md-8 offset-md-1">
                                    <Formik
                                        initialValues={{ title: vm.content.title, text: vm.content.text }}
                                        validate={values => {
                                            let errors = {};

                                            if (!values.title) {
                                                errors.title = 'Content Title is required';
                                            }

                                            if (!values.text) {
                                                errors.text = 'Content Text is required';
                                            }

                                            return errors;
                                        }}
                                        onSubmit={(values, { setSubmitting }) => {
                                            setTimeout(() => {

                                                let topicId = vm.content.topicId;
                                                const newContent = {
                                                    id: vm.content.id,
                                                    topicId: topicId,
                                                    title: values.title,
                                                    text: values.text
                                                };

                                                createContent(newContent)
                                                    .then(res => {
                                                        toast.notify("Content updated successfully.", { position: "top-right" });
                                                        props.history.push(`/topic/${topicId}`);
                                                    }).catch(err => {
                                                        toast.notify("Topic does not exist!", { position: "top-right" });
                                                    });

                                                setSubmitting(false);
                                            }, 400);
                                        }}
                                    >
                                        {({ isSubmitting }) => (
                                            <Form>
                                                <div className="form-group row text-left">
                                                    <label htmlFor="contentTitle" className="col-sm-12 col-form-label">Material <strong>Title</strong></label>
                                                    <div className="col-sm-12">
                                                        <Field type="text" name="title" id="contentTitle" placeholder="content title" className="form-control" />
                                                        <ErrorMessage name="contentTitle" component="div" />
                                                    </div>
                                                </div>

                                                <div className="form-group row text-left">
                                                    <label htmlFor="contentText" className="col-sm-12 col-form-label">Material <strong>Body</strong> </label>
                                                    <div className="col-sm-12">
                                                        <Field name="text" component={EditorField} placeholder="Enter Content" row="20" />
                                                        <ErrorMessage name="contentText" component="div" />
                                                    </div>
                                                </div>

                                                <Button variant="success" type="submit" block disabled={isSubmitting}>Save</Button>
                                            </Form>
                                        )}
                                    </Formik>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            )
        )
    };

}

export default EditContent;