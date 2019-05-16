import React, { Component } from 'react';
import { Row, InputGroup } from 'react-bootstrap';
import { Link, withRouter } from 'react-router-dom';
import PageHeader from "../components/PageHeader";
import { WikiLabels } from "../components/Wiki";
import axios from 'axios';
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
            this.setState({
                topics: res.data,
                loading: false
            })
        }).catch(err => {
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
        const topics = this.state.topics;

        return (
            <React.Fragment>

                <PageHeader title="Around the World" />

                <div className="container">
                    <div className="row  mt-5 mb-5">
                        <div className="col-md-12">
                            <InputGroup>
                                <input value={this.state.input} placeholder="Search topics" className="form-control searchInput" type="text" onChange={this.handleSearch} />
                            </InputGroup>
                        </div>
                    </div>
                    <div className="card-columns">
                        {
                            topics.map((topic, topicIndex) => {
                                return (
                                    <div  key={topicIndex}>
                                        <div className="card" style={{ padding: '20px' }}>
                                            <div className="card-bod">
                                                <img src={topic.imageUrl} className="img-fluid mb-2" alt={topic.title} />
                                                <h4>{topic.title}</h4>
                                                <small className="text-left"><strong>by </strong> @{topic.createdBy} {' '}</small>
                                                <hr />
                                                <div className="card-text text-justify">{topic.description}</div>
                                                <WikiLabels wikis={topic.wikiData} />
                                                <hr />
                                                <Link className="btn btn-sm btn-primary fullWidth" to={`/topic/preview/${topic.id}`}>Details</Link>
                                            </div>
                                        </div>
                                    </div>
                                )
                            })
                        }
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

export default withRouter(Glossary);