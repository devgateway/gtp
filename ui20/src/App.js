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

            <Route exact={true} path="/:lan/home" render={() => (<div>
                    <Header ></Header>
                  <Home></Home>
              </div>)}/>

            <Route exact={true} path="/:lan/analytic/production" render={() => (<div>
                  <Header ></Header>
                  <Analytic></Analytic>
              </div>)}/>

            <Route exact={true} path="/:lan/analytic/marketPrice" render={() => (<div>
                <Header ></Header>
                  <Analytic></Analytic>
              </div>)}/>
            <Route exact={true} path="/:lan/analytic/consumption" render={() => (<div>
                <Header ></Header>
                  <Analytic></Analytic>
              </div>)}/>
            <Route exact={true} path="/:lan/indicators" render={() => (<div>
                <Header className="fix" ></Header>
                  <Indicators header={e=>this.divRef} language={props.match.params.lan}></Indicators>
              </div>)}/>
              <Route exact={true} path="/:lan/microdata" render={() => (<div>
                  <Header></Header>
                  <Microdata></Microdata>
                </div>)}/>

                <Route exact={true} path="/:lan/gis/regional" render={() => (<div>
                    <Header></Header>
                    <Regional></Regional>
                  </div>)}/>


                <Route exact={true} path="/:lan/gis/departmental" render={() => (<div>
                    <Header></Header>
                    <Departmental></Departmental>
                  </div>)}/>

                  <Route exact={true} path="/:lan/partners" render={() => (<div>
                      <Header></Header>
                      <Partners></Partners>
                    </div>)}/>

                    <Route exact={true} path="/:lan/initiatives" render={() => (<div>
                        <Header></Header>
                        <Initiatives></Initiatives>
                      </div>)}/>


                  <Route exact={true} path="/:lan/national" render={() => (<div>
                      <Header></Header>
                      <NationalIndicators></NationalIndicators>
                    </div>)}/>
            <Route render={() => (<div>
                <Header></Header>
                <div className="not-found">Page Not Found</div>
            </div>)}/>
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
