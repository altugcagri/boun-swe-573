import React, { Component } from 'react';
import {Row, InputGroup, Button} from 'react-bootstrap';
import { Link, withRouter } from 'react-router-dom';
import PageHeader from "../components/PageHeader";
import { WikiLabels } from "../components/Wiki";
import axios from 'axios';
import toast from "toasted-notes";
import { resolveEndpoint } from "../util/Helpers";
import Loading from '../components/Loading';

class Glossary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            topics: [],
            input: '',
            loading: true
        };
        this.loadTopicList = this.loadTopicList.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
    }

    loadTopicList() {
        let url = resolveEndpoint('getAllTopics', []);

        axios.get(url).then(res => {
            if (this.props.currentUser) {
                let filteredTopics = res.data.filter(
                    obj => obj.createdBy !== this.props.currentUser.id
                )
                this.setState({
                    topics: filteredTopics,
                    loading: false
                })
            } else {
                this.setState({
                    topics: res.data,
                    loading: false
                })
            }

        }).catch(err => {
            toast.notify("Something went wrong!", { position: "top-right" });
            console.log(err)
        });
    }

    handleSearch(e) {
        this.setState({
            input: e.target.value,
        })
    }

    componentDidMount() {

        this.loadTopicList();
    }

    render() {
        const { topics, input, loading } = this.state;

        return (
            <React.Fragment>
                {loading ? <Loading /> : (
                    <React.Fragment>
                        <PageHeader title="Explore" />
                        <div className="container">
                            <div className="row  mt-5 mb-5">
                                <div className="col-md-12">
                                    <InputGroup>
                                        <input value={input} placeholder="Search topics" className="form-control searchInput" type="text" onChange={this.handleSearch} />
                                    </InputGroup>
                                </div>
                            </div>
                            {
                                topics.length === 0 && (<div className="mt-5 text-center">Nothing to show</div>)
                            }
                            <div className="card-columns">
                            {topics.filter(topic => input === '' || topic.title.toLowerCase().indexOf(input) > -1).map((topic, topicIndex) => {
                                    return (
                                        <div key={topicIndex}>
                                            <div className="card" style={{ padding: '20px' }}>
                                                <div className="card-bod">
                                                    <img src={topic.imageUrl} className="img-fluid mb-2" alt={topic.title} />
                                                    <h4>{topic.title}</h4>
                                                    <small className="text-left"><strong>by </strong> @ {topic.createdByName} {' '}</small>
                                                    <hr />
                                                    <div className="topicCaption">{topic.description}</div>
                                                    <hr />
                                                    <WikiLabels wikis={topic.wikiData} />
                                                    <hr />
                                                    <Link className="btn btn-sm btn-outline-primary fullWidth" to={`/topic/preview/${topic.id}`}>Details</Link>
                                                </div>
                                            </div>
                                        </div>
                                    )
                                })}
                            </div>
                        </div>
                    </React.Fragment>
                )}
            </React.Fragment>
        )
    }
}

export default withRouter(Glossary);