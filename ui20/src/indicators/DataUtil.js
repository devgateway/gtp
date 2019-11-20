



export const gender2options = (genders) => genders
  ? genders.sort((c1, c2) => c1.label.localeCompare(c2.label)).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []

export const activity2options = (activities) => activities
  ? activities.sort((c1, c2) => c1.label.localeCompare(c2.label)).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []

export const age2options = (activities) => activities
  ? activities.sort((c1, c2) => c1.id - c2.id).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []

export const items2options=(items)=> items
  ? items.sort((c1, c2) => c1.id - c2.id).map(r => ({'key': r.id, 'text': r.label, 'value': r.id}))
  : []



  export const getPovertyRegionalYearly = (data = []) => {

    const keys=Array.from(new Set(data.map(d=>d.year)))
    const years = new Set(data.map(r => r.year))
    const regions = new Set(data.map(r => r.region))
    let barData = []

    regions.forEach(r => {
      const record = {'Region': r}
      years.forEach(y=>{
        const toReduce=data.filter(d=>d.year==y && d.region==r && d.povertyLevel!='Not poor')
        const value =toReduce.reduce((a,b)=>a+b.percentage,0)
        record[y]=value*100
      })
      barData.push(record)
    })

    return   {
      'data':barData,
      'keys':keys,
      'groupMode':"grouped",
      'indexBy':"Region",
      'colors':{ scheme: 'red_yellow_blue'}
    }
  }



export const getPovertyRegionalStackedByPovertyLevel = (data) => {
    const keys=Array.from(new Set(data.map(d=>d.povertyLevel)))
    const years = Array.from(new Set(data.map(r => r.year)))
    const maxYear=years.pop()
    const regions = new Set(data.map(r => r.region))
    const mostRecent=data.filter(d=>d.year==maxYear)
    let barData = []
    regions.forEach(r => {
      const record = {'Region': r}
          mostRecent.filter(m=>m.region==r).forEach(p=>{
            record[p.povertyLevel]=p.percentage *100
          })
        barData.push(record)
    })


  return   {
      data: barData,
      keys:keys,
      groupMode:"grouped",
      indexBy:"Region",
      groupMode:'stacked',
      colors:["#228E58","#C25E7F","#C15E50"]
    }
  }


export const getPovertyTimeLine=(data)=>{

  const regions=Array.from(new Set(data.map(d=>d.region)))
  const years=Array.from(new Set(data.map(d=>d.year)))
  const lineData=regions.map(r=>{
      const record={'id':r, 'data':[]}

        years.forEach((y)=>{
            debugger;
            const value=data.filter(d=>d.region==r && d.povertyLevel!='Not poor' && d.year==y).reduce((a,b)=>a+(b.percentage*100),0)

            record.data.push({'x':y,'y':value})

        })


        return record
  })
  debugger;
  return {'data':lineData}
}


export const getPovertyTimeLine_1=(data)=>{

  const regions=Array.from(new Set(data.map(d=>d.region)))

  const lineData=regions.map(r=>{
      debugger;
    const subData=data.filter(d=>d.region==r).map(f=>{return {x:f.year, y:(f.percentage*100), level:f.povertyLevel}}).filter(f=>f.level!='Not poor')
    const series=Array.from(new Set(subData.map(r=>r.x))).map(year=>{
      let y=0;
      subData.filter(s=>s.x==year).forEach(val=>y=y+val.y)
      return {x:year, y:y}
    })

    return {id:r,  "color": "hsl(332, 70%, 50%)", data:series}

  })
  return Object.keys(lineData).map(k=>lineData[k])
}






export const getAverageProductionLossData = (data = [], valueField) => {

  const crops = Array.from(new Set(data.map(d => d.cropType)));
  const types =  Array.from(new Set(data.map(r => r.lossType)))
  let barData = []
  const years = new Set(data.map(r => r.year))
  const maxYear = Array.from(years).sort()[years.size - 1]

  const mostRecent =data.filter(d=>d.year==maxYear)

  crops.forEach(g => {
    const r = {'Crop': g}

    const value = mostRecent.filter(d => d.cropType == g).forEach(gd=>{
      r[gd.lossType]=gd[valueField]
    });
    barData.push(r)
  })
  return   {
      data: barData,
      keys:types,
      indexBy:"Crop",
      groupMode:'stacked'
    }
}





export const getWomenDistributionByGroup = (data = []) => {

  const keys = Array.from(new Set(data.map(d => d.groupType)));
  const groups =  Array.from(new Set(data.map(r => r.groupType)))
  const genders =  Array.from(new Set(data.map(r => r.gender)))

  let barData = []
  const years = new Set(data.map(r => r.year))
  const maxYear = Array.from(years).sort()[years.size - 1]

  const mostRecent =data.filter(d=>d.year==maxYear)

  groups.forEach(g => {
    const r = {'Age': g}
    const value = mostRecent.filter(d => d.groupType == g).forEach(gd=>{
      r[gd.gender]=gd.percentage
    });
    barData.push(r)
  })

  console.log(barData)
  return   {
      data: barData,
      keys:genders,
      indexBy:"Age",
      groupMode:'stacked'
    }

}


export const getWomebHistoricalDistribution = (data = []) => {
  const keys = Array.from(new Set(data.map(d => d.groupType)));
  const groups =  Array.from(new Set(data.map(r => r.groupType)))
  const genders =  Array.from(new Set(data.map(r => r.gender)))
  const years = Array.from(new Set(data.map(r => r.year)))
  const filteredData=data.filter(d=>d.gender=='Female')
  const colors={'Male':[],'Female':[]}
  const getColor = (p) =>{}

  let lineData = []
    groups.forEach((g) =>{
      const r2 = {'id': g ,gender:'Female',  data:[]}
        years.forEach((year) =>{
          const filtered=filteredData.filter(d=>d.year==year && d.groupType==g&&d.gender=='Female');
          if (filtered.length >0){
            const value=filtered.reduce((a,b)=>{return a+b.percentage;},0)
            r2.data.push({'x':year, 'y':value})
          }
        })
        if(r2.data.length >0){
            lineData.push(r2)
        }
      })
    const sorted = lineData.sort((a,b) => a.gender.localeCompare(b.gender))
    return  {data:sorted}
}



export const getAOItotalBudget=(data)=>{
  let barData=[]
  let years=[]
  let keys=[]

    if (data){
      debugger;
      data.sort((a,b)=>a.year-b.year)
      years= [...new Set(data.map(d=>d.year))]
      keys= [...new Set(data.map(d=>d.indexType))]

      years.map(y=>{
          const row={'Year':y}
          const yearlyData=data.filter(d=>d.year==y).forEach(r=>{
            row[r.indexType]=r.budgetedExpenditures
          })
          barData.push(row)

      })
    }


  return   {
      data: barData,
      keys:keys,
      groupMode:"stacked",
      indexBy:"Year",
  }
}


export const getAOIsubsidies=(data)=>{
  let barData=[]
  let years=[]
  let keys=[]

    if (data){
      data.sort((a,b)=>a.year-b.year)
      years= [...new Set(data.map(d=>d.year))]
      keys= [...new Set(data.map(d=>d.indexType))]

      years.map(y=>{
          const row={'Year':y}
          const yearlyData=data.filter(d=>d.year==y).forEach(r=>{
            row[r.indexType]=r.subsidies
          })
          barData.push(row)

      })
    }


  return   {
      data: barData,
      keys:keys,
      groupMode:"stacked",
      indexBy:"Year",
  }
}
