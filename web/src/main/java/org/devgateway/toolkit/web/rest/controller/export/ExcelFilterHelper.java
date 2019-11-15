package org.devgateway.toolkit.web.rest.controller.export;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.dao.categories.AgriculturalWomenGroup;
import org.devgateway.toolkit.persistence.dao.categories.CropSubType;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.devgateway.toolkit.persistence.dao.categories.Gender;
import org.devgateway.toolkit.persistence.dao.categories.IndexType;
import org.devgateway.toolkit.persistence.dao.categories.LossType;
import org.devgateway.toolkit.persistence.dao.categories.PovertyLevel;
import org.devgateway.toolkit.persistence.dao.categories.ProfessionalActivity;
import org.devgateway.toolkit.persistence.dto.ExcelFilterDTO;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AgriculturalWomenFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterPagingRequest;

import java.util.TreeSet;

public class ExcelFilterHelper extends ExcelFilterDTO {

    public static final String EMPTY_STRING = "";
    public static final String FILTERS_USED = "Filters used: ";

    private TreeSet<Region> region;

    private TreeSet<CropType> crop;

    private TreeSet<Integer> year;

    private TreeSet<Gender> gender;

    private TreeSet<IndexType> indexType;

    private Double minPercentage;

    private Double maxPercentage;

    private TreeSet<AgriculturalWomenGroup> awGroup;

    private TreeSet<CropSubType> cropSubType;

    private Double minKg;

    private Double maxKg;

    private TreeSet<LossType> lossType;

    private Double minAge;

    private Double maxAge;

    private Double minScore;

    private Double maxScore;

    private TreeSet<PovertyLevel> povertyLevel;

    private TreeSet<ProfessionalActivity> activity;

    private Double minAvgPercentage;

    private Double maxAvgPercentage;

    private Double minAvgKilogram;

    private Double maxAvgKilogram;


    public ExcelFilterHelper() {
    }

    public ExcelFilterHelper(AOIFilterPagingRequest req) {
        year = req.getYear();
        filters = filtersToString();
    }

    public ExcelFilterHelper(AgriculturalWomenFilterPagingRequest req) {
        year = req.getYear();

        filters = filtersToString();
    }

    public ExcelFilterHelper(FoodLossFilterPagingRequest req) {
        year = req.getYear();

        filters = filtersToString();
    }

    public ExcelFilterHelper(PovertyFilterPagingRequest req) {
        year = req.getYear();

        filters = filtersToString();
    }

    public String filtersToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getLabels(" Years: ", year));
        sb.append(getLabels(" Regions: ", region));
        sb.append(getLabels(" Genders: ", gender));
        sb.append(getLabels(" Index Types: ", indexType));
        sb.append(getLabels(" Crop Types: ", crop));
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

    private String getLabels(String label, TreeSet t) {
        if (t != null && !t.isEmpty()) {
            return (label + t);
        }
        return EMPTY_STRING;
    }

    public TreeSet<Region> getRegion() {
        return region;
    }

    public void setRegion(TreeSet<Region> region) {
        this.region = region;
    }

    public TreeSet<CropType> getCrop() {
        return crop;
    }

    public void setCrop(TreeSet<CropType> crop) {
        this.crop = crop;
    }

    public TreeSet<Integer> getYear() {
        return year;
    }

    public void setYear(TreeSet<Integer> year) {
        this.year = year;
    }

    public TreeSet<Gender> getGender() {
        return gender;
    }

    public void setGender(TreeSet<Gender> gender) {
        this.gender = gender;
    }

    public TreeSet<IndexType> getIndexType() {
        return indexType;
    }

    public void setIndexType(TreeSet<IndexType> indexType) {
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

    public TreeSet<AgriculturalWomenGroup> getAwGroup() {
        return awGroup;
    }

    public void setAwGroup(TreeSet<AgriculturalWomenGroup> awGroup) {
        this.awGroup = awGroup;
    }

    public TreeSet<CropSubType> getCropSubType() {
        return cropSubType;
    }

    public void setCropSubType(TreeSet<CropSubType> cropSubType) {
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

    public TreeSet<LossType> getLossType() {
        return lossType;
    }

    public void setLossType(TreeSet<LossType> lossType) {
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

    public TreeSet<PovertyLevel> getPovertyLevel() {
        return povertyLevel;
    }

    public void setPovertyLevel(TreeSet<PovertyLevel> povertyLevel) {
        this.povertyLevel = povertyLevel;
    }

    public TreeSet<ProfessionalActivity> getActivity() {
        return activity;
    }

    public void setActivity(TreeSet<ProfessionalActivity> activity) {
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

    @Override
    public String getFilters() {
        return filters;
    }

    @Override
    public void setFilters(String filters) {
        this.filters = filters;
    }
}
