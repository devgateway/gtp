import React, {
  Component
} from 'react'
import * as d3 from 'd3'
import  messages from '../translations/messages'
import './map.scss'

const formatOptions = {
  style: 'percent',
  minimumFractionDigits: 2,
  maximumFractionDigits: 2
}
const width = 638,
  height = 450;

export default class D3Map extends Component < {},
  State > {

    constructor(props) {
      super(props);
      this.showDetails = this.showDetails.bind(this);
      this.getFillColor = this.getFillColor.bind(this);
      this.clicked = this.clicked.bind(this)
      this.createPaths = this.createPaths.bind(this)
      this.createLabels = this.createLabels.bind(this)
      this.inverted=this.props.inverted;
    }

    getFillColor(d) {
      const colorInterpolator = d3.scaleSequential().domain([((this.inverted)?this.props.max:this.props.min), ((this.inverted)?this.props.min:this.props.max)]).interpolator(d3['interpolate' + this.props.color]);
      if (d.properties.value) {
        return colorInterpolator(d.properties.value)
      } else {
        return '#FFF'
      }
    }


    showDetails(fid, duration) {
      let action;
      let data;
      if (fid == null) {
        data = this.props.json
        action = 'out'
      } else {
        action = 'in'
        data = this.g.selectAll("path").filter(d => d.properties.fid == fid).data()[0]

      }

      this.scaleTo(data, fid, duration, (action=='in'?true:false))

      const measure = this.props.measure
      var text1, text2;
      if (action == 'in') {
            text2 = `  ${this.props.indicator.text} - ${data.properties.value? this.props.intl.formatNumber(data.properties.value) +' ('+ data.properties.measure +')':this.props.intl.formatMessage(messages.data_no_data_available)} `

      }
      this.svg.selectAll('text').remove()
      this.svg.selectAll('circle').remove()
      this.svg.selectAll('rect').remove()
      this.svg.selectAll('line').remove()

      if (action == 'in') {
        const self=this
            this.svg.append("text")
            .attr("x",d=>20)
            .attr("y",d=>45)
            .attr("class", "big label")
            .attr("fill", "black")
            .attr("text-anchor", "start")
            .text("")
            .transition()
            .delay(100)
            .duration(300)
            .tween("text", function(d) {
            var textLength = data.properties.NAME.length;
            return function(t) {
              this.textContent = data.properties.NAME.substr(0, Math.round(t * textLength));
            };
          });

      const valText=`${data.properties.value?self.props.intl.formatNumber(data.properties.value).toString():''} `
      const measureText=`${data.properties.measure?data.properties.measure:''} `

        this.svg.append("text")
        .attr("x",d=>width -30)
        .attr("y",d=>height -25)
        .attr("class", "medium label")
        .attr("fill", "black")
        .attr("text-anchor", "end")
        .text("")
        .transition()
        .delay(100)
        .duration(300)
        .tween("text", function(d) {
            var textLength =valText.toString().length;
            return function(t) {this.textContent = valText.substr(0, Math.round(t * textLength)) };
        });

        this.svg.append("text")
        .attr("x",d=>width -30)
        .attr("y",d=>height -5)
        .attr("class", "small label")
        .attr("fill", "black")
        .attr("text-anchor", "end")
        .text("")
        .transition()
        .delay(100)
        .duration(300)
        .tween("text", function(d) {
            var textLength =measureText.toString().length;
            return function(t) {this.textContent = measureText.substr(0, Math.round(t * textLength)) };
        });


      } else {
        this.createLabels(this.props.json)
      }

    }

    clicked(d) {
      let {fid } = d.properties
      let fid1 = this.props.selection ? this.props.selection.fid : null;
      if (this.props.onClick) {
        this.props.onClick({fid})
      }
    }

    componentDidUpdate(prevProps) {
      const newFid = this.props.selection ? this.props.selection.fid : null
      const prevFid = prevProps.selection ? prevProps.selection.fid : null
      const colorsWereChanged=(this.props.color != prevProps.color) || (this.props.sideColor != prevProps.sideColor)

      if (this.props.color != prevProps.color) {
        this.updateColors();
      }else if (newFid != prevFid )  {
        this.showDetails(newFid,400);
      }

      if (prevProps.json != this.props.json) {
        this.generate()
      }
    }

    createLabels(json) {
      const _this = this
      this.g.selectAll("text")
      .data(json.features)
      .enter()
      .append("text")
      .attr("class", "label")
      .attr("fill", "black")
      .style("text-anchor", "start")
      .attr("class", "label")
      .text(function(d) {
        var bounds=_this.path.bounds(d)
          var xwidth  = bounds[1][0] - bounds[0][0];
          if(xwidth >(d.properties.NAME.length*4)){
            return d.properties.NAME
          }else{
              return '';
          }
        })   .attr("x", function(d) {
          return _this.path.centroid(d)[0];
        })
        .attr("y", function(d) {
          return _this.path.centroid(d)[1];
        });

    }

    createPaths(json) {
      this.g.selectAll('path').remove()
      this.g.selectAll('path').data(json.features).enter().append('path').attr('d', this.path).attr('vector-effect', 'non-scaling-stroke').style('fill', this.getFillColor)
        .on('click', this.clicked).style('stroke', '#EEE')

    }


    updateColors() {
      this.g.selectAll('path').style('fill', this.getFillColor)
        .on('click', this.clicked).style('stroke', '#EEE')
    }



    scaleTo(data, fid,duration , remark) {
      var bounds = this.path.bounds(data),

        dx = bounds[1][0] - bounds[0][0],
        dy = bounds[1][1] - bounds[0][1],
        x = (bounds[0][0] + bounds[1][0]) / 2,
        y = (bounds[0][1] + bounds[1][1]) / 2,

        scale = 1 / Math.max(dx / width, dy / height),

        translate = [
          width / 2 - scale * x,
          height / 2 - scale * y
        ];
      const getFillColor = this.getFillColor

      this.g.selectAll('path')
        .style('fill', (d) => fid && d.properties.fid === fid ? getFillColor(d) : remark?"#FFF":getFillColor(d) )
        .style('stroke', (d) => fid && d.properties.fid === fid ? 'red' : '#EEE');

      this.g.transition().duration(duration)
        .style("stroke-width", 1.5 / scale + "px")
        .attr("transform", "translate(" + translate + ") scale(" + scale + ")");

    }

    generate() {

      d3.select(this.refs.container).selectAll('svg').remove()
      const json = this.props.json
      const color = this.props.color
      const onClick = this.props.onClick
      const measure = this.props.measure

      var center = d3.geoCentroid(json)

      const minValue = this.props.min
      const maxValue = this.props.max


      var scale = 4500;
      var offset = [
        width / 2,
        height / 2
      ];

      const projection = d3.geoMercator().scale(scale).center(center).translate(offset);
      this.path = d3.geoPath().projection(projection);

      this.svg = d3.select(this.refs.container).append('svg')
      this.svg.attr('width', width).attr('height', height);

      this.g = this.svg.append('g');
      this.g.attr('width', width).attr('height', height)


      var bounds = this.path.bounds(json),

        dx = bounds[1][0] - bounds[0][0],
        dy = bounds[1][1] - bounds[0][1],
        x = (bounds[0][0] + bounds[1][0]) / 2,
        y = (bounds[0][1] + bounds[1][1]) / 2,

        scale = 1 / Math.max(dx / width, dy / height),

        translate = [
          width / 2 - scale * x,
          height / 2 - scale * y
        ];
      const getFillColor = this.getFillColor

      this.g.attr("transform", "translate(" + translate + ") scale(" + scale + ")");


      this.createPaths(json)
      this.createLabels(json)
      if(this.props.selection){
        this.showDetails(this.props.selection.fid, 0)
      }

    }

    componentDidMount() {
      this.generate()
    }

    render() {
      const {indicator:{description}}=this.props
      debugger;
      return (

        <div className="map">
          <div ref = "container"/>
            <div className="description">{description }</div>
        </div>)
      }

    }
