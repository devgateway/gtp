package org.devgateway.toolkit.persistence.dto;


import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.ipar.IndicatorMetadata;
import org.devgateway.toolkit.persistence.util.Constants;

public class IndicatorMetadataDTO {

    private Long id;

    private String intro;

    private String indicatorName;

    private Integer indicatorType;

    private String ansdLink;

    private String source;

    public IndicatorMetadataDTO(final IndicatorMetadata im, final String lang) {
        this.id = im.getId();
        this.intro = (StringUtils.isNotEmpty(lang) && lang.equalsIgnoreCase(Constants.LANG_FR))
                || StringUtils.isBlank(im.getIntro())
                ? im.getIntroFr() : im.getIntro();
        this.indicatorName = im.getIndicatorName();
        this.indicatorType = im.getIndicatorType();
        this.ansdLink = im.getAnsdLink();
        this.source = im.getSource();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public Integer getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(Integer indicatorType) {
        this.indicatorType = indicatorType;
    }

    public String getAnsdLink() {
        return ansdLink;
    }

    public void setAnsdLink(String ansdLink) {
        this.ansdLink = ansdLink;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
