import React, {Component} from 'react'
import * as d3 from 'd3'

import './map.scss'

const formatOptions = {
    style: 'percent',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  }
  const width = 655,
    height = 500;

  export default class D3Map extends Component < {},
  State > {

    constructor(props) {
      super(props);
      this.showDetails = this.showDetails.bind(this);
      this.getFillColor = this.getFillColor.bind(this);
      this.clicked = this.clicked.bind(this)
      this.createPaths = this.createPaths.bind(this)
      this.createLabels = this.createLabels.bind(this)
    }

    getFillColor(d) {
      const colorInterpolator = d3.scaleSequential().domain([this.props.min,this.props.max]).interpolator(d3['interpolate' + this.props.color]);
      if (d.properties.value) {
        return colorInterpolator(d.properties.value)
      } else {
        return '#FFF'
      }
    }

    showDetails(fid) {

      const data = this.g.selectAll("path").filter(d => d.properties.fid == fid).data()[0]
      
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

      const measure = this.props.measure
      const getFillColor = this.getFillColor
        var text1, text2;

        if (fid != null) {
          text1 = `${data.properties.indicator}`
          text2 = `value: ${this.props.intl.formatNumber(data.properties.value)}`
        }

        this.g.selectAll('path').style('fill', (d) => fid && d.properties.fid === fid ? getFillColor(d) : getFillColor(d)).style('stroke', (d) => fid && d.properties.fid === fid? 'red': '#EEE');
        this.g.transition().duration(400).style("stroke-width", 1.5 / scale + "px").attr("transform", "translate(" + translate + ")scale(" + scale + ")");

        this.svg.selectAll('text').remove()
        this.svg.selectAll('circle').remove()
        this.svg.selectAll('rect').remove()

        if (fid != null) {
          this.createLabels({features: [data]})
          this.svg.append('rect')
          .attr("rx", 0)
          .attr("ry", 0)
          .attr('x', 0)
          .attr('y', 0)
          .attr('width', width).attr('height', 60)
          this.svg.append("text").attr('class', 'info').attr("x", 15).attr("y", 25).text("").transition().delay(100).duration(500).tween("text", function(d) {
            var textLength = text1.length;
            return function(t) {
              this.textContent = text1.substr(0, Math.round(t * textLength));
            };
          });

          this.svg.append("text").attr("x", 15).attr("y", 48).text("").transition().delay(600).duration(500).tween("text", function(d) {
            var textLength = text2.length;
            return function(t) {
              this.textContent = text2.substr(0, Math.round(t * textLength));
            };
          });
        } else {
          this.createLabels(this.props.json)
        }
    }


    clicked(d) {
  
      let {fid} = d.properties
      let fid1 = this.props.selection? this.props.selection.fid: null;

      if (fid == fid1) {
        this.props.onClick({fid: null})
      }

      if (this.props.onClick) {
        this.props.onClick({fid})
      }

    }

    componentDidUpdate(prevProps) {
      
      if (prevProps.data != this.props.data) {
        this.generate()
      }

      if (this.props.selection) {
        if ((prevProps.selection == null && this.props.selection != null) || (prevProps.selection.fid != this.props.selection.fid1)) {
          this.showDetails(this.props.selection.fid);
        }
      }
    }

    createLabels(json) {
      const _this = this

      this.g.selectAll("circle").data(json.features).enter().append("circle").attr("r", 2).attr("cx", d => _this.path.centroid(d)[0] - 4).attr("cy", d => _this.path.centroid(d)[1] - 3)

      this.g.selectAll("text").data(json.features).enter().append("text").attr("class", "label").attr("fill", "black").style("text-anchor", "start").attr("class", "label")
      .attr("transform", (d)=> {
        return "translate(" + _this.path.centroid(d) + ")";
      }).text(function(d) {
        return d.properties.NAME_1
      });

    }

    createPaths(json) {
      this.g.selectAll('path').data(json.features).enter().append('path').attr('d', this.path).attr('vector-effect', 'non-scaling-stroke').style('fill', this.getFillColor)
      .on('click', this.clicked).style('stroke', '#EEE')

    }


      generate() {
        d3.select(this.refs.container).selectAll('svg').remove()
        const json = this.props.json
        const color = this.props.color
        const onClick = this.props.onClick
        const measure = this.props.measure

        var center = d3.geoCentroid(json)

        const minValue=this.props.min
        const maxValue =this.props.max


        var scale = 4000;
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

        this.createPaths(json)
        this.createLabels(json)
      }

      componentDidMount() {
        this.generate()
      }

      render() {
        return (< div className = "map" ref = "container" > < /div>)
      }

  }
