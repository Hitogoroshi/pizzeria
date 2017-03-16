(function () {
    'use strict';
    var service = function ($http,$rootScope,pizzConst) {
        var vm = this;
        this.getDataLocalestorage = function () {
            return JSON.parse(localStorage.getItem("panier"));
        };

        this.postLocaleStorage = function (panier) {
            //Appel de mon event panier pour la récupération de celui ci
            $rootScope.$emit('panierevent', panier);
            return localStorage.setItem("panier", JSON.stringify(panier));
        };

        this.deleteElementLocaleStorage = function (pizza) {
            var monPanier = vm.getDataLocalestorage();
            var newPanier = [];

            monPanier.find(function (maPizza) {
                if (maPizza.id != pizza.id) {
                    newPanier.push(maPizza);
                }
                vm.clearPanier();
                vm.postLocaleStorage(newPanier);
            });

        };

        this.clearPanier = function () {
            localStorage.removeItem("panier");
        };
        //Initialisation du panier en localStorage avec un tableau vide si inexistant
        if (!vm.getDataLocalestorage()) {
            var emptyArray = [];
            localStorage.setItem("panier", JSON.stringify(emptyArray));
        }
        // enregistrement de la commande en base
        this.postCommande = function (cmd) {
            return $http.post(pizzConst.apiUrl + "commandes",cmd);
        };
    };
    module.exports = service;
} ());
