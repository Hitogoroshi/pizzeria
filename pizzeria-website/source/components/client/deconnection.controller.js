(function () {
    'use strict';
    var controller = function(cliService, $log, $timeout) {
        var vm = this;
        vm.Action = function() {
            cliService.deconnectionClient();
        };
        vm.isConnected = cliService.isConnected;
        vm.getClientConnecte = cliService.getClientConnecte;

    }
        module.exports = controller;
    }());