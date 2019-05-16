import React, { Component } from 'react';
import { REQUEST_HEADERS } from "../constants";
import axios from "axios";
import { Link, withRouter } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronRight } from '@fortawesome/free-solid-svg-icons'
import PageHeader from "../components/PageHeader";
import { resolveEndpoint } from "../util/Helpers";
import Loading from '../components/Loading';

class ViewContent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            content: false,
            loading: true
        };
        this.loadContentById = this.loadContentById.bind(this);
    }


    loadContentById() {
        let url = resolveEndpoint('getContentById', [{ "slug1": this.props.match.params.contentId }]);

        axios.get(url, REQUEST_HEADERS)
            .then(res => {
                this.setState({ content: res.data, loading: false })
            }).catch(err => {
                console.log(err)
            });
    }

    componentDidMount() {
        this.loadContentById();
    }


    render() {

        const { content, loading } = this.state;

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <PageHeader title="Content Quiz">
                            <Link to={`/topic/view/${content.topicId}`} className="breadcrumbLink">
                                <span>!! TOPIC NAME !!</span>
                            </Link>
                        </PageHeader>

                        {
                            content && (
                                <div className="bg-alt sectionPadding text-left">
                                    <div className="container">
                                        <div className="row">
                                            <div className="col-md-12">
                                                <h4 className="mb-4"><strong>{content.title}</strong></h4>
                                                <div className="text-left" dangerouslySetInnerHTML={{ __html: content.text }} ></div>
                                                <div className="text-right mt-5">
                                                    <hr />
                                                    <Link className="btn btn-success btn-sm ml-2 inlineBtn" to={`/content/${content.id}/quiz`}><FontAwesomeIcon icon={faChevronRight} /> Start Section Quiz</Link>

                                                </div>
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

export default withRouter(ViewContent);