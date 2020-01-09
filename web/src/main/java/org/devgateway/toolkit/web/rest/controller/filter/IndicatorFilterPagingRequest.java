package org.devgateway.toolkit.web.rest.controller.filter;

import java.util.TreeSet;

public class IndicatorFilterPagingRequest extends DefaultFilterPagingRequest {

    private Double minPercentage;

    private Double maxPercentage;

    private TreeSet<Integer> id;

    private TreeSet<Integer> datasetId;

    private TreeSet<Integer> region;

    private TreeSet<Integer> year;

    private TreeSet<Integer> gender;

    private TreeSet<Integer> crop;

    private TreeSet<Integer> awGroup;

    private TreeSet<Integer> awGroupType;

    private TreeSet<Integer> ageGroup;

    private TreeSet<Integer> methodOfEnforcement;

    private TreeSet<Integer> indexType;

    private AOIWrapper subsidies;

    private AOIWrapper budget;

    private Double minAvgPercentage;

    private Double maxAvgPercentage;

    private Double minAvgKilogram;

    private Double maxAvgKilogram;

    private TreeSet<Integer> lossType;

    private Double minAge;

    private Double maxAge;

    private Double minScore;

    private Double maxScore;

    private TreeSet<Integer> povertyLevel;

    private TreeSet<Integer> activity;

    private String lang;

    @Override
    public TreeSet<Integer> getRegion() {
        return region;
    }

    @Override
    public void setRegion(TreeSet<Integer> region) {
        this.region = region;
    }

    @Override
    public TreeSet<Integer> getId() {
        return id;
    }

    @Override
    public void setId(TreeSet<Integer> id) {
        this.id = id;
    }

    @Override
    public TreeSet<Integer> getDatasetId() {
        return datasetId;
    }

    @Override
    public void setDatasetId(TreeSet<Integer> datasetId) {
        this.datasetId = datasetId;
    }

    @Override
    public TreeSet<Integer> getYear() {
        return year;
    }

    @Override
    public void setYear(TreeSet<Integer> year) {
        this.year = year;
    }

    @Override
    public TreeSet<Integer> getGender() {
        return gender;
    }

    @Override
    public void setGender(TreeSet<Integer> gender) {
        this.gender = gender;
    }

    @Override
    public TreeSet<Integer> getCrop() {
        return crop;
    }

    @Override
    public void setCrop(TreeSet<Integer> crop) {
        this.crop = crop;
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

    public TreeSet<Integer> getAwGroup() {
        return awGroup;
    }

    public void setAwGroup(TreeSet<Integer> awGroup) {
        this.awGroup = awGroup;
    }

    public TreeSet<Integer> getAwGroupType() {
        return awGroupType;
    }

    public void setAwGroupType(TreeSet<Integer> awGroupType) {
        this.awGroupType = awGroupType;
    }

    public TreeSet<Integer> getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(TreeSet<Integer> ageGroup) {
        this.ageGroup = ageGroup;
    }

    public TreeSet<Integer> getMethodOfEnforcement() {
        return methodOfEnforcement;
    }

    public void setMethodOfEnforcement(TreeSet<Integer> methodOfEnforcement) {
        this.methodOfEnforcement = methodOfEnforcement;
    }

    public TreeSet<Integer> getIndexType() {
        return indexType;
    }

    public void setIndexType(TreeSet<Integer> indexType) {
        this.indexType = indexType;
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

    public TreeSet<Integer> getLossType() {
        return lossType;
    }

    public void setLossType(TreeSet<Integer> lossType) {
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

    public TreeSet<Integer> getPovertyLevel() {
        return povertyLevel;
    }

    public void setPovertyLevel(TreeSet<Integer> povertyLevel) {
        this.povertyLevel = povertyLevel;
    }

    public TreeSet<Integer> getActivity() {
        return activity;
    }

    public void setActivity(TreeSet<Integer> activity) {
        this.activity = activity;
    }

    public AOIWrapper getSubsidies() {
        return subsidies;
    }

    public void setSubsidies(AOIWrapper subsidies) {
        this.subsidies = subsidies;
    }

    public AOIWrapper getBudget() {
        return budget;
    }

    public void setBudget(AOIWrapper budget) {
        this.budget = budget;
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public void setLang(String lang) {
        this.lang = lang;
    }


}
