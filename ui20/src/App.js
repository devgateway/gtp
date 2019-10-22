import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

import {Provider} from 'react-redux'
import {Route, Switch, Redirect} from 'react-router' // react-router v4/v5
import {ConnectedRouter, ConnectedRoute} from 'connected-react-router/immutable'
import configureStore, {history} from './Store'
import {connect} from 'react-redux';
import messages_fr from "./translations/fr.json";
import messages_en from "./translations/en.json";

import {loadDataItems} from './modules/Data'
import Home from './home/Home'
import Analytic from "./analytic/Analytic"

import Header from './layout/Header'
import Footer from './layout/Footer'

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

const messages = {
  'fr': messages_fr,
  'en': messages_en
};

const language = navigator.language.split(/[-_]/)[0]; // language without region code




const IntlRoutes = (props) => {
  return (<IntlProvider locale={language} messages={messages[props.match.params.lan]}>
    <Switch>
      <Route exact path="/:lan/home" render={() => (<div>
          <Home language={props.match.params.lan}></Home>
        </div>)}/>
      <Route exact  path="/:lan/analytic/production" render={() => (<div>
          <Analytic language={props.match.params.lan}></Analytic>
        </div>)}/>
      <Route exact path="/:lan/analytic/marketPrice" render={() => (<div>
          <Analytic language={props.match.params.lan}></Analytic>
        </div>)}/>
      <Route exact  path="/:lan/analytic/consumption" render={() => (<div>
          <Analytic language={props.match.params.lan}></Analytic>
        </div>)}/>

      <Route render={() => (<div className="not-found">Page Not Found</div>)}/>
    </Switch>
  </IntlProvider>)

}

const MainRoutes=(props) => {
  return (<ConnectedRouter history={history}>
    <Switch>
      <Route path="/:lan/" component={IntlRoutes}></Route>
      <Redirect to="/en/home"></Redirect>
    </Switch>
  </ConnectedRouter>)
}



const store = configureStore()

class AppWrapper extends Component {

  componentDidMount() {}

  render() {
    return (<Provider store={store}>
      <Header></Header>
      <MainRoutes></MainRoutes>
      <Footer></Footer>
    </Provider>);
  }
}



export default AppWrapper;
