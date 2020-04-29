import React,{Component} from 'react';
import GoogleAnalytics from "react-ga";
import {getAnaliticUserCode} from '../../modules/api'


GoogleAnalytics.initialize(getAnaliticUserCode());

const withTracker = (WrappedComponent, options = {}) => {
  const trackPage = page => {
    GoogleAnalytics.set({
      page,
      ...options,
    });
    debugger;
    GoogleAnalytics.pageview(page);
  };

  const HOC = class extends Component {
    componentDidMount() {
      const page = this.props.location.pathname;
      trackPage(page);
    }

    componentWillReceiveProps(nextProps) {
      const currentPage = this.props.location.pathname;
      const nextPage = nextProps.location.pathname;

      if (currentPage !== nextPage) {
        trackPage(nextPage);
      }
    }

    render() {
      debugger;
      return <WrappedComponent {...this.props} />;
    }
  };

  return HOC;
};

export default withTracker;
