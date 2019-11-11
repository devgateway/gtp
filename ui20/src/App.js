import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import {withRouter} from "react-router";
import {Provider} from 'react-redux'
import {Route, Switch, Redirect} from 'react-router' // react-router v4/v5
import {ConnectedRouter, ConnectedRoute} from 'connected-react-router/immutable'
import configureStore, {history} from './Store'
import {connect} from 'react-redux';
import messages_fr from "./translations/fr.json";
import messages_en from "./translations/en.json";
import {IntlProvider} from "react-intl";
import {loadDataItems} from './modules/Data'
import Home from './home/Home'
import Analytic from "./analytic/Analytic"
import {Container} from 'semantic-ui-react'
import Header from './layout/Header'
import Footer from './layout/Footer'
import Indicators from './indicators/Indicators'


var areIntlLocalesSupported = require('intl-locales-supported');

const messages = {
  'fr': messages_fr,
  'en': messages_en
};

const language = navigator.language.split(/[-_]/)[0]; // language without region code


class IntlRoutes extends Component {


  constructor(props) {
     super(props);
     this.header = React.createRef()
   }

  componentDidMount(){
    this.props.onLoadFilterData('year','filter')
    this.props.onLoadFilterData('region','filter')
    this.props.onLoadFilterData('cropType','filter')
    this.props.onLoadFilterData('department','filter')
    this.props.onLoadFilterData('market','filter')
    this.props.onLoadFilterData('ageGroup','filter')
    this.props.onLoadFilterData('professionalActivity','filter')
    this.props.onLoadFilterData('gender','filter')
    this.props.onLoadFilterData('methodOfEnforcement','filter')


  }

  render() {
    const self=this;
    const props = this.props;
    const locale = this.props.location.pathname.split("/")[1]
    return (<IntlProvider key={locale} locale={locale} messages={messages[props.match.params.lan]}>
      <div>

          <Switch>

            <Route exact={true} path="/:lan/home" render={() => (<div>
                    <Header ></Header>
                  <Home language={props.match.params.lan}></Home>
              </div>)}/>
            <Route exact={true} path="/:lan/analytic/production" render={() => (<div>
                  <Header ></Header>
                  <Analytic language={props.match.params.lan}></Analytic>
              </div>)}/>
            <Route exact={true} path="/:lan/analytic/marketPrice" render={() => (<div>
                <Header ></Header>
                  <Analytic language={props.match.params.lan}></Analytic>
              </div>)}/>
            <Route exact={true} path="/:lan/analytic/consumption" render={() => (<div>
                <Header ></Header>
                  <Analytic language={props.match.params.lan}></Analytic>
              </div>)}/>
            <Route exact={true} path="/:lan/indicators" render={() => (<div>
                <Header className="fix" ></Header>
                  <Indicators header={e=>this.divRef} language={props.match.params.lan}></Indicators>
              </div>)}/>
            <Route render={() => (<div className="not-found">Page Not Found</div>)}/>
          </Switch>

          <Footer></Footer>
      </div>
    </IntlProvider>)
  }
}




const mapStateToProps = (state, ownProps) => {


  return {

  }
}

const mapActionCreators = {
  onLoadFilterData: loadDataItems,

};

const IntlRoutesRouted = connect(mapStateToProps, mapActionCreators)(withRouter(IntlRoutes))



const MainRoutes = (props) => {
  return (<ConnectedRouter history={history}>
  <Switch>

      <Route path="/:lan/" component={IntlRoutesRouted}></Route>
      <Redirect to="/en/home"></Redirect>

    </Switch>

  </ConnectedRouter>)
}

const store = configureStore()

class AppWrapper extends Component {

  render() {

    return (<Provider store={store}>
      <MainRoutes></MainRoutes>

    </Provider>);
  }
}

export default AppWrapper;
