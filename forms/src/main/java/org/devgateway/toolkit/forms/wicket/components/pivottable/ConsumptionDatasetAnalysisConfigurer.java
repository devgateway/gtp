package org.devgateway.toolkit.forms.wicket.components.pivottable;

import static java.util.stream.Collectors.toMap;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.ipar.Department;
import org.devgateway.toolkit.persistence.dao.ipar.categories.CropType;
import org.devgateway.toolkit.persistence.service.category.CropTypeService;
import org.devgateway.toolkit.persistence.service.category.DepartmentService;

/**
 * @author Octavian Ciubotaru
 */
public class ConsumptionDatasetAnalysisConfigurer implements DatasetAnalysisConfigurer {

    private DepartmentService departmentService;

    private CropTypeService cropTypeService;

    public ConsumptionDatasetAnalysisConfigurer(DepartmentService departmentService, CropTypeService cropTypeService) {
        this.departmentService = departmentService;
        this.cropTypeService = cropTypeService;
    }

    @Override
    public List<String> getCols() {
        return ImmutableList.of("cropTypeName");
    }

    @Override
    public List<String> getRows() {
        return ImmutableList.of("departmentName");
    }

    @Override
    public List<String> getVals() {
        return ImmutableList.of("weeklyConsumption");
    }

    @Override
    public String getAggregatorName() {
        return "average";
    }

    @Override
    public List<PivotField> getExtraFields() {
        return ImmutableList.of(
                new PivotField("cropTypeName", true, false),
                new PivotField("departmentName", true, false),
                new PivotField("regionName", true, false));
    }

    @Override
    public Object getExtraOpts(String language) {
        ConsumptionOpts opts = new ConsumptionOpts();

        List<Department> departments = departmentService.findAll();

        opts.setDepartmentNames(departments.stream().collect(toMap(Department::getId, Department::getName)));

        opts.setRegionNames(departments.stream().collect(toMap(Department::getId, d -> d.getRegion().getName())));
        List<CropType> cropTypes = cropTypeService.findAll();
        opts.setCropTypeNames(cropTypes.stream().collect(toMap(CropType::getId, ct -> ct.getLocalizedLabel(language))));

        return opts;
    }
}
