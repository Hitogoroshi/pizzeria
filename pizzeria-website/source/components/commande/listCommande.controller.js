(function () {
    'use strict';

    var controller = function (cliService, cmdService, $log, $timeout, localeStorageService,$window) {
        var vm = this;
        if (cliService.isConnected()) {
            vm.commandePizza = {};
            vm.total = [];
            cmdService.getOne(JSON.parse(localStorage.getItem("client")).id).then(function (result) {
                vm.commande = result.data;
                vm.commande.forEach(function (commande) {
                    commande.total = 0;
                    for (var i = 0; i < commande.pizzas.length; i++) {
                         commande.total += commande.pizzas[i].pizza.prix * commande.pizzas[i].quantite;
                    }
                }, this);
            });
        }else{
            $window.location.href = '/#/connection';
        }


    };
    module.exports = controller;
} ());