import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { Button } from "react-bootstrap";
import { createContent } from "../util/APIUtils";
import toast from "toasted-notes";
import EditorField from '../components/EditorField'

const AddContent = (props) => (
    <div>
        <div className="pageHeader text-left">
            <div className="container">
                <div className="row">
                    <div className="col-md-12">
                        Add Material
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
                            initialValues={{ title: '', text: '' }}
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

                                    let topicId = props.match.params.topicId;
                                    const newContent = {
                                        topicId: topicId,
                                        title: values.title,
                                        text: values.text
                                    };

                                    createContent(newContent, topicId)
                                        .then(res => {
                                            toast.notify("Content created successfully.", { position: "top-right" });
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
);

export default AddContent;