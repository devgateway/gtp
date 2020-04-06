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

import {Container} from 'semantic-ui-react'
import Header from './layout/Header'
import Footer from './layout/Footer'

import {Departmental,Regional} from './maps/index.js'


import smoothscroll from 'smoothscroll-polyfill';

import asyncComponent from "./AsyncComponent";
import withTracker from './withTracker'



const Home = asyncComponent(() => import("./home/"));
const Indicators = asyncComponent(() => import("./indicators/"));
const Microdata = asyncComponent(() => import("./microdata/"));
const Analytic = asyncComponent(() => import("./analytic/"));
const NationalIndicators = asyncComponent(() => import("./national/"));
const Partners = asyncComponent(() => import("./partners/"));
const Initiatives = asyncComponent(() => import("./initiatives/"));




// kick off the polyfill!
smoothscroll.polyfill();




const messages = {
  'fr': messages_fr,
  'en': messages_en
};

//const language = navigator.language.split(/[-_]/)[0]; // language without region code
//import Home from './home/'
//import Indicators from './indicators'
//import Microdata from './microdata'
//import Analytic from "./analytic/"

//import NationalIndicators from './national/index.jsx'
//import Partners from './partners'
//import Initiatives from './initiatives'


const WithDefHeader=(Component)=><div><Header/><Component/></div>
const HomeLayout=(props)=>WithDefHeader(Home)
const AnalyticLayout=(props)=>WithDefHeader(Analytic)
const IndicatorLayout=(props) => (<div><Header className="fix" ></Header><Indicators header={e=>this.divRef} language={props.match.params.lan}></Indicators></div>)
const RegionalLayout=(props) => WithDefHeader(Regional)
const DepartmentalLayout=(props) => WithDefHeader(Departmental)
const PartnersLayout=(props) => WithDefHeader(Partners)
const InitiativesLayout=(props) => WithDefHeader(Initiatives)
const MicrodataLayout=(props) => WithDefHeader(Microdata)
const NationalIndicatorsLayout=(props) => WithDefHeader(NationalIndicators)



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
    this.props.onLoadFilterData('indexType/1','filter')
    this.props.onLoadFilterData('indexType/2','filter')
    this.props.onLoadFilterData('locationType','filter')
    this.props.onLoadFilterData('lossType','filter')
    this.props.onLoadFilterData('methodOfEnforcement','filter')
    this.props.onLoadFilterData('organization','filter')
    this.props.onLoadFilterData('dataset/organizations','filter')

    //data/filter/dataset/organizations

  }

  render() {
    const self=this;
    const props = this.props;
    const locale = this.props.location.pathname.split("/")[1]
    return (<IntlProvider key={locale} locale={locale} messages={messages[props.match.params.lan]}>
      <div>

          <Switch>

            <Route exact={true} path="/:lan/home"  component={withTracker(HomeLayout)}/>

            <Route exact={true} path="/:lan/analytic/production" component={withTracker(AnalyticLayout)}/>
            <Route exact={true} path="/:lan/analytic/marketPrice" component={withTracker(AnalyticLayout)}/>
            <Route exact={true} path="/:lan/analytic/consumption" component={withTracker(AnalyticLayout)}/>
            <Route exact={true} path="/:lan/indicators" component={withTracker(IndicatorLayout)}/>
            <Route exact={true} path="/:lan/microdata" component={withTracker(MicrodataLayout)}/>
            <Route exact={true} path="/:lan/gis/regional" component={withTracker(RegionalLayout)}/>
            <Route exact={true} path="/:lan/gis/departmental" component={withTracker(DepartmentalLayout)}/>
            <Route exact={true} path="/:lan/partners" component={withTracker(PartnersLayout)}/>
            <Route exact={true} path="/:lan/initiatives" component={withTracker(InitiativesLayout)}/>
            <Route exact={true} path="/:lan/national" component={withTracker(InitiativesLayout)}/>

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
      <Redirect to="/fr/home"></Redirect>

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
