const DrySequenceFilter: {
  year: number,
  pluviometricPostId: number
} = {
}

export const drySequenceFilterFromAPI: DrySequenceFilter = ({year, pluviometricPostId}) => {
  DrySequenceFilter.year = year
  DrySequenceFilter.pluviometricPostId = pluviometricPostId
  return DrySequenceFilter
}

export default DrySequenceFilter
