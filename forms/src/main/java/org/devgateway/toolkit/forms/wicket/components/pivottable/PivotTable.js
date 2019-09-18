
// TODO
//  - do we cache data?
//  - filtering by ranges not yet possible, see https://github.com/nicolaskruchten/pivottable/issues/191

var PivotTable = new function() {

    var _fields;

    this.init = function(opts, extraOpts) {
        _fields = opts.fields;

        var el = $("#" + opts.elementId);

        $.get({
            url: opts.dataUrl,
            dataType: "json"
        }).done(function (data) {
            if (data.length === 0) {
                el.html("No data.");
            } else {
                var localizedData = data.map(toLocalizedRow);

                var extraPivotUIOpts = getExtraPivotUIOpts(opts, extraOpts);

                var pivotUIOpts = $.extend({}, opts.pivotUIOpts, extraPivotUIOpts, {
                    aggregators: buildAggregators(opts.aggregatorNames, opts.language),
                    renderers: buildRenderers(opts.rendererNames, opts.language),
                    unusedAttrsVertical: true,
                    autoSortUnusedAttrs: false
                });

                el.pivotUI(localizedData, pivotUIOpts, false, opts.language);
            }
        }).fail(function () {
            el.html("Failed to load data.");
        });
    };

    /**
     * Returns any ui options for pivottable.js that are specific to one dataset.
     * @param opts
     * @param extraOpts
     * @returns ui options for pivottable.js
     */
    function getExtraPivotUIOpts(opts, extraOpts) {
        if (opts.dataset === "MarketPrice") {
            return pivotUIOptsForMarketPrices(opts, extraOpts);
        } else if (opts.dataset === "Production") {
            return pivotUIOptsForProduction(opts, extraOpts);
        } else {
            throw opts.dataset + " not supported";
        }
    }

    function pivotUIOptsForMarketPrices(opts, extraOpts) {
        var CROP_TYPE = "_cropType";
        var CROP_TYPE_NAME = "cropTypeName";
        var MARKET = "_market";
        var MARKET_NAME = "marketName";
        var DEPARTMENT = "department";
        var REGION = "region";
        var REGION_CODE = "regionCode";
        var DATE = "date";
        var WEEK_DAY = "weekDay";
        var MONTH = "month";
        var QUARTER = "quarter";
        var YEAR = "year";

        var derivers = $.extend({}, $.pivotUtilities.derivers);
        derivers.cropTypeIdToName = function(record) {
            return extraOpts.cropTypeNames[record[CROP_TYPE]];
        };
        derivers.marketIdToName = function(record) {
            return extraOpts.marketNames[record[MARKET]];
        };
        derivers.marketIdToDepartment = function(record) {
            return extraOpts.departmentNames[record[MARKET]];
        };
        derivers.marketIdToRegion = function(record) {
            return extraOpts.regionNames[record[MARKET]];
        };
        derivers.marketIdToRegionCode = function(record) {
            return extraOpts.regionCodes[record[MARKET]];
        };
        derivers.quarter = function(col) {
            return function(record) {
                var date = new Date(Date.parse(record[col]));
                if (isNaN(date)) {
                    return "";
                } else {
                    return Math.ceil((date.getMonth() + 1) / 3);
                }
            }
        };

        var extraFields = opts.extraFields;
        var dateCol = opts.fields[DATE];

        var derivedAttributes = {};
        derivedAttributes[extraFields[CROP_TYPE_NAME]] = derivers.cropTypeIdToName;
        derivedAttributes[extraFields[MARKET_NAME]] = derivers.marketIdToName;
        derivedAttributes[extraFields[DEPARTMENT]] = derivers.marketIdToDepartment;
        derivedAttributes[extraFields[REGION]] = derivers.marketIdToRegion;
        derivedAttributes[extraFields[REGION_CODE]] = derivers.marketIdToRegionCode;
        derivedAttributes[extraFields[WEEK_DAY]] = derivers.dateFormat(dateCol, "%w", false, opts.mthNames, opts.dayNames);
        derivedAttributes[extraFields[MONTH]] = derivers.dateFormat(dateCol, "%n", false, opts.mthNames, opts.dayNames);
        derivedAttributes[extraFields[QUARTER]] = derivers.quarter(dateCol);
        derivedAttributes[extraFields[YEAR]] = derivers.dateFormat(dateCol, "%y");

        var sortAs = $.pivotUtilities.sortAs;
        var sorters = {};
        sorters[extraFields[MONTH]] = sortAs(opts.mthNames);
        sorters[extraFields[WEEK_DAY]] = sortAs(opts.dayNames);

        return {
            derivedAttributes: derivedAttributes,
            sorters: sorters
        };
    }

    function pivotUIOptsForProduction(opts, extraOpts) {
        var YEAR = "_year";
        var CAMPAIGN = "campaign";
        var CROP_TYPE = "_cropType";
        var CROP_TYPE_NAME = "cropTypeName";
        var REGION = "_region";
        var REGION_NAME = "regionName";
        var REGION_CODE = "regionCode";

        var derivers = $.extend({}, $.pivotUtilities.derivers);
        derivers.campaign = function(record) {
            var year = record[YEAR];
            return year + "/" + (year + 1);
        };
        derivers.cropTypeIdToName = function(record) {
            return extraOpts.cropTypeNames[record[CROP_TYPE]];
        };
        derivers.regionIdToRegion = function(record) {
            return extraOpts.regionNames[record[REGION]];
        };
        derivers.regionIdToRegionCode = function(record) {
            return extraOpts.regionCodes[record[REGION]];
        };

        var extraFields = opts.extraFields;

        var derivedAttributes = {};
        derivedAttributes[extraFields[CAMPAIGN]] = derivers.campaign;
        derivedAttributes[extraFields[CROP_TYPE_NAME]] = derivers.cropTypeIdToName;
        derivedAttributes[extraFields[REGION_NAME]] = derivers.regionIdToRegion;
        derivedAttributes[extraFields[REGION_CODE]] = derivers.regionIdToRegionCode;

        return {
            derivedAttributes: derivedAttributes
        };
    }

    /**
     * Returns aggregators to use in pivottable.js.
     * @param names names of the aggregators
     * @param language language in which names were specified
     */
    function buildAggregators(names, language) {
        var ptLocale = $.pivotUtilities.locales[language] ? language : "en";
        return objSubset(names, $.pivotUtilities.locales[ptLocale].aggregators);
    }

    /**
     * Returns renderers to use in pivottable.js.
     * @param names names of the renderers
     * @param language language in which names were specified
     */
    function buildRenderers(names, language) {
        var ptLocale = $.pivotUtilities.locales[language] ? language : "en";
        return objSubset(names, $.pivotUtilities.locales[ptLocale].renderers);
    }

    /**
     * Given an object, build a another object using only the specified fields.
     * @param fields array of field names
     * @param obj source object
     * @returns subset of the object
     */
    function objSubset(fields, obj) {
        return fields.reduce(function(acc, field) {
            acc[field] = obj[field];
            return acc;
        }, {});
    }

    /**
     * Maps internal names to localized labels.
     * @param row object that uses internal names
     * @return object that uses localized labels
     */
    function toLocalizedRow(row) {
        var localizedRow = {};
        for (var field in _fields) {
            if (_fields.hasOwnProperty(field)) {
                var label = _fields[field];
                var value = row[field];
                if (value) {
                    localizedRow[label] = value;
                }
            }
        }
        return localizedRow;
    }
}();
