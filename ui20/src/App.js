import React, {Component} from 'react';
import './App.css';
import {withRouter} from "react-router";
import {Provider} from 'react-redux'
import {Route, Switch, Redirect} from 'react-router' // react-router v4/v5
import {ConnectedRouter} from 'connected-react-router/immutable'
import ConnectionCheckWrapper from "./components/common/ConnectionCheckWrapper"
import {cssClasses, getBrowserClass} from "./components/ComponentUtil"
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
const About = asyncComponent(() => import("./components/about/"));
const Water = asyncComponent(() => import("./components/water/"));
const Market = asyncComponent(() => import("./components/market/"));
const Bulletin = asyncComponent(() => import("./components/bulletin/"));

// kick off the polyfill!
smoothscroll.polyfill();

const messages = {
  'fr': messages_fr,
  'en': messages_en
};

const withLayout = (Component) => <Layout><ConnectionCheckWrapper childrenBuilder={(props) => <Component {...props} />} /></Layout>
const HomeLayout = (props) => withLayout(Home)
const AboutLayout = (props) => withLayout(About)
const WaterLayout = (props) => withLayout(Water)
const MarketLayout = (props) => withLayout(Market)
const BulletinLayout = (props) => withLayout(Bulletin)


class IntlRoutes extends Component {

  constructor(props) {
     super(props);
     this.header = React.createRef()
   }

  render() {
    const props = this.props;
    const locale = this.props.location.pathname.split("/")[1]
    return (<IntlProvider key={locale} locale={locale} messages={messages[props.match.params.lan]}>
      <div className={cssClasses("page-wrapper", getBrowserClass())}>
          <Header />

          <Switch>
            <Route exact={true} path="/:lan/home"  component={HomeLayout}/>
            <Route exact={true} path="/:lan/about"  component={AboutLayout}/>

            <Route exact={true} path="/:lan/water-resources"  component={WaterLayout}/>
            <Route exact={true} path="/:lan/agriculture-and-market"  component={MarketLayout}/>

            <Route exact={true} path="/:lan/gtp-bulletins"  component={BulletinLayout}/>

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
