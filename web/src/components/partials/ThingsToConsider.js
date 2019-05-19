import React, { Component } from 'react';

export default class WikiLabel extends Component {
    render() {
        return (
            <div className="col-md-3">
                <h4 style={{ fontSize: '20px' }}><strong>How to create a great Learning Path for Others</strong></h4>
                <hr />
                <p style={{ fontSize: '14px', textAlign: 'justify' }}>
                  In order to create a topic you have to fill all the Title, Main Image Url, Description and Keyword areas.<br />
                  You can search any label from Keyword area. According to your search, some labels will be ordered. You can select or unselect the labels as you wish.<br />
                  Labels which direct you to WikiData contents will be added.<br />
                  After filling the areas you can save the topic. <br /><br />
                  After saving the Topic you can build your own Learning Path by simply adding material.<br />
                  You should fill Material Title, and Material Body. You can also add images to material body. Feel free!<br />
                  After adding content, you can add questions and their answers easily.<br /><br />
                  You can save your topics and related contents any time you like.<br />
                  In order to make your topic can be seen by others you have to publish your topic.<br />
                  But remember, every topic has to have at least one content, and every content must have at least one question to be published<br />

                </p>
                <br />
                <strong>Example Image Url:</strong>
                <br />
                https://picsum.photos/id/498/1920/800
            </div>
        )

    }
}

