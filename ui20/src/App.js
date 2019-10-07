import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

import {Provider} from 'react-redux'
import {Route, Switch,Redirect} from 'react-router' // react-router v4/v5
import {ConnectedRouter, ConnectedRoute} from 'connected-react-router/immutable'
import configureStore, {history} from './Store'
import {connect} from 'react-redux';

import messages_fr from "./translations/fr.json";
import messages_en from "./translations/en.json";

import Home from './home/Home'
import {
  IntlProvider,
  FormattedNumber,
  FormattedDate,
  addLocaleData,
  createIntl,
  createIntlCache,
  injectIntl,
  FormattedMessage,
  defineMessages
} from "react-intl";

var areIntlLocalesSupported = require('intl-locales-supported');

debugger;
const messages = {
  'fr': messages_fr,
  'en': messages_en
};

const language = navigator.language.split(/[-_]/)[0]; // language without region code

const IntlRoutes = (props) => {
  debugger;
  return (<IntlProvider locale={language} messages={messages[props.match.params.lan]}>
    <Switch>
        <Route exact="exact" path="/:lan/home" render={() => (<div>

            <Home language={props.match.params.lan}></Home>
        </div>)}/>
      <Route render={() => (<div>Miss</div>)}/>
    </Switch>
  </IntlProvider>)

}


const store = configureStore()
class AppWrapper extends Component {
  render() {
    return (<Provider store={store}>
      <ConnectedRouter history={history}>
          <Switch>
        <Route path="/:lan/" component={IntlRoutes}>

        </Route>
        <Redirect to="/en/home"></Redirect>
      </Switch>
      </ConnectedRouter>
    </Provider>);
  }
}

export default AppWrapper;