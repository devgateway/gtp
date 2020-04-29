/* eslint-disable */
import React, {Component,useState} from 'react';
import {connect} from 'react-redux';
import {injectIntl, FormattedMessage, FormattedHTMLMessage} from 'react-intl';
import {doSubscribe} from '../../redux/ipar/newsLetter'
import messages from '../../translations/messages'
import './newsletter.scss';


const newsLetter = injectIntl(({onSubscribe, intl}) => {
  debugger;
  const [email, setEmail] = useState('');
  const [enabled, setEnabled] = useState(false);

  const handleEmailChange = (event) => {

    event.persist();
    const value= event.target.value;

     if( value && (/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(value))) {
        setEnabled(true)
      }else{
        setEnabled(false)
      }

    setEmail(value);
  }

  const handleSubscription = (event) => {
    //onSubscribe(email)
    return enabled
  }


  console.log(email)
  return (<div className="home-news-letter">

    <div className="news-letter-text">
      <FormattedMessage id="home.newsletter.title" defaultMessage="Subscribe to our newsletter"></FormattedMessage>
    </div>


    <div>


    </div>
    <div className="btn-group">
        <form  onSubmit={handleSubscription} action="https://gmail.us19.list-manage.com/subscribe/post?u=bf5d2580ed54286720f7ebd3c&amp;id=35d5eec81f" method="post" id="mc-embedded-subscribe-form" name="mc-embedded-subscribe-form" class="validate" target="_blank" novalidate>
            <input type="email" onChange={handleEmailChange} value={email} className="input-news-letter-suscribe" name="EMAIL"  id="mce-EMAIL"
             placeholder={intl.formatMessage(messages.home_newsletter_email_place_holder)} required />
            <input className={`btn-news-letter-suscribe ${enabled?'enabled':'disabled'}`}
            type="submit" value={intl.formatMessage(messages.home_newsletter_button_subscribe)} name="subscribe" id="mc-embedded-subscribe" />
            <input type="hidden" s name="b_bf5d2580ed54286720f7ebd3c_35d5eec81f" tabindex="-1" value=""/>
        </form>
    </div>

  </div>);
})



const mapStateToProps = state => {

return {}
}

const mapActionCreators = {
  onSubscribe:doSubscribe
};


export default connect(mapStateToProps, mapActionCreators)(newsLetter);
