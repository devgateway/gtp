import React, {
  Component
} from 'react'
import * as d3 from 'd3'



export default class D3Map extends Component < {}, State > {

    componentDidMount() {
      const json = this.props.json
      const color = this.props.color
      const onClickEvent = this.props.onClick

      var width = 600,
        height = 550,
        centered;

      var center = d3.geoCentroid(json)
      var scale = 4000;
      var offset = [width / 2, height / 2];
      const colorInterpolator = d3.scaleSequential(d3['interpolate' + color]);

      const projection = d3.geoMercator().scale(scale).center(center).translate(offset);
      const path = d3.geoPath().projection(projection);



      const svg = d3.select(this.refs.container).append('svg')
      svg.attr('width', width)
        .attr('height', height);



      var g = svg.append('g');
      g.attr('width', width)
        .attr('height', height)

      g.selectAll('path')
        .data(json.features)
        .enter().append('path')
        .attr('d', path)
        .attr('vector-effect', 'non-scaling-stroke')
        .style('fill', d => {
          return colorInterpolator(d.properties.NAME_1.length / 10)
        }).on('click', clicked)
        .on('mouseover', handleMouseOver)
        .on('mouseout', handleMouseOut)
        .on('mousemove', handleMouseMove)


      var div = d3.select("body").append("div")
        .attr("class", "tooltip")
        .style("opacity", 0);


      function handleMouseOut(d) {
        div.style("opacity", 0);
      }


      function handleMouseOver(d) {

        div.transition()
          .duration(200)
          .style("opacity", .9);
        div.html(`cadena de texto`).style("left", (d3.event.pageX - 50) + "px")
          .style("top", (d3.event.pageY - 28) + "px");
      }

      function handleMouseMove(d) {

        div.style("left", (d3.event.pageX - 50) + "px").style("top", (d3.event.pageY - 28) + "px");
      }

      function clicked(d) {
        var x, y, k;

        // Compute centroid of the selected path
        if (d && centered !== d) {
          var centroid = path.centroid(d);
          x = centroid[0];
          y = centroid[1];
          k = 4;
          centered = d;
        } else {
          x = width / 2;
          y = height / 2;
          k = 1;
          centered = null;
        }

        // Highlight the clicked province
        g.selectAll('path').style('fill', function(d) {
          return centered && d === centered ? '#D5708B' : colorInterpolator(d.properties.NAME_1.length / 10);
        });

        // Zoom
        g.transition().duration(750).attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')scale(' + k + ')translate(' + -x + ',' + -y + ')');





        if (onClickEvent) {
          onClickEvent(d)
        }

      }




    }

    render() {
      return ( < div className = "map"
        ref = "container" > < /div>)
      }
    }
