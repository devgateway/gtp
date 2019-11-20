package org.devgateway.toolkit.web.rest.controller.filter;

import java.util.TreeSet;

public class IndicatorFilterPagingRequest extends DefaultFilterPagingRequest {

    private Double minPercentage;

    private Double maxPercentage;

    private TreeSet<Integer> awGroup;

    private TreeSet<Integer> awGroupType;

    private TreeSet<Integer> ageGroup;

    private TreeSet<Integer> methodOfEnforcement;

    private TreeSet<Integer> indexType;

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
}
