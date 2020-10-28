package org.devgateway.toolkit.web.rest.controller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.FileContent;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.categories.River;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayer;
import org.devgateway.toolkit.persistence.dao.indicator.RainfallMapLayerType;
import org.devgateway.toolkit.persistence.dao.indicator.RiverLevel;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.dao.reference.RiverLevelReference;
import org.devgateway.toolkit.persistence.dao.reference.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.dto.ChartsData;
import org.devgateway.toolkit.persistence.dto.WaterConfig;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChart;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartData;
import org.devgateway.toolkit.persistence.dto.drysequence.DrySequenceChartFilter;
import org.devgateway.toolkit.persistence.dto.drysequence.MonthDecadalDaysWithRain;
import org.devgateway.toolkit.persistence.dto.rainfall.DecadalInstantRainLevel;
import org.devgateway.toolkit.persistence.dto.rainfall.MonthDecadalRainLevel;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChart;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartData;
import org.devgateway.toolkit.persistence.dto.rainfall.RainLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.rainfall.ReferenceLevels;
import org.devgateway.toolkit.persistence.dto.rainfallMap.RainMap;
import org.devgateway.toolkit.persistence.dto.rainfallMap.RainMapConfig;
import org.devgateway.toolkit.persistence.dto.rainfallMap.RainMapFilter;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChart;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartConfig;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartData;
import org.devgateway.toolkit.persistence.dto.riverlevel.RiverLevelChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonChart;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartConfig;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartData;
import org.devgateway.toolkit.persistence.dto.season.SeasonChartFilter;
import org.devgateway.toolkit.persistence.dto.season.SeasonPrediction;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Octavian Ciubotaru
 */
public class SampleWaterData {

    private final List<PluviometricPost> posts;
    private final RiverStation riverStationBakel;
    private final RiverStation riverStationFaleme;

    private final List<RiverStation> riverStations;

    private final ChartsData chartsData;
    private final PluviometricPost postKolda;
    private final PluviometricPost postFongolimbi;

    private final DecadalRainfallMap decadalRainfallMapAny;

    public SampleWaterData(SampleCommonData commonData) {
        postKolda = new PluviometricPost(1L);
        postKolda.setLabel("Kolda");
        postKolda.setLatitude(12.88);
        postKolda.setLongitude(-14.97);
        postKolda.setDepartment(commonData.getDepartmentKedougou());

        postFongolimbi = new PluviometricPost(2L);
        postFongolimbi.setLabel("Fongolimbi");
        postFongolimbi.setLatitude(12.42);
        postFongolimbi.setLongitude(-12.02);
        postFongolimbi.setDepartment(commonData.getDepartmentKedougou());

        posts = ImmutableList.of(postKolda, postFongolimbi);

        River riverSenegal = new River(1L, "Sénégal");
        River riverFaleme = new River(2L, "Faleme");

        riverStationBakel = new RiverStation(1L);
        riverStationBakel.setAlertLevel(1000);
        riverStationBakel.setName("Bakel");
        riverStationBakel.setRiver(riverSenegal);

        riverStationFaleme = new RiverStation(2L);
        riverStationFaleme.setAlertLevel(1300);
        riverStationFaleme.setName("Faleme");
        riverStationFaleme.setRiver(riverFaleme);

        riverStations = ImmutableList.of(riverStationBakel, riverStationFaleme);

        decadalRainfallMapAny = new DecadalRainfallMap();
        RainfallMapLayer layer = new RainfallMapLayer(RainfallMapLayerType.ABNORMAL_POLYGON);
        decadalRainfallMapAny.setLayers(ImmutableSet.of(layer));
        layer.setFile(ImmutableSet.of(
                new FileMetadata(1L, "layer.json", MediaType.APPLICATION_JSON_VALUE,
                        new FileContent("{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"ZLEVEL\":100.0},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[-12.208333333333334,14.45],[-12.205,14.45]]]}}]}".getBytes()))));

        chartsData = new ChartsData(commonData.getCommonConfig(), getWaterConfig(), getRainLevelChart(), getRainMap(),
                getDrySequenceChart(), getSeasonChart(), getRiverLevelChart());
    }

    public ChartsData getChartsData() {
        return chartsData;
    }

    private WaterConfig getWaterConfig() {
        return new WaterConfig(posts);
    }

    private RainLevelChart getRainLevelChart() {
        RainLevelChartConfig config = new RainLevelChartConfig(
                ImmutableList.of(2019, 2020),
                posts.stream().map(AbstractPersistable::getId).collect(toList()));

        int year = config.getYears().last();
        RainLevelChartFilter filter = new RainLevelChartFilter(ImmutableList.of(year), postKolda.getId());

        List<DecadalInstantRainLevel> levels = ImmutableList.of(
                new DecadalInstantRainLevel(year, Month.MAY, Decadal.FIRST, 2),
                new DecadalInstantRainLevel(year, Month.MAY, Decadal.SECOND, 0),
                new DecadalInstantRainLevel(year, Month.MAY, Decadal.THIRD, 1),
                new DecadalInstantRainLevel(year, Month.JUNE, Decadal.FIRST, 3),
                new DecadalInstantRainLevel(year, Month.JUNE, Decadal.SECOND, 4),
                new DecadalInstantRainLevel(year, Month.JUNE, Decadal.THIRD, 2));

        ReferenceLevels refLevelsFor2020 = new ReferenceLevels(1981, 2020, 1981, 2010, ImmutableList.of(
                new MonthDecadalRainLevel(Month.MAY, Decadal.FIRST, 1),
                new MonthDecadalRainLevel(Month.MAY, Decadal.SECOND, 1),
                new MonthDecadalRainLevel(Month.MAY, Decadal.THIRD, 2),
                new MonthDecadalRainLevel(Month.JUNE, Decadal.FIRST, 2),
                new MonthDecadalRainLevel(Month.JUNE, Decadal.SECOND, 3),
                new MonthDecadalRainLevel(Month.JUNE, Decadal.THIRD, 3)));
        List<ReferenceLevels> referenceLevels = ImmutableList.of(refLevelsFor2020);

        RainLevelChartData data = new RainLevelChartData(levels, referenceLevels);

        return new RainLevelChart(config, filter, data);
    }

    private RainMap getRainMap() {
        RainMapConfig config = new RainMapConfig(ImmutableSortedSet.of(2019, 2020));
        return new RainMap(config, new RainMapFilter(config.getYears().last(), Month.OCTOBER, Decadal.THIRD,
                RainfallMapLayerType.ABNORMAL_POLYGON));
    }

    public DecadalRainfallMap getDecadalRainfallMapAny() {
        return decadalRainfallMapAny;
    }

    private DrySequenceChart getDrySequenceChart() {
        int year = 2020;
        DrySequenceChartFilter filter = new DrySequenceChartFilter(year, postKolda.getId());

        DrySequenceChartData data = new DrySequenceChartData(ImmutableList.of(
                new MonthDecadalDaysWithRain(year, Month.MAY, Decadal.FIRST, 2),
                new MonthDecadalDaysWithRain(year, Month.MAY, Decadal.SECOND, 1),
                new MonthDecadalDaysWithRain(year, Month.MAY, Decadal.THIRD, 4)));

        return new DrySequenceChart(filter, data);
    }

    private SeasonChart getSeasonChart() {
        SeasonChartConfig config = new SeasonChartConfig(ImmutableList.of(2018, 2019, 2020));

        Integer year = config.getYears().last();
        SeasonChartFilter filter = new SeasonChartFilter(year);

        SeasonChartData data= new SeasonChartData(1981, 2020, 1981, 2010, ImmutableList.of(
                new SeasonPrediction(1L, MonthDay.of(Month.MAY, 15), LocalDate.of(year, Month.MAY, 23)),
                new SeasonPrediction(2L, MonthDay.of(Month.MAY, 10), LocalDate.of(year, Month.MAY, 2))));

        return new SeasonChart(config, filter, data);
    }

    private RiverLevelChart getRiverLevelChart() {
        RiverLevelChartConfig config = new RiverLevelChartConfig(
                ImmutableSortedSet.of(HydrologicalYear.fromInt(2019), HydrologicalYear.fromInt(2020)),
                riverStations);

        HydrologicalYear year = config.getYears().last();
        RiverLevelChartFilter filter = new RiverLevelChartFilter(ImmutableSortedSet.of(year),
                riverStationBakel.getId());

        RiverStationYearlyLevels yl = new RiverStationYearlyLevels(riverStationBakel, year);
        yl.addLevel(new RiverLevel(MonthDay.of(Month.MAY, 1), BigDecimal.valueOf(803.5)));
        yl.addLevel(new RiverLevel(MonthDay.of(Month.MAY, 2), BigDecimal.valueOf(820.7)));
        yl.addLevel(new RiverLevel(MonthDay.of(Month.MAY, 3), BigDecimal.valueOf(840.1)));

        RiverStationYearlyLevelsReference ylRef = new RiverStationYearlyLevelsReference(riverStationBakel,
                HydrologicalYear.fromInt(1987));
        ylRef.addLevel(new RiverLevelReference(MonthDay.of(Month.MAY, 1), BigDecimal.valueOf(1301)));
        ylRef.addLevel(new RiverLevelReference(MonthDay.of(Month.MAY, 2), BigDecimal.valueOf(1324)));
        ylRef.addLevel(new RiverLevelReference(MonthDay.of(Month.MAY, 3), BigDecimal.valueOf(1331)));

        RiverLevelChartData data = new RiverLevelChartData(ImmutableList.of(yl), ImmutableList.of(ylRef));

        return new RiverLevelChart(config, filter, data);
    }
}
