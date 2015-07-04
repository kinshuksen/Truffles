angular.module('app.controllers.hotcold', [])

.controller('ThermoCtrl', function ($scope) {
    function RGB2HTML(red, green, blue) {
        var decColor = 0x1000000 + blue + 0x100 * green + 0x10000 * red;
        return '#' + decColor.toString(16).substr(1);
    }

    function initialize() {

        $scope.startScan();

        $('#thermo').thermometer({
            height: 400,
            textColour: '#000',
            tickColour: '#000',
            liquidColour: function (value) {
                var red = ~~(value / 100 * 255);
                var grn = ~~((100 - value) / 100 * 255);
                return RGB2HTML(red, grn, 0);
            },
            onLoad: function () {
                updateThermometer();
            }
        });
    };
    initialize();

    function updateThermometer() {
        $('#thermo').thermometer('setValue', $scope.distance * 8);
        //window.setTimeout(updateThermometer(), 2500);
    };

    $scope.update = function () {
        updateThermometer();
    }
});


