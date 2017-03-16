(function () {
    'use strict';

    var config = function($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: "./source/components/home/home.html",
            controller: "homeCtrl",
            controllerAs: 'home'
        });
        $routeProvider.when('/livreurs', {
            templateUrl: "./source/components/livreurs/livreurs.html",
            controller: "livCtrl",
            controllerAs: 'liv'
        });
        /*
         * Pizzas
         */
        $routeProvider.when('/pizzas', {
            templateUrl: "./source/components/pizzas/pizzas.html",
            controller: "pizzCtrl",
            controllerAs: 'pizz'
        });
        $routeProvider.when('/pizzas/:id', {
            templateUrl: "./source/components/pizzas/pizzaDetails.html",
            controller: "pizzDetailsCtrl",
            controllerAs: 'pizzD'
        });
        /*
         * Clients
         */
        $routeProvider.when('/inscription', {
            templateUrl: "./source/components/client/inscription.html",
            controller: "insriCtrl",
            controllerAs: 'inscri'
        });
        $routeProvider.when('/connection', {
            templateUrl: "./source/components/client/connection.html",
            controller: "clientCtrl",
            controllerAs: 'client'
        });
        $routeProvider.when('/deconnection', {
            templateUrl: "./source/components/client/deconnection.html",
            controller: "decoCtrl",
            controllerAs: 'deco'
        });

        /**
         * Commande
         */
        $routeProvider.when('/commande', {
            templateUrl: "./source/components/commande/cmd.html",
            controller: "commandeCtrl",
            controllerAs: 'cmd'
        });
        $routeProvider.when('/listCommandes', {
            templateUrl: "./source/components/commande/listCommandes.html",
            controller: "comCtrl",
            controllerAs: 'com'
        });
    };

    module.exports = config;
}());
