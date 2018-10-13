/**
 * @preserve jQuery PeriodPicker plugin v6.1.8
 * @homepage http://xdsoft.net/jqplugins/periodpicker/
 * @copyright (c) 2018 xdsoft.net Chupurnov Valeriy
 * @license PRO http://xdsoft.net/jqplugins/periodpicker/license/
 */
(function ($, window, document) {
    'use strict';
    var uniqueid = 0,
        timer,
        getClient = function (e) {
            return (e.originalEvent && e.originalEvent.touches && e.originalEvent.touches[0]) ? e.originalEvent.touches[0] : e;
        },
        toggle = function (items, show) {
            if ((show || show === undefined) && items.is(':hidden')) {
                items.each(function () {
                    this.style.display = '';
                });
            } else if (!show) {
                items.hide();
            }
        };

    function TimeWrapper(str, format, date) {
        var that = date || new Date(), value;

        that.isTW = true;
        that.weekdays = function (start) {
            var weekdays = moment.weekdaysShort(), ret, i;
            ret = weekdays.splice(1);
            ret[6] = weekdays[0];
            weekdays = ret; // [m,t,w,th,f,s,sn]
            ret = weekdays.splice(start - 1);

            for (i = 0; i < start - 1; i += 1) {
                ret.push(weekdays[i]);
            }

            return ret;
        };
        that.clone = function (y, m, d, h, i, s) {
            var tmp = new TimeWrapper(false, false, new Date(that.getTime()));
            if (h) {
                tmp.setHours(h);
            }

            if (i) {
                tmp.setMinutes(i);
            }

            if (s) {
                tmp.setSeconds(s);
            }

            if (y) {
                tmp.setFullYear(y);
            }

            if (m) {
                tmp.setMonth(m);
            }

            if (d) {
                tmp.setDate(d);
            }

            return tmp;
        };
        that.inRange = function (value, range) {
            return moment(value).isBetween(range[0], range[1], 'day') || moment(value).isSame(range[0], 'day') || moment(value).isSame(range[1], 'day');
        };
        that.isValid = function () {
            if (Object.prototype.toString.call(that) !== "[object Date]") {
                return false;
            }
            return !isNaN(that.getTime());
        };
        that.parseStr = function (str, format) {
            var time;

            if (typeof str === 'string') {
                time = moment(str, format);
            } else {
                if ($.type(str) === 'date') {
                    time = new TimeWrapper(0, 0, new Date(str.getTime()));
                } else {
                    time = str;
                }
            }

            if (time && time.isValid()) {
                that = !time.isTW ? new TimeWrapper(0, 0, time.toDate()) : time;
                return that;
            }

            return null;
        };
        that.isEqualDate = function (date1, date2) {
            if (!date1 || !date1.isValid() || !date2 || !date2.isValid()) {
                return false;
            }
            return (date1.getDate() === date2.getDate() && date1.getMonth() === date2.getMonth() && date1.getMonth() === date2.getMonth());
        };
        that.format = function (format) {
            value = moment(that).format(format);
            return new RegExp('^[0-9]+$').test(value) ? parseInt(value, 10) : value;
        };

        that.countDaysInMonth = function () {
            return new Date(that.getFullYear(), that.getMonth() + 1, 0).getDate();
        };

        if (str && format) {
            that.parseStr(str, format);
        }

        return that;
    }

    function PeriodPicker(startinput, opt) {
        var that = this,
            date,
            values = [],
            options = $.extend(true, {}, $.fn.periodpicker.defaultOptions, opt),
            i18n = function (key) {
                return (options.i18n[options.lang] !== undefined &&
                    options.i18n[options.lang][key] !== undefined) ? options.i18n[options.lang][key] : key;
            };

        /**
         * @type {*|jQuery}
         */
        that.picker = $('<div class="period_picker_box xdsoft_noselect" style="">\
                <div class="period_picker_resizer"></div>\
                    <div class="period_picker_head">\
                        <span class="period_picker_head_title"></span>\
                        <span class="period_picker_max_min" title="' + i18n('Open fullscreen') + '"></span>\
                        <span class="period_picker_close" title="' + i18n('Close') + '"></span>\
                    </div>\
                    <div class="period_picker_years">\
                        <div class="period_picker_years_inner">\
                            <div class="period_picker_years_selector">\
                                <div class="period_picker_years_selector_container" style="width: 5960px; left: 0;">\
                                </div>\
                            </div>\
                        </div>\
                    </div>\
                    <div class="period_picker_work">\
                        <a href="" class="xdsoft_navigate xdsoft_navigate_prev"></a>\
                        <div class="period_picker_timepicker_box">\
                            <input data-index="0" class="timepicker" type="hidden"/>\
                        </div>\
                    <div class="period_picker_days">\
                        <table>\
                            <tbody>\
                            </tbody>\
                        </table>\
                    </div>\
                    <div class="period_picker_timepicker_box">\
                        <input data-index="1"  class="timepicker" type="hidden">\
                    </div>\
                    <a href="" class="xdsoft_navigate xdsoft_navigate_next"></a>\
                </div>\
                <div class="period_picker_submit_shadow"></div>\
                <div class="period_picker_submit_dates">\
                    <span class="period_picker_from_time_block period_picker_time">\
                        <span class="input_box"><input data-index="0"  class="input_control period_picker_from_time"></span>\
                    </span>\
                    <span class="period_picker_from_block period_picker_date">\
                        <span class="input_box"><input class="input_control period_picker_from" maxlength="10"></span>\
                    </span>\
                    <span class="period_picker_date_separator">&#8212;</span>\
                    <span class="period_picker_to_block period_picker_date">\
                        <span class="input_box"><input class="input_control period_picker_to" maxlength="10"></span>\
                    </span>\
                    <span class="period_picker_to_time_block period_picker_time">\
                        <span class="input_box"><input data-index="1" class="input_control period_picker_to_time"></span>\
                    </span>\
                    <button class="period_picker_show period_picker_ok" role="button" type="button">\
                        <span class="button_text">' + i18n('OK') + '</span>\
                    </button>\
                    <button class="period_picker_show period_picker_today" role="button" type="button">\
                        <span class="button_text">' + i18n('Today') + '</span>\
                    </button>\
                    <button class="period_picker_show period_picker_clear" role="button" type="button">\
                        <span class="button_text">' + i18n('Clear') + '</span>\
                    </button>\
                </div>\
            </div>');
        /**
         * @type {*|jQuery}
         */
        that.pickerdays = that.picker.find('.period_picker_days');
        that.calendarbox = that.pickerdays.find('> table > tbody');
        that.yearsline = that.picker.find('.period_picker_years_selector_container');
        that.yearslineparent = that.picker.find('.period_picker_years_selector');
        that.timepicker = that.picker.find('.period_picker_timepicker_box');

        /**
         * @type {*|jQuery}
         */
        that.button = $('<div class="period_picker_input ' + options.buttonClassSuffix + '" role="button">' +
            '<span class="period_button_text">' +
                '<span class="period_button_content_wrapper">' +
                    '<span class="period_button_content">' +
                        '<span class="icon_calendar"></span>' +
                        '<span class="period_button_content_body">' + i18n(options.norange ? 'Choose date' : 'Choose period') + '</span>' +
                        '<span class="icon_clear"></span>' +
                    '</span>' +
                '</span>' +
            '</span>' +
        '</div>');




        that.startinput = options.start ? $(options.start) : $(startinput);
        that.endinput = $(options.end);

        that.startinput.attr('autocomplete', 'off');
        that.endinput.attr('autocomplete', 'off');

        that.periodtime = [[]];
        that.period = [];
        that.director = 0;

        var applyOptions = function () {
                var addHandler;

                moment.locale(options.lang);

                that.picker.toggleClass('period_picker_maximize', options.fullsize);

                toggle(that.picker.find('.period_picker_resizer'), options.resizeButton);
                toggle(that.picker.find('.period_picker_head_title').html(i18n(options.norange ? 'Select date' : 'Select period')), options.title);
                toggle(that.picker.find('.period_picker_max_min'), options.fullsizeButton);
                toggle(that.picker.find('.period_picker_close'), options.closeButton && !options.inline);
                toggle(that.picker.find('.period_picker_years'), options.yearsLine);
                toggle(that.picker.find('.xdsoft_navigate'), options.navigate);

                toggle(that.picker.find('.period_picker_timepicker_box').eq(0), options.timepicker && $.fn.TimePicker !== undefined && !(options.norange && options.timePickerInRight));
                toggle(that.picker.find('.period_picker_timepicker_box').eq(1), options.timepicker && $.fn.TimePicker !== undefined && (!options.norange || options.timePickerInRight));

                that.picker.find('.period_picker_timepicker_box').eq(0).find('input').data('index', 0);
                that.picker.find('.period_picker_timepicker_box').eq(1).find('input').data('index', 1);

                if (options.norange && options.timePickerInRight) {
                    that.picker.find('.period_picker_timepicker_box').eq(1).find('input').data('index', 0);
                    that.picker.find('.period_picker_timepicker_box').eq(0).find('input').data('index', 1);
                }

                that.picker.find('.period_picker_date,.period_picker_date_separator').css('visibility', options.showDatepickerInputs ? '' : 'hidden');

                toggle(that.picker.find('.period_picker_from_time_block'), options.timepicker && $.fn.TimePicker !== undefined);
                that.picker.find('.period_picker_from_time_block').css('visibility', options.showTimepickerInputs ? '' : 'hidden');

                toggle(that.picker.find('.period_picker_to_time_block'), options.timepicker && $.fn.TimePicker !== undefined && !options.norange);
                that.picker.find('.period_picker_to_time_block').css('visibility', options.showTimepickerInputs ? '' : 'hidden');

                toggle(that.picker.find('.period_picker_ok'), options.okButton && !options.inline);
                toggle(that.picker.find('.period_picker_today'), options.todayButton);
                toggle(that.picker.find('.period_picker_clear'), options.clearButton);

                toggle(that.button.find('.period_button_content .icon_clear'), options.clearButtonInButton);

                if (options.tabIndex !== false) {
                    that.button.attr('tabindex', options.tabIndex);
                }

                if (options.withoutBottomPanel || (!options.todayButton && !options.clearButton && (!options.okButton || options.inline) && !options.showDatepickerInputs && (!options.showTimepickerInputs || !options.timepicker || $.fn.TimePicker === undefined))) {
                    that.picker.addClass('without_bottom_panel');
                    options.withoutBottomPanel = true;
                    options.someYOffset = 0;
                }

                if (!options.yearsLine) {
                    that.picker.addClass('without_yearsline');
                }

                if (!options.title && !options.fullsizeButton && !(options.closeButton && !options.inline)) {
                    that.picker.addClass('without_header');
                }

                if (options.timepicker && $.fn.TimePicker !== undefined) {
                    that.picker.addClass('with_first_timepicker');
                }

                if (options.timepicker && $.fn.TimePicker !== undefined && !options.norange) {
                    that.picker.addClass('with_second_timepicker');
                }

                if (options.animation) {
                    that.picker.addClass('animation');
                }

                that.picker.removeClass('xdsoft_norange xdsoft_norange_timepickerinright');
                if (options.norange) {
                    that.picker.addClass('xdsoft_norange');
                    if (options.timePickerInRight) {
                        that.picker.addClass('xdsoft_norange_timepickerinright');
                    }
                }

                if (options.inline) {
                    that.picker.addClass('xdsoft_inline');
                }

                addHandler = function (name) {
                    var i, finded = false;
                    if (options[name] !== undefined && $.isFunction(options[name])) {
                        for (i = 0; i < that[name].length; i += 1) {
                            if (options[name] === that[name][i]) {
                                finded = true;
                                break;
                            }
                        }
                        if (!finded) {
                            that[name].push(options[name]);
                        }
                    }
                };

                addHandler('onAfterShow');
                addHandler('onAfterHide');
                addHandler('onAfterRegenerate');

                that.maxdate = options.maxDate ? new TimeWrapper(options.maxDate, options.formatDate) : false;
                that.mindate = options.minDate ? new TimeWrapper(options.minDate, options.formatDate) : false;

                if (options.yearsPeriod[0] === null && that.mindate) {
                    options.yearsPeriod[0] = that.mindate.getFullYear();
                }
                if (options.yearsPeriod[1] === null && that.maxdate) {
                    options.yearsPeriod[1] = that.maxdate.getFullYear();
                }

                if (options.yearsPeriod[0] === null) {
                    options.yearsPeriod[0] = options.yearsPeriod[1] ? options.yearsPeriod[1] - 40 : (new Date()).getFullYear() - 20;
                }
                if (options.yearsPeriod[1] === null) {
                    options.yearsPeriod[1] = options.yearsPeriod[0] + 40;
                }


                that.monthcount = options.cells[0] * options.cells[1];

                var cloneMaxDate;
                if (that.maxdate) {
                    cloneMaxDate = that.maxdate.clone(null, that.maxdate.clone().getMonth() - options.cells[0] * options.cells[1] + 1)
                }

                if (options.startYear === null) {
                    if (that.mindate) {
                        options.startYear = that.mindate.getFullYear()
                    } else if (that.maxdate) {
                        options.startYear = cloneMaxDate.getFullYear()
                    } else {
                        options.startYear = (new Date()).getFullYear()
                    }
                }

                if (options.startMonth === null) {
                    if (that.mindate) {
                        options.startMonth = that.mindate.getMonth() + 1
                    } else if (that.maxdate) {
                        options.startMonth = cloneMaxDate.getMonth() + 1
                    } else {
                        options.startMonth = (new Date()).getMonth() + 1
                    }
                }


                that.picker.css({
                    width: options.cells[1] * options.monthWidthInPixels + ((options.timepicker && $.fn.TimePicker) ? 87 * (!options.norange ? 2 : 1) : 0) + 50,
                    height: (options.cells[0] * options.monthHeightInPixels) + options.someYOffset
                });
            },

            returnPeriod = function () {
                that.picker.find('input.period_picker_from').val(that.period !== undefined ? that.period : '');
                that.picker.find('input.period_picker_to').val(that.period[1] !== undefined ? that.period[1] : that.picker.find('input.period_picker_from').val());
            },
            moveTimeToDate = function () {
                if (options.timepicker && that.periodtime.length && that.periodtime[0].length) {
                    if (that.period[0] !== null && that.period[0].format  && that.periodtime[0][0].format) {
                        that.period[0].setSeconds(that.periodtime[0][0].getSeconds());
                        that.period[0].setMinutes(that.periodtime[0][0].getMinutes());
                        that.period[0].setHours(that.periodtime[0][0].getHours());
                    }
                    if (that.periodtime[0][1] !== null && that.period[1] !== null && that.period[1].format && that.periodtime[0][1].format) {
                        that.period[1].setSeconds(that.periodtime[0][1].getSeconds());
                        that.period[1].setMinutes(that.periodtime[0][1].getMinutes());
                        that.period[1].setHours(that.periodtime[0][1].getHours());
                    }
                }
            },

            syncTimesInputs = function () {
                if (options.timepicker && $.fn.TimePicker !== undefined) {
                    var tw = new TimeWrapper(),
                        tinputs = that.timepicker.find('input.timepicker'),
                        nativeinputs = that.picker.find('.period_picker_submit_dates .period_picker_time input');

                    if (that.periodtime[0][0]) {
                        if ($.fn.TimePicker) {
                            tinputs.eq(0).TimePicker('setValue', that.periodtime[0][0], true);
                        }
                        if (!nativeinputs.eq(0).is(':focus')) {
                            nativeinputs.eq(0).val(that.periodtime[0][0].format(options.timepickerOptions.inputFormat));
                        }
                    }

                    if (!options.norange && that.periodtime[0][1]) {
                        if ($.fn.TimePicker) {
                            tinputs.eq(1).TimePicker('setValue', that.periodtime[0][1], true);
                        }
                        if (!nativeinputs.eq(1).is(':focus')) {
                            nativeinputs.eq(1).val(that.periodtime[0][1].format(options.timepickerOptions.inputFormat));
                        }
                    }

                    if (!options.norange && options.useTimepickerLimits && tw.isEqualDate(that.period[0], that.period[1])) {
                        if (that.currentTimepickerIndex === 0) {
                            tinputs.eq(1).TimePicker('setMin', tinputs.eq(0).val()).TimePicker('setMin', false);
                        } else {
                            tinputs.eq(0).TimePicker('setMax', tinputs.eq(1).val()).TimePicker('setMax', false);
                        }
                    }
                }
            },

            getInputsValue = function () {
                var result = [], format;
                syncTimesInputs();
                if (that.startinput.length && that.period && that.period.length) {

                    moveTimeToDate();

                    format = options.timepicker ? options.formatDateTime : options.formatDate;
                    if (that.period[0] && that.period[0].format) {
                        result.push(that.period[0].format(format));
                    }
                    if (that.period[1] && that.period[1].format) {
                        result.push(that.period[1].format(format));
                    }
                }
                return result;
            },
            __safecall = function (event) {
                if (options[event] && $.isFunction(options[event])) {
                    if (options[event].call(that) === false) {
                        return false;
                    }
                }
            },

            setInputsValue = function () {
                var result = getInputsValue();
                if (result.length) {
                    if (result[0] && that.startinput.val() !== result[0]) {
                        that.startinput.val(result[0]);
                    }
                    if (result[1] && that.endinput.val() !== result[1]) {
                        that.endinput.val(result[1]);
                    }
                } else {
                    that.startinput.val('');
                    that.endinput.val('');
                }

                if (that.oldStringRange !== result.join('-')) {
                    that.oldStringRange = result.join('-');
                    that.startinput.trigger('change');
                    that.endinput.trigger('change');

                    if (result[0]) {
                        __safecall('onChange');
                        if (result[1] && result[0] !== result[1]) {
                            __safecall('onChangePeriod');
                        }
                    }
                }
            },

            getLabel = function () {
                var result = [], formats;
                if (that.period.length) {
                    moveTimeToDate();

                    formats = !options.timepicker ? [
                        options.formatDecoreDateWithYear || options.formatDecoreDate || options.formatDate,
                        options.formatDecoreDateWithoutMonth || options.formatDecoreDate || options.formatDate,
                        options.formatDecoreDate || options.formatDate,
                        options.formatDate
                    ] : [
                        options.formatDecoreDateTimeWithYear || options.formatDecoreDateTime || options.formatDateTime,
                        options.formatDecoreDateTimeWithoutMonth || options.formatDecoreDateTime || options.formatDateTime,
                        options.formatDecoreDateTime || options.formatDateTime,
                        options.formatDateTime
                    ];

                    if (that.period[1] === undefined || !that.period[1] || that.period[1].format === undefined || !that.period[1].format || that.period[0].format(formats[3]) === that.period[1].format(formats[3])) {
                        result.push(that.period[0].format(formats[0]));
                    } else {
                        result.push(that.period[0].format(that.period[0].format('YYYY') !== that.period[1].format('YYYY') ? formats[0] : (that.period[0].format('M') !== that.period[1].format('M') ? formats[2] : formats[1])));
                        result.push(that.period[1].format(formats[0]));
                    }
                }
                return result;
            },
            setLabel = function () {
                var result = getLabel(), button = that.button;
                if (result.length) {
                    if (result.length === 1) {
                        button.find('.period_button_content_body').html(result[0]);
                    } else {
                        button.find('.period_button_content_body').html('<span>' +
                            result[0] +
                            '</span>' +
                            '<span class="period_button_dash">&#8212;</span>' +
                            '<span>' + result[1] + '</span>');
                    }
                    if (options.clearButtonInButton) {
                        toggle(button.find('.period_button_content .icon_clear'), true);
                    }
                } else {
                    button.find('.period_button_content_body').html(i18n(options.norange ? 'Choose date' : 'Choose period'));
                    if (options.clearButtonInButton) {
                        setTimeout(function () {
                            toggle(button.find('.period_button_content .icon_clear'), false);
                        }, options.defaultTimeout * 2);
                    }
                }
            },

            highlightPeriod = function () {
                var date = new TimeWrapper();

                if (!that.picker.is(':hidden')) {
                    that.picker.find('.period_picker_cell.period_picker_selected').removeClass('period_picker_selected');
                    if (that.period.length) {
                        that.picker.find('.period_picker_cell').each(function () {
                            var current = date.parseStr($(this).data('date'), options.formatDate);
                            if (date.inRange(current, that.period)) {
                                $(this).addClass('period_picker_selected');
                            }
                        });
                        that.picker.find('.period_picker_years_period').css({
                            width: Math.floor((options.yearSizeInPixels / 365) * Math.abs(moment(that.period[1]).diff(that.period[0], 'day'))) + 'px',
                            left: Math.floor((options.yearSizeInPixels / 365) * -(moment([options.yearsPeriod[0], 1, 1]).diff(that.period[0], 'day')))
                        });
                        that.picker.find('input.period_picker_from:not(:focus)').val((that.period[0] !== undefined && that.period[0]) ? that.period[0].format(options.formatDate) : '');
                        that.picker.find('input.period_picker_to:not(:focus)').val((that.period[1] !== undefined && that.period[1]) ? that.period[1].format(options.formatDate) : that.picker.find('input.period_picker_from').val());

                        that.picker.find('input.period_picker_from:not(:focus),input.period_picker_to:not(:focus)').trigger('change');
                    } else {
                        that.picker.find('input.period_picker_from:not(:focus),input.period_picker_to:not(:focus)').val('');
                    }
                }

                setLabel();
                setInputsValue();
            },
            addRangeTime = function (value1, value2) {
                var date = new TimeWrapper();

                that.periodtime[0][0] = date.parseStr(value1, options.timepickerOptions.inputFormat);
                if (!options.norange) {
                    that.periodtime[0][1] = date.parseStr(value2, options.timepickerOptions.inputFormat);
                    if (that.periodtime[0][0] === null && that.periodtime[0][1]) {
                        that.periodtime[0][0] = that.periodtime[0][1];
                    }
                } else {
                    that.periodtime[0][1] = that.periodtime[0][0];
                }

                if (that.periodtime[0][0] === null) {
                    that.periodtime[0] = [];
                }
                setLabel();
                setInputsValue();
            },
            addRange = function (value) {
                that.oldStringRange = getInputsValue().join('-');

                that.currentTimepickerIndex = 0;
                var date = new TimeWrapper(), buff;
                if (options.norange) {
                    that.director = 0;
                }

                var oldrange = [
                    that.period[0] ? that.period[0].clone() : undefined,
                    that.period[1] ? that.period[1].clone() : undefined,
                ];

                if ($.isArray(value)) {
                    that.period = [date.parseStr(value[0], options.formatDate), date.parseStr(value[1], options.formatDate)];
                    if (that.period[0] === null) {
                        that.period = [];
                    }
                    that.director = 0;
                } else {
                    if (that.period === undefined) {
                        that.period = [];
                    }
                    that.period[options.norange ? 0 : that.director] = date.parseStr(value, options.formatDate);
                    if (that.period[that.director] === null) {
                        that.period = [];
                        highlightPeriod();
                        return;
                    }
                    if (!that.director) {
                        that.period[1] = that.period[that.director].clone();
                    }
                    if (that.period[0] > that.period[1]) {
                        buff = that.period[0];
                        that.period[0] = that.period[1];
                        that.period[1] = buff;
                    }
                    that.director = that.director ? 0 : 1;
                }

                if (options.norange && that.period[0] && that.period[1] && that.period[1] !== that.period[0]) {
                    that.period[1] = that.period[0].clone();
                }

                highlightPeriod();
                if (options.hideAfterSelect && that.period[0] && that.period[1] && that.period[0] !== that.period[1]) {
                    hide();
                }

                that.month = that.period.length ? that.period[0].getMonth() + 1 : parseInt(options.startMonth, 10);
                that.year = that.period.length ? that.period[0].getFullYear() : parseInt(options.startYear, 10);


                if (oldrange[0] !== that.period[0] && (oldrange[0] || '').toString() !== (that.period[0] || '').toString()) {
                    __safecall(!oldrange[0] ? 'onFromSelected' : 'onFromChanged');
                }

                if (oldrange[1] !== that.period[1] &&
                    (oldrange[1] || '').toString() !== (that.period[1] || '').toString() &&
                    (
                        ((that.period[1] || '').toString() !== (that.period[0] || '').toString()) ||
                        (oldrange[1] && (oldrange[1] || '').toString() !== (that.period[0] || '').toString())
                    )
                ) {
                    __safecall(
                        (
                            !oldrange[1] ||
                            (oldrange[0] || '').toString() === (that.period[0] || '').toString()
                        ) ? 'onToSelected' : 'onToChanged'
                    );
                }
            },

            recalcDraggerPosition = function () {
                clearTimeout(that.timer2);
                that.timer2 = setTimeout(function () {
                    var parentLeft = Math.abs(parseInt(that.yearsline.css('left'), 10)),
                        perioddragger = that.picker.find('.period_picker_years_dragger'),
                        left = parseInt(perioddragger.css('left'), 10);
                    if (left < parentLeft) {
                        that.yearsline.css('left', -left + 'px');
                    } else if (left + perioddragger.width()  > parentLeft + that.yearslineparent.width()) {
                        that.yearsline.css('left', -(left + perioddragger.width() - that.yearslineparent.width()) + 'px');
                    }
                }, options.defaultTimeout);
            },

            calcDate = function (date, year, month, day) {
                date.setFullYear(year);
                date.setMonth(month); // set month after year, because month can be more then 12 when work mousewheel
                date.setDate(day);
            },

            getRealDateTime = function () {
                var date = new Date();

                calcDate(date, that.year, that.month - 1, 1);

                return [date.getMonth(), date.getFullYear()];
            },
            regenerate = function (cells) {
                if (!that.picker.is(':visible')) {
                    return;
                }

                var width = parseInt(that.pickerdays.width(), 10),
                    height = that.picker[0].offsetHeight,
                    i;


                if (cells === undefined || !Array.isArray(cells)) {
                    options.cells = [Math.floor((height - options.someYOffset) / options.monthHeightInPixels) || 1, Math.floor(width / options.monthWidthInPixels) || 1];
                } else {
                    options.cells = cells;
                    that.picker.css({
                        width: options.cells[1] * options.monthWidthInPixels + ((options.timepicker && $.fn.TimePicker) ? 87 * (!options.norange ? 2 : 1) : 0) + 50,
                        height: (options.cells[0] * options.monthHeightInPixels) + options.someYOffset
                    });
                }

                if (options.cells[0] < 0) {
                    options.cells[0] = 1;
                }

                that.monthcount = options.cells[0] * options.cells[1];

                generateCalendars(that.month, that.year);
                generateYearsLine();
                recalcDraggerPosition();
                highlightPeriod();

                recalcPosition(that.startinput[0]);

                for (i = 0; i < that.onAfterRegenerate.length; i += 1) {
                    that.onAfterRegenerate[i].call(that);
                }
            },

            init = function () {
                var offset,
                    start,
                    diff,
                    drag,
                    perioddrag,
                    perioddragger,
                    left,
                    headdrag,
                    period_picker_years_selector,
                    period_picker_years_selector_container,
                    period_picker_time_inputs,
                    timepickerCallback;

                that.button.on('click keydown', function (e) {
                    if (e.type === 'keydown') {
                        switch (e.which) {
                            case 9:
                                if (!options.inline) {
                                    hide();
                                }
                                return;
                            case 38:
                            case 13:
                                break;
                            default:
                                return;
                        }
                    }
                    if (that.button.is('[disabled]')) {
                        e.preventDefault();
                        return false;
                    }
                    togglePicker();
                });

                if (!options.inline) {
                    that.startinput.after(that.button);
                }

                offset = that.startinput.offset();

                that.picker.find('.period_picker_submit_dates input')
                    .on('focus', function () {
                        $(this).parent().parent().addClass('input_focused_yes');
                    })
                    .on('blur', function () {
                        $(this).parent().parent().removeClass('input_focused_yes');
                    });

                // enter date in center fields
                that.picker.find('.period_picker_submit_dates .period_picker_date input')
                    .on('keydown', function (e) {
                        var input = this;
                        clearTimeout(that.timer3);
                        that.timer3 = setTimeout(function () {
                            if ($(input).val()) {
                                var time = moment($(input).val(), options.formatDate);
                                if (!time.isValid()) {
                                    $(input).parent().parent().addClass('period_picker_error');
                                    return;
                                }
                                addRange([that.picker.find('.period_picker_submit_dates .period_picker_date input').eq(0).val(), that.picker.find('.period_picker_submit_dates .period_picker_date input').eq(1).val()]);
                                if (e.which === 13) {
                                    hide();
                                }
                            }
                            $(input).parent().parent().removeClass('period_picker_error');
                        }, options.defaultTimeout * 2);
                        e.stopPropagation();
                    });

                if (options.timepicker && $.fn.TimePicker) {
                    // enter time in center inputs
                    timepickerCallback = function (e) {
                        var input = this,
                            time,
                            tw = new TimeWrapper();

                        that.currentTimepickerIndex = parseInt($(that).data('index'), 10);

                        if ($(input).val()) {
                            time = moment($(input).val(), options.timepickerOptions.inputFormat);
                            if (!time.isValid()) {
                                $(input).parent().parent().addClass('period_picker_error');
                                return;
                            }
                            if (that.period && that.period.length && tw.isEqualDate(that.period[0], that.period[1]) && moment(period_picker_time_inputs.eq(0).val(), options.timepickerOptions.inputFormat).toDate().getTime() > moment(period_picker_time_inputs.eq(1).val(), options.timepickerOptions.inputFormat).toDate().getTime()) {
                                $(input).parent().parent().addClass('period_picker_error');
                                return;
                            }
                            addRangeTime(that.picker.find('.period_picker_submit_dates .period_picker_time input').eq(0).val(), that.picker.find('.period_picker_submit_dates .period_picker_time input').eq(1).val());
                            if (e && e.which === 13) {
                                hide();
                            }
                        }

                        $(input).parent().parent().removeClass('period_picker_error');
                    };
                    period_picker_time_inputs = that.picker.find('.period_picker_submit_dates .period_picker_time input')
                        .on('keydown change', function (e) {
                            if (e.type === 'keydown') {
                                clearTimeout(that.timer3);
                                that.timer3 = setTimeout(timepickerCallback.bind(this, e), options.defaultTimeout * 3);
                            } else {
                                timepickerCallback.call(this, e);
                            }
                            e.stopPropagation();
                        });
                }

                that.picker.find('.period_picker_max_min')
                    .on('mousedown click mouseup touchstart touchend', function (e) {
                        e.stopPropagation();
                    })
                    .on('mouseup touchend', function (e) {
                        that.picker.toggleClass('period_picker_maximize');
                        regenerate();
                    });

                if (options.fullsizeOnDblClick) {
                    that.picker.find('.period_picker_head').on('dblclick', function () {
                        that.picker.toggleClass('period_picker_maximize');
                        regenerate();
                    });
                }

                that.picker.find('.period_picker_close')
                    .on('mousedown mouseup click touchstart touchend', function (e) {
                        e.stopPropagation();
                    })
                    .on('click', hide);

                if (options.mousewheel) {
                    that.picker.on('mousewheel', function (e) {
                        that.month += (options.reverseMouseWheel ? -1 : 1) * e.deltaY;
                        regenerate();
                        return false;
                    });
                    if (options.mousewheelYearsLine) {
                        that.picker.find('.period_picker_years_selector').on('mousewheel', function (e) {
                            that.year += (options.reverseMouseWheel ? -1 : 1) * e.deltaY;
                            that.month = 1;

                            regenerate();

                            e.preventDefault();
                            e.stopPropagation();
                            return false;
                        });
                    }
                }

                if (options.navigate) {
                    that.picker.find('.xdsoft_navigate').on('click', function () {
                        that.month += $(this).hasClass('xdsoft_navigate_prev') ? -1 : 1;
                        regenerate();
                        return false;
                    });
                }

                that.picker.on('click', '.period_picker_show.period_picker_today', function () {
                    if (__safecall('onTodayButtonClick') === false) {
                        return;
                    }
                    var now = new Date();
                    that.year = now.getFullYear();
                    that.month = now.getMonth() + 1;
                    regenerate();
                });

                that.picker.on('click', '.period_picker_show.period_picker_ok', function () {
                    if (__safecall('onOkButtonClick') === false) {
                        return;
                    }
                    hide();
                });

                if (options.clearButtonInButton) {
                    that.button.find('.icon_clear').on('mouseup touchstart mousedown click', function (e) {
                        e.preventDefault();
                        e.stopPropagation();
                        return false;
                    });
                    that.button.find('.icon_clear').on('mousedown', function (e) {
                        clear();
                        e.stopImmediatePropagation();
                        return false;
                    });
                }

                if (options.clearButton) {
                    that.picker.on('click', '.period_picker_show.period_picker_clear', function () {
                        clear();
                    });
                }

                that.picker.on('click touchstart', '.period_picker_years_selector .period_picker_year', function () {
                    that.year = parseInt($(this).text(), 10);
                    that.month = -Math.floor(that.monthcount / 2) + 1;
                    regenerate();
                });
                that.picker.on('mousedown touchstart', '.period_picker_days td td,.period_picker_month', function () {
                    if ($(this).hasClass('period_picker_month')) {
                        var date = new TimeWrapper();
                        var start = date.parseStr($(this).data('datestart'), options.formatDate),
                            end = date.parseStr($(this).data('dateend'), options.formatDate);
                        if ((!that.mindate || start >= that.mindate) && (!that.maxdate || end <= that.maxdate)) {
                            addRange([$(this).data('datestart'), $(this).data('dateend')]);
                        }
                    } else {
                        if (!$(this).hasClass('period_picker_gray_period') && !$(this).hasClass('period_picker_empty')) {
                            if ($(this).hasClass('period_picker_selector_week')) {
                                var week = parseInt($(this).parent().data('week'), 10),
                                    days = that.picker.find('tr[data-week=' + week + '] > td.period_picker_cell:not(.period_picker_gray_period)'),
                                    last = days.eq(-1),
                                    first = days.eq(0);
                                if (last.length) {
                                    addRange([first.data('date'), last.data('date')]);
                                }
                            } else {
                                if (that.picker.find('.period_picker_selected').length !== 1) {
                                    that.picker.find('.period_picker_selected').removeClass('period_picker_selected');
                                    $(this).addClass('period_picker_selected');
                                } else {
                                    $(this).addClass('period_picker_selected');
                                }
                                addRange($(this).data('date'));
                            }
                        }
                    }
                });

                that.picker.on('mousedown touchstart', '.period_picker_years_selector_container', function (e) {
                    period_picker_years_selector = $(this);
                    period_picker_years_selector_container = true;
                    start = [getClient(e).clientX, getClient(e).clientY, parseInt(period_picker_years_selector.css('left') || 0, 10)];
                    e.preventDefault();
                });

                that.picker.on('mousedown touchstart', '.period_picker_years_dragger', function (e) {
                    perioddragger = $(this);
                    perioddrag = true;
                    start = [getClient(e).clientX, getClient(e).clientY, parseInt(perioddragger.css('left'), 10)];
                    e.stopPropagation();
                    e.preventDefault();
                });

                if (options.draggable) {
                    that.picker
                        .on('mousedown touchstart', '.period_picker_head', function (e) {
                            headdrag = true;
                            start = [getClient(e).clientX, getClient(e).clientY, parseInt(that.picker.css('left'), 10), parseInt(that.picker.css('top'), 10)];
                            e.preventDefault();
                        });
                }

                that.picker.on('mouseup touchend', function (e) {
                    drag = false;
                    perioddrag = false;
                    headdrag = false;
                    period_picker_years_selector_container = false;
                    if (options.timepicker && $.fn.TimePicker) {
                        that.timepicker.find('input.timepicker').TimePicker('stopDrag');
                    }
                    e.stopPropagation();
                });
                that.picker.find('.period_picker_resizer').on('mousedown touchstart', function (e) {
                    drag = true;
                    start = [getClient(e).clientX, getClient(e).clientY, parseInt(that.picker.css('width'), 10), parseInt(that.picker.css('height'), 10)];
                    e.preventDefault();
                });

                if (!options.noHideSourceInputs && !options.likeXDSoftDateTimePicker) {
                    that.startinput.hide();
                    that.endinput.hide();
                } else {
                    that
                        .startinput
                        .add(that.endinput)
                        .on('keydown.xdsoftpp mousedown.xdsoftpp touchstart.xdsoftpp', function () {
                            clearTimeout(timer);
                            timer = setTimeout(function () {
                                var value = getInputsValue(),
                                    date,
                                    format = options.timepicker ? options.formatDateTime : options.formatDate;
                                if ((value[0] !== undefined && value[0] !== that.startinput.val()) || (value[1] !== undefined && that.endinput.length && value[1] !== that.endinput.val())) {
                                    date = new TimeWrapper();
                                    addRange([date.parseStr(that.startinput.val(), format), date.parseStr(that.endinput.val(), format)]);
                                    if (that.period[0]) {
                                        that.year = that.period[0].getFullYear();
                                        that.month = that.period[0].getMonth() + 1;
                                        regenerate();
                                    }
                                }
                            }, options.defaultTimeout * 3);
                        });

                    if (options.likeXDSoftDateTimePicker) {
                        that.button.remove();
                        that
                            .startinput
                            .add(that.endinput)
                            .on('open.xdsoftpp focusin.xdsoftpp mousedown.xdsoftpp touchstart.xdsoftpp', function () {
                                var input = this;
                                if ($(input).is(':disabled') || that.picker.hasClass('visible')) {
                                    return;
                                }
                                clearTimeout(timer);
                                timer = setTimeout(function () {
                                    show(input);
                                }, options.defaultTimeout);
                            });

                        if (options.hideOnBlur) {
                            that.startinput
                                .add(that.endinput)
                                .on('blur.xdsoftpp', function () {
                                    setTimeout(function () {
                                        if (!that.picker.find('*:focus').length) {
                                            hide();
                                        }
                                    }, options.defaultTimeout * 2);
                                });
                        }
                    }
                }

                if (options.inline) {
                    that.startinput.after(that.picker);
                    show();
                }

                $(window)
                    .on('resize.xdsoftpp' + that.uniqueid, regenerate)
                    .on('keydown.xdsoftpp' + that.uniqueid, function (e) {
                        if (that.picker.hasClass('visible')) {
                            switch (e.which) {
                                case 40:
                                case 27:
                                    if (!options.inline) {
                                        hide();
                                    }
                                    break;
                                case 37:
                                case 39:
                                    that.picker.find('.xdsoft_navigate').eq(e.which === 37 ? 0 : 1).trigger('click');
                                    break;
                            }
                        }
                    })
                    .on('mouseup.xdsoftpp' + that.uniqueid + ' ' + 'touchend.xdsoftpp' + that.uniqueid, function (e) {
                        if (drag || perioddrag || headdrag || period_picker_years_selector_container) {
                            drag = false;
                            perioddrag = false;
                            headdrag = false;
                            period_picker_years_selector_container = false;
                        } else {
                            if (!options.inline) {
                                hide();
                                if (options.likeXDSoftDateTimePicker && (that.startinput.is(e.target) || that.endinput.is(e.target))) {
                                    show(e.target);
                                }
                            }
                        }
                    })
                    .on('mousemove.xdsoftpp' + that.uniqueid + ' ' + 'touchmove.xdsoftpp' + that.uniqueid, function (e) {
                        if (headdrag && !options.inline) {
                            diff = [getClient(e).clientX - start[0], getClient(e).clientY - start[1]];
                            if (!that.picker.hasClass('xdsoft_i_moved')) {
                                that.picker.addClass('xdsoft_i_moved');
                            }
                            that.picker.css({
                                left: start[2] + diff[0],
                                top: start[3] + diff[1]
                            });
                        }

                        if (drag) {
                            diff = [getClient(e).clientX - start[0], getClient(e).clientY - start[1]];
                            that.picker.css({
                                width: start[2] + diff[0],
                                height: start[3] + diff[1]
                            });
                            regenerate();
                        }

                        if (perioddrag) {
                            diff = [getClient(e).clientX - start[0], getClient(e).clientY - start[1]];
                            left = start[2] + diff[0];
                            perioddragger.css('left', left);
                            calcMonthOffsetFromPeriodDragger(left);
                            generateCalendars(that.month, that.year);
                            recalcDraggerPosition();
                        }

                        if (period_picker_years_selector_container) {
                            diff = [getClient(e).clientX - start[0], getClient(e).clientY - start[1]];
                            left = start[2] + diff[0];
                            period_picker_years_selector.css('left', left);
                        }
                    });

                generateTimePicker();
            },

            generateTimePicker = function () {
                if (options.timepicker && $.fn.TimePicker !== undefined) {
                    that.timepicker.each(function () {
                        var $that = $(this).find('input.timepicker'),
                            index = parseInt($that.data('index') || '0', 10);
                        if ($that.length && !$that.data('timepicker') && $.fn.TimePicker !== undefined) {
                            // init timepicker
                            if (index && options.defaultEndTime) {
                                options.timepickerOptions.defaultTime = options.defaultEndTime;
                            }
                            $that.TimePicker(options.timepickerOptions, $(this));
                            that.onAfterRegenerate.push(function () {
                                $that.TimePicker('regenerate');
                            });
                            $that
                                .on('change', function () {
                                    var input = that.picker.find('.period_picker_submit_dates .period_picker_time input').eq(index);
                                    if (!input.is(':focus') && input.val() !== this.value) {
                                        input.val(this.value)
                                            .trigger('change');
                                    }
                                })
                                .trigger('change');
                        }
                    });
                }
            },
            generateCalendars = function (month, year) {
                var i,
                    out = [],
                    date = getRealDateTime(),
                    weekdays = (new TimeWrapper()).weekdays(options.dayOfWeekStart);

                if (date[1] > options.yearsPeriod[1]) {
                    that.year = options.yearsPeriod[1];
                    year = that.year;
                    that.month = 12 - that.monthcount;
                    month = that.month;
                }

                if (date[1] < options.yearsPeriod[0]) {
                    that.year = options.yearsPeriod[0];
                    year = that.year;
                    that.month = 1;
                    month = that.month;
                }

                out.push('<tr class="period_picker_first_letters_tr">');

                function generateWeek() {
                    var k, out2 = [];
                    for (k = 0; k < weekdays.length; k += 1) {
                        out2.push('<th class="' + (options.weekEnds.indexOf(k + options.dayOfWeekStart > 7 ? (k + options.dayOfWeekStart) % 7 : k + options.dayOfWeekStart) !== -1 ? 'period_picker_holiday' : '') + '">' + weekdays[k] + '</th>');
                    }
                    return out2.join('');
                }

                for (i = 0; i < options.cells[1]; i += 1) {
                    out.push('<td class="period_picker_first_letters_td">' +
                        '<table class="period_picker_first_letters_table">' +
                        '<tbody>' +
                        '<tr>' +
                        '<th class="period_picker_selector_week_cap">' +
                        '<span class="period_picker_selector_week_cap"></span>' +
                        '</th>' +
                        generateWeek() +
                        '</tr>' +
                        '</tbody>' +
                        '</table>' +
                        '</td>');
                }

                out.push('</tr>');

                for (i = 0; i < options.cells[0]; i += 1) {
                    out.push('<tr>');
                    out.push(generateCalendarLine(month + i * options.cells[1], year, options.cells[1]));
                    out.push('</tr>');
                }

                that.calendarbox.html(out.join(''));

                highlightPeriod();
            },

            calcPixelOffsetForPeriodDragger = function () {
                var date = getRealDateTime();
                return (date[1] - options.yearsPeriod[0]) * options.yearSizeInPixels + date[0] * Math.floor(options.yearSizeInPixels / 12);
            },
            calcMonthOffsetFromPeriodDragger = function (left) {
                that.year = Math.floor(left / options.yearSizeInPixels) + options.yearsPeriod[0];
                that.month = Math.floor((left % options.yearSizeInPixels) / Math.floor(options.yearSizeInPixels / 12)) + 1;
            },
            generateYearsLine = function () {
                if (!options.yearsLine) {
                    return;
                }
                var y, out = [], i = 0;
                out.push('<div class="period_picker_years_dragger" title="' + i18n('Move to select the desired period') + '" style="left: ' + calcPixelOffsetForPeriodDragger() + 'px; width: ' + (Math.floor(options.yearSizeInPixels / 12) * that.monthcount) + 'px;"></div>');
                out.push('<div class="period_picker_years_period" style="display: block; width: 0; left: 300px;"></div>');
                if (options.yearsPeriod && $.isArray(options.yearsPeriod)) {
                    for (y = options.yearsPeriod[0]; y <= options.yearsPeriod[1]; y += 1) {
                        out.push('<div class="period_picker_year" style="left:' + (i * options.yearSizeInPixels) + 'px">' + y + '</div>');
                        i += 1;
                    }
                }
                that.yearsline.css('width', (i * options.yearSizeInPixels) + 'px');
                that.yearsline.html(out.join(''));
            },
            generateCalendarLine = function (month, year, count) {
                var i, j, k, out = [], date = new TimeWrapper(), countDaysInMonth, currentMonth, ticker, now = (new TimeWrapper()).format('DD.MM.YYYY');

                date.setDate(1);
                date.setFullYear(year); // change because not changed year
                date.setMonth(month - 1);

                for (i = 0; i < count; i = i + 1) {
                    currentMonth = date.getMonth() + 1;
                    countDaysInMonth = date.countDaysInMonth();

                    out.push('<td class="period_picker_month' + date.format('M') + '">' + '<table>' + '<tbody>');
                    out.push(
                        '<tr>' +
                        '<th class="period_picker_month" data-datestart="' + date.format(options.formatDate) + '"  data-dateend="' + date.clone(0, 0, countDaysInMonth).format(options.formatDate) + '" colspan="8" title="' + date.format(options.formatMonth) + '">' + date.format(options.formatMonth) + '<b>' + date.format('M.YYYY') + '</b></th>' +
                        '</tr>'
                    );

                    ticker = 0;
                    while (date.format('E') !== options.dayOfWeekStart && ticker < 7) {
                        date.setDate(date.getDate() - 1);
                        ticker += 1;
                    }

                    j = 1;
                    ticker = 0;

                    while (j <= countDaysInMonth && ticker < 100) {
                        out.push('<tr data-week="' + date.format('W') + '">');
                        out.push(
                            '<td class="period_picker_selector_week" title="' + i18n('Select week #') + ' ' + date.format('W') + '">' +
                            '<span class="period_picker_selector_week"></span>' +
                            '</td>'
                        );

                        for (k = 1; k <= 7; k += 1) {
                            if (date.format('M') !== currentMonth) {
                                out.push('<td class="period_picker_empty">&nbsp;</td>');
                            } else {
                                if ((!that.maxdate || date < that.maxdate) && (!that.mindate || date > that.mindate) && options.disableDays.indexOf(date.format(options.formatDate)) === -1) {
                                    out.push('<td data-date="' + date.format(options.formatDate) + '"');
                                    out.push('    class="period_picker_cell ');
                                    out.push((options.weekEnds.indexOf(date.format('E')) !== -1 || options.holidays.indexOf(date.format(options.formatDate)) !== -1) ? ' period_picker_holiday' : ' period_picker_weekday');
                                    if (date.format('DD.MM.YYYY') === now) {
                                        out.push(' period_picker_cell_today ');
                                    }
                                    out.push(((k === 7 || date.format('D') === countDaysInMonth) ? ' period_picker_last_cell' : '') + '">' + date.format('D') + '</td>');
                                } else {
                                    out.push('<td class="period_picker_gray_period">' + date.format('D') + '</td>');
                                }
                                j += 1;
                            }
                            date.setDate(date.getDate() + 1);
                        }

                        ticker += 1;
                        out.push('</tr>');
                    }

                    month += 1;

                    date.setDate(1);
                    date.setFullYear(year);
                    date.setMonth(month - 1); // for the same reason

                    out.push('</tbody>' + '</table>' + '</td>');
                }

                return out.join('');
            },

            togglePicker = function () {
                if (that.picker.hasClass('active')) {
                    hide();
                } else {
                    show();
                }
            },
            clear = function () {
                addRange();
                if (options.onClearButtonClick && $.isFunction(options.onClearButtonClick)) {
                    options.onClearButtonClick.call(that);
                }
                if (options.closeAfterClear && !options.inline) {
                    hide();
                }
            },
            recalcPosition = function (target) {
                if (isOpen && !that.picker.hasClass('xdsoft_i_moved')) {
                    that.picker.css(getPosition(target));
                }
            },
            getPosition = function (target) {
                var offset = !options.likeXDSoftDateTimePicker ? that.button.offset() : $(target).offset(),
                    top = 0,
                    left = 0;

                top = offset.top + (!options.likeXDSoftDateTimePicker ? that.button.outerHeight() : $(target).outerHeight()) - 1;
                left = offset.left;

                if (top + that.picker.outerHeight() > $(window).height() + $(window).scrollTop()) {
                    top = offset.top - that.picker.outerHeight() - 1;
                }

                if (top < 0) {
                    top = 0;
                }

                if (left + that.picker.outerWidth() > $(window).width()) {
                    left = $(window).width() - that.picker.outerWidth();
                }

                options.placement.toLowerCase().split(/\s/).forEach(function (placement) {
                    switch (placement) {
                        case 'right':
                            left = offset.left + that.button.outerWidth();
                            break;
                        case 'left':
                            left = offset.left - that.picker.outerWidth();
                            break;
                        case 'top':
                            top = offset.top - that.picker.outerHeight();
                            break;
                        case 'bottom':
                            top = offset.top + (!options.likeXDSoftDateTimePicker ? that.button.outerHeight() : $(target).outerHeight()) - 1;
                            break;
                        default:

                            break;
                    }
                });




                return {
                    left: left,
                    top: top
                };
            },
            isOpen = false,
            show = function (target) {
                if (isOpen) {
                    return;
                }
                isOpen = true;

                if (!options.inline) {
                    that.picker.parent().length || $(document.body).append(that.picker);

                    that.picker
                        .addClass('visible');

                    if (options.defaultTimeout) {
                        setTimeout(function () {
                            that.picker.addClass('active');
                        }, options.defaultTimeout);
                    } else {
                        that.picker.addClass('active');
                    }

                    if (options.fullsize) {
                        that.picker.addClass('period_picker_maximize');
                    } else if (!that.picker.hasClass('xdsoft_i_moved')) {
                        recalcPosition(target);
                    }
                }

                regenerate();

                for (var i = 0; i < that.onAfterShow.length; i += 1) {
                    that.onAfterShow[i].call(that);
                }
            },
            hide = function () {
                if (!isOpen) {
                    return;
                }
                isOpen = false;

                if (that.picker.hasClass('visible')) {
                    that.picker.removeClass('active');
                    if (that.picker.hasClass('animation') && options.defaultTimeout) {
                        setTimeout(function () {
                            if (!that.picker.hasClass('active')) {
                                that.picker.removeClass('visible');
                            }
                        }, options.defaultTimeout * 3);
                    } else {
                        that.picker.removeClass('visible');
                    }
                    if (that.onAfterHide !== undefined && that.onAfterHide.length) {
                        for (var i = 0; i < that.onAfterHide.length; i += 1) {
                            that.onAfterHide[i].call(that);
                        }
                    }
                }
            },

            __isDestroyed = false,
            destroy = function () {
                if (__isDestroyed) {
                    return;
                }
                __isDestroyed = true;

                that.picker.remove();
                that.button.remove();

                that.startinput.off('.xdsoftpp').show().removeData('periodpicker');
                that.endinput.off('.xdsoftpp').show();

                $(window).off('.xdsoftpp' + that.uniqueid);

                PeriodPicker.instances[that.startinput.id || 'picker' + that.uniqueid] = undefined;
                delete PeriodPicker.instances[that.startinput.id || 'picker' + that.uniqueid];
                that.startinput.removeData('periodpicker');
            };

        date = new TimeWrapper();

        values[0] = that.startinput.val();
        values[1] = that.endinput.val();

        applyOptions();

        addRange([
            date.parseStr(values[0], !options.timepicker ? options.formatDate : options.formatDateTime) || date.parseStr(values[0], options.formatDate),
            date.parseStr(values[1], !options.timepicker ? options.formatDate : options.formatDateTime) || date.parseStr(values[1], options.formatDate)
        ]);

        that.onAfterShow = [];
        that.onAfterHide = [];
        that.onAfterRegenerate = [];

        that.uniqueid = uniqueid;
        PeriodPicker.instances[that.startinput.id || 'picker' + that.uniqueid] = that;

        that.currentTimepickerIndex = 0;
        that.timepickerSetLimits = false;

        that.timer1 = 0;
        that.timer2 = 0;
        that.timer3 = 0;
        uniqueid += 1;

        init();

        if (options.timepicker && $.fn.TimePicker !== undefined) {
            addRangeTime(
                date.parseStr(values[0], options.formatDateTime) || date.parseStr(values[0], options.formatDate),
                date.parseStr(values[1], options.formatDateTime) || date.parseStr(values[1], options.formatDate)
            );
        }

        // public
        that.returnPeriod = returnPeriod;
        that.getInputsValue = getInputsValue;
        that.getLabel = getLabel;
        that.setLabel = setLabel;
        that.regenerate = regenerate;
        that.clear = clear;
        that.addRange = addRange;
        that.addRangeTime = addRangeTime;
        that.toggle = togglePicker;
        that.show = show;
        that.hide = hide;
        that.applyOptions = applyOptions;
        that.destroy = destroy;
    }
    PeriodPicker.instances = {};

    window.PeriodPicker = PeriodPicker;
    window.TimeWrapper = TimeWrapper;

    $.fn.periodpicker = function (opt, opt2, opt3) {
        if (window.moment === undefined) {
            throw new Error('PeriodPicker\'s JavaScript requires MomentJS');
        }

        var returnValue = this;
        this.each(function () {
            var options,
                times = [],
                date,
                $that = $(this),
                periodpicker = $that.data('periodpicker');

            if (!periodpicker) {
                periodpicker = new PeriodPicker(this, opt);
                $that.data('periodpicker', periodpicker);
            } else {
                options = periodpicker.options;
                switch (opt) {
                case 'picker':
                    returnValue = periodpicker;
                    break;
                case 'regenerate':
                    periodpicker.regenerate(opt2);
                    break;
                case 'setOption':
                    periodpicker.options[opt2] = opt3;
                    periodpicker.applyOptions();
                    break;
                case 'setOptions':
                    periodpicker.options = $.extend(true, {}, periodpicker.options, opt2);
                    periodpicker.applyOptions();
                    break;
                case 'clear':
                    periodpicker.addRange();
                    break;
                case 'change':
                    date = new TimeWrapper();
                    times[0] = date.parseStr(periodpicker.startinput.val(), !options.timepicker ? options.formatDate : options.formatDateTime) || date.parseStr(periodpicker.startinput.val(), options.formatDate);
                    if (periodpicker.endinput.length) {
                        times[1] = date.parseStr(periodpicker.endinput.val(), !options.timepicker ? options.formatDate : options.formatDateTime) || date.parseStr(periodpicker.endinput.val(), options.formatDate);
                    }
                    periodpicker.addRange(times);
                    break;
                case 'destroy':
                    periodpicker.destroy();
                    break;
                case 'hide':
                    periodpicker.hide();
                    break;
                case 'show':
                    periodpicker.show();
                    break;
                case 'value':
                    if (opt2 !== undefined) {
                        date = new TimeWrapper();
                        if ($.isArray(opt2)) {
                            times[0] = date.parseStr(opt2[0], !options.timepicker ? options.formatDate : options.formatDateTime) || date.parseStr(opt2[0], options.formatDate);
                            if (opt2[1] !== undefined) {
                                times[1] = date.parseStr(opt2[1], !options.timepicker ? options.formatDate : options.formatDateTime) || date.parseStr(opt2[1], options.formatDate);
                            }
                        } else {
                            times[0] = date.parseStr(opt2, !options.timepicker ? options.formatDate : options.formatDateTime) || date.parseStr(opt2, options.formatDate);
                        }
                        periodpicker.addRange(times);
                        if (options.timepicker && $.fn.TimePicker !== undefined) {
                            periodpicker.addRangeTime(times[0], times[1] || times[0]);
                        }
                    } else {
                        returnValue = periodpicker.period;
                    }
                    break;
                case 'valueStringStrong':
                    returnValue =  periodpicker.getInputsValue().join('-');
                    break;
                case 'valueString':
                    returnValue =  periodpicker.getLabel().join('-');
                    break;
                case 'disable':
                    periodpicker.button.attr('disabled', true);
                    periodpicker.startinput
                        .add(periodpicker.endinput)
                        .attr('disabled', true)
                        .attr('readonly', true);
                    break;
                case 'enable':
                    periodpicker.button.removeAttr('disabled');
                    periodpicker.startinput
                        .add(periodpicker.endinput)
                        .removeAttr('disabled')
                        .removeAttr('readonly');
                    break;
                }
            }
        });
        return returnValue;
    };

    PeriodPicker.defaultOptions = $.fn.periodpicker.defaultOptions = {
        /**
         * @property {string} buttonClassSuffix='' Add class to main button
         */
        buttonClassSuffix: '',

        /**
         * @property {boolean|int} tabIndex=0 Tabindex
         */
        tabIndex: 0,

        /**
         * @property {boolean} animation=true Enable animation when periodpicker is shown
         */
        animation: true,
        lang: 'en',
        i18n: {
            'en' : {
                'Select week #' : 'Select week #',
                'Select period' : 'Select period',
                'Select date' : 'Select date',
                'Choose period' : 'Select period',
                'Choose date' : 'Select date',
                'Clear' : 'Clear'
            },
            'ru' : {
                'Move to select the desired period' : 'Переместите, чтобы выбрать нужный период',
                'Select week #' : 'Выбрать неделю №',
                'Select period' : 'Выбрать период',
                'Select date' : 'Выбрать дату',
                'Open fullscreen' : 'Открыть на весь экран',
                'Close' : 'Закрыть',
                'OK' : 'OK',
                'Choose period' : 'Выбрать период',
                'Choose date' : 'Выбрать дату',
                'Clear' : 'Отчистить'
            },
            'fr' : {
                'Move to select the desired period' : 'Déplacer pour sélectionner la période désirée',
                'Select week #' : 'Sélectionner la semaine #',
                'Select period' : 'Choisissez une date',
                'Select date' : 'Sélectionner la date',
                'Open fullscreen' : 'Ouvrir en plein écran',
                'Close' : 'Fermer',
                'OK' : 'OK',
                'Choose period' : 'Choisir la période',
                'Choose date' : 'Choisir une date',
                'Clear' : 'Propre'
            }
        },
        /**
         * @property {boolean} withoutBottomPanel=false Do not show the bottom panel with buttons and input elements
         */
        withoutBottomPanel: false,
        /**
         * @property {boolean} showTimepickerInputs=true Show inputs for enterind times
         */
        showTimepickerInputs: true,
        /**
         * @property {boolean} showDatepickerInputs=true Show inputs for enterind dates
         */
        showDatepickerInputs: true,
        timepicker: false,

        /**
         *  @property {boolean} useTimepickerLimits=true Don't allow set time2 less than time1 and time1 more than time2 in timepickers when was selected range  
         */
        useTimepickerLimits: true,

        timepickerOptions: {
            inputFormat: 'HH:mm'
        },

        /**
         *  @property {boolean} defaultEndTime=false If you need different defaultTime for start and for end use that option for END, and timepickerOptions.defaultTime for START 
         */
        defaultEndTime: false,

        /**
         * @property {boolean} yearsLine=true Show years selector
         */
        yearsLine: true,
        title: true,

        /**
         * @property {boolean} inline=false Show picker on the spot of the original element. Button is not shown
         */
        inline: false,

        /**
         * @property {boolean} clearButtonInButton=false Show clear value button in main button
         */
        clearButtonInButton: false,

        /**
         * @property {boolean} clearButton=false Show clear value button in periodpicker
         */
        clearButton: false,

        /**
         * @property {boolean} closeAfterClear=false Hide periodpicker after clear operation
         */
        closeAfterClear: true,

        okButton: true,

        /**
         * @property {boolean} todayButton=false Go to today
         */
        todayButton: false,

        closeButton: true,
        fullsizeButton: true,
        resizeButton: true,
        navigate: true,

        //buttons
        fullsizeOnDblClick: true,
        fullsize: false,
        draggable: true,


        mousewheel: true,
        /**
         * @property {boolean} mousewheelYearsLine=true If true, then when scrolling the mouse wheel over lightYears, it will not be constantly changing the month and year will
         */
        mousewheelYearsLine: true,
        reverseMouseWheel: true,
        hideAfterSelect: false,

        /**
         * @property {boolean} hideOnBlur=true When enabled likeXDSoftDateTimePicker mode, and form has more 1 elements, source input lose focus
         */
        hideOnBlur: true,

        norange: false,
        timePickerInRight: false,

        //formats
        formatMonth: 'MMMM YYYY',

        formatDecoreDate: 'D MMMM',
        formatDecoreDateWithYear: 'D MMMM YYYY',
        formatDecoreDateWithoutMonth: 'D',

        formatDecoreDateTimeWithoutMonth: 'HH:mm D',
        formatDecoreDateTime: 'HH:mm D MMMM',
        formatDecoreDateTimeWithYear: 'HH:mm D MMMM YYYY',
        formatDateTime: 'HH:mm YYYY/MM/D',
        formatDate: 'YYYY/MM/D',

        //end period input identificator
        end: '',

        /**
         * @property {boolean} noHideSourceInputs=false Don't hide source inputs
         */
        noHideSourceInputs: false,

        /**
         * @property {boolean} likeXDSoftDateTimePicker=false Hide Picker button, not hide the source input fields. If the input field gets the focus it displays periodpiker. Behavior similar to datetimepicker http://xdsoft.net/jqplugins/datetimepicker/
         */
        likeXDSoftDateTimePicker: false,

        startMonth: null,
        startYear: null,

        dayOfWeekStart: 1, //Mon - 1,t,Wen - 3,th,f,s,Sun - 7
        yearSizeInPixels: 120,
        timepickerWidthInPixels: 50,
        monthWidthInPixels: 184,
        monthHeightInPixels: 174,
        someYOffset: 150,
        yearsPeriod: [null, null],
        weekEnds: [6, 7],   // 1 - is Mon, 7 - is Sun
        holidays: [],       // in formatDate format
        disableDays: [],    // in formatDate format
        minDate: false,     // in formatDate format
        maxDate: false,
        cells: [1, 3],

        /**
         * @property {string|int|null} utcOffset=null Setting the utc offset by supplying minutes http://momentjs.com/docs/#/manipulating/utc-offset/
         */
        utcOffset: null,

        placement: "bottom",


        // events
        onTodayButtonClick: false,
        onOkButtonClick: false,
        onAfterShow: false,
        onAfterHide: false,
        onAfterRegenerate: false,
        onClearButtonClick: false,

        onFromSelected: false,
        onToSelected: false,
        onFromChanged: false,
        onToChanged: false,

        defaultTimeout: 100
    };

}(jQuery, window, document));

if (Array.prototype.indexOf === undefined) {
    Array.prototype.indexOf = function (obj, start) {
        var i, j;
        j = that.length;
        for (i = (start || 0); i < j; i += 1) {
            if (that[i] === obj) { return i; }
        }
        return -1;
    };
}