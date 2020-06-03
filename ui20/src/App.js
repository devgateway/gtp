import React, {Component} from 'react';
import './App.css';
import {withRouter} from "react-router";
import {Provider} from 'react-redux'
import {Route, Switch, Redirect} from 'react-router' // react-router v4/v5
import {ConnectedRouter} from 'connected-react-router/immutable'
import Layout from "./components/layout/Layout"
import configureStore, {history} from './redux/Store'
import {connect} from 'react-redux';
import messages_fr from "./translations/fr.json";
import messages_en from "./translations/en.json";
import {IntlProvider} from "react-intl";

import Header from './components/layout/header/Header'
import Footer from './components/layout/Footer'


import smoothscroll from 'smoothscroll-polyfill';

import asyncComponent from "./components/common/AsyncComponent";
// import withTracker from './components/common/withTracker'

const Home = asyncComponent(() => import("./components/home/"));
const Water = asyncComponent(() => import("./components/water/"));
const Market = asyncComponent(() => import("./components/market/"));

// kick off the polyfill!
smoothscroll.polyfill();

const messages = {
  'fr': messages_fr,
  'en': messages_en
};

const withLayout = (Component) => <Layout><Component/></Layout>
const HomeLayout = (props) => withLayout(Home)
const WaterLayout = (props) => withLayout(Water)
const MarketLayout = (props) => withLayout(Market)


class IntlRoutes extends Component {

  constructor(props) {
     super(props);
     this.header = React.createRef()
   }

  render() {
    const props = this.props;
    const locale = this.props.location.pathname.split("/")[1]
    return (<IntlProvider key={locale} locale={locale} messages={messages[props.match.params.lan]}>
      <div className="page-wrapper">
          <Header />

          <Switch>
            <Route exact={true} path="/:lan/home"  component={HomeLayout}/>

            <Route exact={true} path="/:lan/water-resources"  component={WaterLayout}/>
            <Route exact={true} path="/:lan/agriculture-and-market"  component={MarketLayout}/>

            <Route render={() => (
              <Layout>
                <div className="not-found">Page Not Found</div>
              </Layout>)}/>
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
