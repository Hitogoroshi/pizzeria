(function () {
    'use strict';
    var controller = function (pizzService, localeStorageService, $log, constantImg) {
        var constImg = constantImg.apiUrlImg;
        var vm = this;
        pizzService.getAllPizzas().then(function (result) {
            vm.allPizzas = result.data;
        });
        vm.panier = [];
        vm.pizz = {};
        vm.trouver = false;
        //vérification si le localStorage est pas vide on récupere le panier dedans
        //But ne pas perdre le panier client au F5 et enfaire un nouveau

        this.ajoutPanier = function (pizza) {
            // test si la pizza est en rupture
            if(pizza.disponible){
                //Test si le panier existe (Normalement oui , un panier se crée au lancement du service)
                if (localeStorageService.getDataLocalestorage()) {
                vm.panier = localeStorageService.getDataLocalestorage();
                    }
                //but de tester les différents cas:
                //dans le cas ou le panier n'est pas vide
                if (vm.panier.length > 0) {
                    for (var i = 0; i < vm.panier.length; i++) {
                        //si la pizza a ajouter exsite déjà dans le panier
                        if (pizza.id === vm.panier[i].id) {
                            vm.panier[i].quan += 1;
                            vm.trouver = true;
                        }
                    }
                    //Si la pizza n'exsite pas dans la liste'
                    if (!vm.trouver) {
                        pizzService.ajoutpizza(vm.pizz, vm.panier, pizza);
                        
                    }
                    vm.trouver = false;
                    //cas du panier vide
                } else {
                    pizzService.ajoutpizza(vm.pizz, vm.panier, pizza);
                }
                //reset de l'objet temporaire des pizzas
                vm.pizz = {};
                localeStorageService.postLocaleStorage(vm.panier);
            }
        };

        //mise en place d'un panier local qui recupere les pizzas

        vm.localpanier = localeStorageService.getDataLocalestorage();
    };
    module.exports = controller;
} ());