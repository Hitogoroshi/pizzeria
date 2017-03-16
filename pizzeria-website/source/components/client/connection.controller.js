(function () {
    'use strict';
    var controller = function($window, cliService, $log, $timeout) {
        var vm = this;
        vm.error = false;
        vm.success = false;
        vm.client = {};
        vm.logClient = function() {
            vm.client.email = vm.cli.email;
            vm.client.password = vm.cli.password;
            cliService.connectionClient(vm.client).then(function(result) { 
                vm.success = true;
                vm.cli = {};
                $timeout(function() {
                    vm.success = false;
                    $window.location.href = '/#/pizzas';
                }, 2000);
            }, function() {
                vm.error = true;
                $timeout(function() {
                    vm.error = false;
                }, 5000);
            });
        };
    };

    module.exports = controller;
}());