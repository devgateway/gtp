
// TODO
//  1 do we cache data?
//  3 even data from some columns could be localized (ex Crop column has MAÏS/CORN)
//  4 decide on initial setup of the pivot table
//  9 filtering by ranges not yet possible, see https://github.com/nicolaskruchten/pivottable/issues/191

var PivotTable = new function() {

    var _fields;

    this.init = function(opts, extraOpts) {
        _fields = opts.fields;

        var el = $("#" + opts.elementId);

        $.get({
            url: opts.dataUrl,
            dataType: "json"
        }).done(function (data) {
            var localizedData = data.map(toLocalizedRow);

            var extraPivotUIOpts = {};
            if (opts.dataset === "MarketPrice") {
                extraPivotUIOpts = pivotUIOptsForMarketPrices(opts, extraOpts);
            }

            var pivotUIOpts = $.extend({}, opts.pivotUIOpts, extraPivotUIOpts, {
                aggregators: buildAggregators(opts.aggregatorNames, opts.language),
                renderers: buildRenderers(opts.rendererNames, opts.language),
                unusedAttrsVertical: true,
                autoSortUnusedAttrs: false
            });

            el.pivotUI(localizedData, pivotUIOpts, false, opts.language);
        }).fail(function () {
            el.html("Failed to load dataset.");
        });
    };

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
        var dateCol = _fields[DATE];

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

    function buildAggregators(names, language) {
        var ptLocale = $.pivotUtilities.locales[language] ? language : "en";
        return objSubset(names, $.pivotUtilities.locales[ptLocale].aggregators);
    }

    function buildRenderers(names, language) {
        var ptLocale = $.pivotUtilities.locales[language] ? language : "en";
        return objSubset(names, $.pivotUtilities.locales[ptLocale].renderers);
    }

    function objSubset(names, obj) {
        return names.reduce(function(acc, name) {
            acc[name] = obj[name];
            return acc;
        }, {});
    }

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
