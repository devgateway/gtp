package org.devgateway.toolkit.web.rest.controller.export;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dto.ExcelFilterDTO;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AgriculturalWomenFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterPagingRequest;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ExcelFilterHelper extends ExcelFilterDTO {

    public static final String EMPTY_STRING = "";
    public static final String FILTERS_USED = "Filters used: ";

    private Set<Category> region;

    private Set<Category> crop;

    private Set<Integer> year;

    private Set<Category> gender;

    private Set<Category> indexType;

    private Double minPercentage;

    private Double maxPercentage;

    private Set<Category> awGroup;

    private Set<Category> awGroupType;

    private Set<Category> ageGroup;

    private Set<Category> methodOfEnforcement;

    private Set<Category> cropSubType;

    private Double minKg;

    private Double maxKg;

    private Set<Category> lossType;

    private Double minAge;

    private Double maxAge;

    private Double minScore;

    private Double maxScore;

    private Set<Category> povertyLevel;

    private Set<Category> activity;

    private Double minAvgPercentage;

    private Double maxAvgPercentage;

    private Double minAvgKilogram;

    private Double maxAvgKilogram;

    private String lang;


    public ExcelFilterHelper() {
    }

    public ExcelFilterHelper(AOIFilterPagingRequest req, Map<Integer, Category> categories) {
        lang = req.getLang();
        year = req.getYear();
        if (req.getCrop() != null) {
            crop = req.getCrop().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        if (req.getIndexType() != null) {
            indexType = req.getIndexType().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        filters = filtersToString();
    }

    public ExcelFilterHelper(AgriculturalWomenFilterPagingRequest req, Map<Integer, Category> categories) {
        lang = req.getLang();
        year = req.getYear();
        minPercentage = req.getMinPercentage();
        maxPercentage = req.getMaxPercentage();
        if (req.getGender() != null) {
            gender = req.getGender().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        if (req.getAwGroup() != null) {
            awGroup = req.getAwGroup().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        if (req.getAwGroupType() != null) {
            awGroupType = req.getAwGroupType().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        if (req.getAgeGroup() != null) {
            ageGroup = req.getAgeGroup().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        if (req.getMethodOfEnforcement() != null) {
            methodOfEnforcement = req.getMethodOfEnforcement().stream()
                    .map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        filters = filtersToString();
    }

    public ExcelFilterHelper(FoodLossFilterPagingRequest req, Map<Integer, Category> categories) {
        lang = req.getLang();
        year = req.getYear();
        minAvgPercentage = req.getMinAvgPercentage();
        maxAvgPercentage = req.getMaxAvgPercentage();
        minAvgKilogram = req.getMinAvgKilogram();
        maxAvgKilogram = req.getMaxAvgKilogram();
        if (req.getCrop() != null) {
            crop = req.getCrop().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        if (req.getLossType() != null) {
            lossType = req.getLossType().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        filters = filtersToString();
    }

    public ExcelFilterHelper(PovertyFilterPagingRequest req, Map<Integer, Category> categories) {
        lang = req.getLang();
        year = req.getYear();
        minAge = req.getMinAge();
        maxAge = req.getMaxAge();
        minScore = req.getMinScore();
        maxScore = req.getMaxScore();
        if (req.getCrop() != null) {
            crop = req.getCrop().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        if (req.getRegion() != null) {
            crop = req.getRegion().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        if (req.getPovertyLevel() != null) {
            povertyLevel = req.getPovertyLevel().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        if (req.getActivity() != null) {
            activity = req.getActivity().stream().map(x -> categories.get(x)).collect(Collectors.toSet());
        }
        filters = filtersToString();
    }

    public String filtersToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getLabels(" Years: ", year));
        sb.append(getLabels(" Regions: ", region));
        sb.append(getLabels(" Genders: ", gender));
        sb.append(getLabels(" Index Types: ", indexType));
        sb.append(getLabels(" Crop Types: ", crop));
        sb.append(getLabels(" AW Groups: ", awGroup));
        sb.append(getLabels(" AW Group Types: ", awGroupType));
        sb.append(getLabels(" Crop SubTypes: ", cropSubType));
        sb.append(getLabels(" Loss Types: ", lossType));
        sb.append(getLabels(" Poverty levels: ", povertyLevel));
        sb.append(getLabels(" Profesional Activities: ", activity));
        if (minPercentage != null) {
            sb.append(" Minimum Percentage: " + minPercentage.toString());
        }
        if (maxPercentage != null) {
            sb.append(" Maximum Percentage: " + maxPercentage.toString());
        }
        if (minKg != null) {
            sb.append(" Minimum Kilograms: " + minKg.toString());
        }
        if (maxKg != null) {
            sb.append(" Maximum Percentage: " + maxKg.toString());
        }
        if (minAge != null) {
            sb.append(" Minimum Age: " + minAge.toString());
        }
        if (maxAge != null) {
            sb.append(" Maximum Age: " + maxAge.toString());
        }
        if (minScore != null) {
            sb.append(" Minimum Score: " + minScore.toString());
        }
        if (maxScore != null) {
            sb.append(" Maximum Score: " + maxScore.toString());
        }
        if (minAvgPercentage != null) {
            sb.append(" Minimum Avg Percentage: " + minAvgPercentage.toString());
        }
        if (maxAvgPercentage != null) {
            sb.append(" Maximum Avg Percentage: " + maxAvgPercentage.toString());
        }
        if (minAvgKilogram != null) {
            sb.append(" Minimum Avg Kilograms: " + minAvgKilogram.toString());
        }
        if (maxAvgKilogram != null) {
            sb.append(" Maximum Avg Kilograms: " + maxAvgKilogram.toString());
        }
        if (StringUtils.isEmpty(sb.toString())) {
            sb.append(" None");
        }

        return FILTERS_USED + sb.toString();
    }

    private String getLabels(String label, Set t) {
        if (t != null && !t.isEmpty()) {
            return (label + t);
        }
        return EMPTY_STRING;
    }

    public Set<Category> getRegion() {
        return region;
    }

    public void setRegion(Set<Category> region) {
        this.region = region;
    }

    public Set<Category> getCrop() {
        return crop;
    }

    public void setCrop(Set<Category> crop) {
        this.crop = crop;
    }

    public Set<Integer> getYear() {
        return year;
    }

    public void setYear(Set<Integer> year) {
        this.year = year;
    }

    public Set<Category> getGender() {
        return gender;
    }

    public void setGender(Set<Category> gender) {
        this.gender = gender;
    }

    public Set<Category> getIndexType() {
        return indexType;
    }

    public void setIndexType(Set<Category> indexType) {
        this.indexType = indexType;
    }

    public Double getMinPercentage() {
        return minPercentage;
    }

    public void setMinPercentage(Double minPercentage) {
        this.minPercentage = minPercentage;
    }

    public Double getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(Double maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public Set<Category> getAwGroup() {
        return awGroup;
    }

    public void setAwGroup(Set<Category> awGroup) {
        this.awGroup = awGroup;
    }

    public Set<Category> getAwGroupType() {
        return awGroupType;
    }

    public void setAwGroupType(Set<Category> awGroupType) {
        this.awGroupType = awGroupType;
    }

    public Set<Category> getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(Set<Category> ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Set<Category> getMethodOfEnforcement() {
        return methodOfEnforcement;
    }

    public void setMethodOfEnforcement(Set<Category> methodOfEnforcement) {
        this.methodOfEnforcement = methodOfEnforcement;
    }

    public Set<Category> getCropSubType() {
        return cropSubType;
    }

    public void setCropSubType(Set<Category> cropSubType) {
        this.cropSubType = cropSubType;
    }

    public Double getMinKg() {
        return minKg;
    }

    public void setMinKg(Double minKg) {
        this.minKg = minKg;
    }

    public Double getMaxKg() {
        return maxKg;
    }

    public void setMaxKg(Double maxKg) {
        this.maxKg = maxKg;
    }

    public Set<Category> getLossType() {
        return lossType;
    }

    public void setLossType(Set<Category> lossType) {
        this.lossType = lossType;
    }

    public Double getMinAge() {
        return minAge;
    }

    public void setMinAge(Double minAge) {
        this.minAge = minAge;
    }

    public Double getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Double maxAge) {
        this.maxAge = maxAge;
    }

    public Double getMinScore() {
        return minScore;
    }

    public void setMinScore(Double minScore) {
        this.minScore = minScore;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public Set<Category> getPovertyLevel() {
        return povertyLevel;
    }

    public void setPovertyLevel(Set<Category> povertyLevel) {
        this.povertyLevel = povertyLevel;
    }

    public Set<Category> getActivity() {
        return activity;
    }

    public void setActivity(Set<Category> activity) {
        this.activity = activity;
    }

    public Double getMinAvgPercentage() {
        return minAvgPercentage;
    }

    public void setMinAvgPercentage(Double minAvgPercentage) {
        this.minAvgPercentage = minAvgPercentage;
    }

    public Double getMaxAvgPercentage() {
        return maxAvgPercentage;
    }

    public void setMaxAvgPercentage(Double maxAvgPercentage) {
        this.maxAvgPercentage = maxAvgPercentage;
    }

    public Double getMinAvgKilogram() {
        return minAvgKilogram;
    }

    public void setMinAvgKilogram(Double minAvgKilogram) {
        this.minAvgKilogram = minAvgKilogram;
    }

    public Double getMaxAvgKilogram() {
        return maxAvgKilogram;
    }

    public void setMaxAvgKilogram(Double maxAvgKilogram) {
        this.maxAvgKilogram = maxAvgKilogram;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String getFilters() {
        return filters;
    }

    @Override
    public void setFilters(String filters) {
        this.filters = filters;
    }
}
