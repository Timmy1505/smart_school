"use strict";

document.addEventListener("DOMContentLoaded", function(event) {
    const d = document;

    // Initialize Swal with Bootstrap buttons
    const initSwal = () => {
        Swal.mixin({
            customClass: {
                confirmButton: 'btn btn-primary me-3',
                cancelButton: 'btn btn-gray'
            },
            buttonsStyling: false
        });
    };

    // Handle theme settings
    const handleThemeSettings = () => {
        const themeSettingsEl = d.getElementById('theme-settings');
        const themeSettingsExpandEl = d.getElementById('theme-settings-expand');

        if (themeSettingsEl) {
            const themeSettingsCollapse = new bootstrap.Collapse(themeSettingsEl, {
                show: true,
                toggle: false
            });

            const isSettingsExpanded = window.localStorage.getItem('settings_expanded') === 'true';

            if (isSettingsExpanded) {
                themeSettingsCollapse.show();
                themeSettingsExpandEl.classList.remove('show');
            } else {
                themeSettingsCollapse.hide();
                themeSettingsExpandEl.classList.add('show');
            }

            themeSettingsEl.addEventListener('hidden.bs.collapse', () => {
                themeSettingsExpandEl.classList.add('show');
                window.localStorage.setItem('settings_expanded', false);
            });

            themeSettingsExpandEl.addEventListener('click', () => {
                themeSettingsExpandEl.classList.remove('show');
                window.localStorage.setItem('settings_expanded', true);
                setTimeout(() => themeSettingsCollapse.show(), 300);
            });
        }
    };

    // Handle sidebar behavior
    const handleSidebar = () => {
        const sidebar = d.getElementById('sidebarMenu');
        const breakpoints = { sm: 540, md: 720, lg: 960, xl: 1140 };

        if (sidebar && d.body.clientWidth < breakpoints.lg) {
            sidebar.addEventListener('shown.bs.collapse', () => d.body.style.position = 'fixed');
            sidebar.addEventListener('hidden.bs.collapse', () => d.body.style.position = 'relative');
        }
    };

    // Handle notification icon behavior
    const handleNotifications = () => {
        const iconNotifications = d.querySelector('.notification-bell');

        if (iconNotifications) {
            iconNotifications.addEventListener('shown.bs.dropdown', () => {
                iconNotifications.classList.remove('unread');
            });
        }
    };

    // Set background attributes
    const setBackgrounds = () => {
        const setBackground = (selector, attribute) => {
            [].slice.call(d.querySelectorAll(selector)).map(el => {
                el.style.background = `url(${el.getAttribute(attribute)})`;
            });
        };

        setBackground('[data-background]', 'data-background');
        setBackground('[data-background-lg]', 'data-background-lg');
        setBackground('[data-background-color]', 'data-background-color');
        setBackground('[data-color]', 'data-color');
    };

    // Initialize tooltips
    const initTooltips = () => {
        const tooltipTriggerList = [].slice.call(d.querySelectorAll('[data-bs-toggle="tooltip"]'));
        tooltipTriggerList.map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
    };

    // Initialize popovers
    const initPopovers = () => {
        const popoverTriggerList = [].slice.call(d.querySelectorAll('[data-bs-toggle="popover"]'));
        popoverTriggerList.map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl));
    };

    // Initialize datepickers
  // Initialize datepickers
const initDatepickers = () => {
    const datepickers = document.querySelectorAll('[data-datepicker]');
    datepickers.forEach(el => {
        if (!el.dataset.datepickerInitialized) {
            new Datepicker(el, {
                buttonClass: 'btn',
                format: 'dd/mm/yyyy',
                autoclose: true,
                todayHighlight: true,
            });
            el.dataset.datepickerInitialized = 'true';
        }
    });
};
var dataTableEl = d.getElementById('datatable');
if(dataTableEl) {
    const dataTable = new simpleDatatables.DataTable(dataTableEl);
}
// Call the function to initialize the datepickers
initDatepickers();


    // Initialize sliders
    const initSliders = () => {
        if (d.querySelector('.input-slider-container')) {
            [].slice.call(d.querySelectorAll('.input-slider-container')).map(el => {
                const slider = el.querySelector(':scope .input-slider');
                const sliderId = slider.getAttribute('id');
                const minValue = slider.getAttribute('data-range-value-min');
                const maxValue = slider.getAttribute('data-range-value-max');
                const sliderValue = el.querySelector(':scope .range-slider-value');
                const sliderValueId = sliderValue.getAttribute('id');
                const startValue = sliderValue.getAttribute('data-range-value-low');
                const sliderElement = d.getElementById(sliderId);
                const sliderValueElement = d.getElementById(sliderValueId);

                noUiSlider.create(sliderElement, {
                    start: [parseInt(startValue)],
                    connect: [true, false],
                    range: { min: [parseInt(minValue)], max: [parseInt(maxValue)] }
                });
            });
        }

        if (d.getElementById('input-slider-range')) {
            const sliderRange = d.getElementById("input-slider-range");
            const low = d.getElementById("input-slider-range-value-low");
            const high = d.getElementById("input-slider-range-value-high");
            const values = [low, high];

            noUiSlider.create(sliderRange, {
                start: [parseInt(low.getAttribute('data-range-value-low')), parseInt(high.getAttribute('data-range-value-high'))],
                connect: true,
                tooltips: true,
                range: {
                    min: parseInt(sliderRange.getAttribute('data-range-value-min')),
                    max: parseInt(sliderRange.getAttribute('data-range-value-max'))
                }
            });

            sliderRange.noUiSlider.on("update", (values, handle) => {
                values[handle].textContent = values[handle];
            });
        }
    };

    // Initialize Chartist charts
    const initCharts = () => {
        if (d.querySelector('.ct-chart-sales-value')) {
            new Chartist.Line('.ct-chart-sales-value', {
                labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                series: [[0, 10, 30, 40, 80, 60, 100]]
            }, {
                low: 0,
                showArea: true,
                fullWidth: true,
                plugins: [Chartist.plugins.tooltip()],
                axisX: { position: 'end', showGrid: true },
                axisY: { showGrid: false, showLabel: false, labelInterpolationFnc: value => `$${value / 1}k` }
            });
        }

        if (d.querySelector('.ct-chart-ranking')) {
            const chart = new Chartist.Bar('.ct-chart-ranking', {
                labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
                series: [[1, 5, 2, 5, 4, 3], [2, 3, 4, 8, 1, 2]]
            }, {
                low: 0,
                showArea: true,
                plugins: [Chartist.plugins.tooltip()],
                axisX: { position: 'end' },
                axisY: { showGrid: false, showLabel: false, offset: 0 }
            });

            chart.on('draw', data => {
                if (data.type === 'line' || data.type === 'area') {
                    data.element.animate({
                        d: {
                            begin: 2000 * data.index,
                            dur: 2000,
                            from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
                            to: data.path.clone().stringify(),
                            easing: Chartist.Svg.Easing.easeOutQuint
                        }
                    });
                }
            });
        }

        if (d.querySelector('.ct-chart-traffic-share')) {
            const data = { series: [70, 20, 10] };
            const sum = (a, b) => a + b;

            new Chartist.Pie('.ct-chart-traffic-share', data, {
                labelInterpolationFnc: value => `${Math.round(value / data.series.reduce(sum) * 100)}%`,
                low: 0,
                high: 8,
                donut: true,
                donutWidth: 20,
                donutSolid: true,
                fullWidth: false,
                showLabel: false,
                plugins: [Chartist.plugins.tooltip()]
            });
        }
    };

    // Load extra content on button click
    const loadExtraContent = () => {
        const loadOnClickBtn = d.getElementById('loadOnClick');

        if (loadOnClickBtn) {
            loadOnClickBtn.addEventListener('click', () => {
                const loadContent = d.getElementById('extraContent');
                const allLoadedText = d.getElementById('allLoadedText');

                loadOnClickBtn.classList.add('btn-loading');
                loadOnClickBtn.setAttribute('disabled', 'true');

                setTimeout(() => {
                    loadContent.style.display = 'block';
                    loadOnClickBtn.style.display = 'none';
                    allLoadedText.style.display = 'block';
                }, 1500);
            });
        }
    };

    // Initialize Smooth Scroll
    const initSmoothScroll = () => {
        new SmoothScroll('a[href*="#"]', { speed: 500, speedAsDuration: true });
    };

    // Set current year
    const setCurrentYear = () => {
        const currentYearEl = d.querySelector('.current-year');

        if (currentYearEl) {
            currentYearEl.textContent = new Date().getFullYear();
        }
    };

    // Initialize Glide.js
    const initGlide = () => {
        const glideElements = [
            { selector: '.glide', options: { type: 'carousel', startAt: 0, perView: 3 } },
            { selector: '.glide-testimonials', options: { type: 'carousel', startAt: 0, perView: 1, autoplay: 2000 } },
            { selector: '.glide-clients', options: { type: 'carousel', startAt: 0, perView: 5, autoplay: 2000 } },
            { selector: '.glide-news-widget', options: { type: 'carousel', startAt: 0, perView: 1, autoplay: 2000 } },
            { selector: '.glide-autoplay', options: { type: 'carousel', startAt: 0, perView: 3, autoplay: 2000 } }
        ];

        glideElements.forEach(glide => {
            if (d.querySelector(glide.selector)) {
                new Glide(glide.selector, glide.options).mount();
            }
        });
    };

    // Handle pricing countup
    const handlePricingCountup = () => {
        const billingSwitchEl = d.getElementById('billingSwitch');

        if (billingSwitchEl) {
            const countUpStandard = new countUp.CountUp('priceStandard', 99, { startVal: 199 });
            const countUpPremium = new countUp.CountUp('pricePremium', 199, { startVal: 299 });

            billingSwitchEl.addEventListener('change', () => {
                if (billingSwitch.checked) {
                    countUpStandard.start();
                    countUpPremium.start();
                } else {
                    countUpStandard.reset();
                    countUpPremium.reset();
                }
            });
        }
    };

    // Initialize all functions
    initSwal();
    handleThemeSettings();
    handleSidebar();
    handleNotifications();
    setBackgrounds();
    initTooltips();
    initPopovers();
    initDatepickers();
    initSliders();
    initCharts();
    loadExtraContent();
    initSmoothScroll();
    setCurrentYear();
    initGlide();
    handlePricingCountup();
});
