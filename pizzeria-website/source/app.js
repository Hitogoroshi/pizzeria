(function () {
    "use strict";
    /*
     * Load Librairies
     */
    require("angular");
    require("angular-route");
    require("angular-translate");
    require("angular-cookies");
    require("angular-ui-bootstrap");

    angular.module("pizzeria", ['ngRoute', 'pascalprecht.translate', 'ngCookies', 'ui.bootstrap'])
        // Load services
        .service("pizzService", require("./commun/service/pizzService.service"))
        .service("cliService", require("./commun/service/cliService.service"))
        .service("cmdService", require("./commun/service/cmdService.service"))
        .service("livService", require("./commun/service/livService.service"))
        .service("cmdPizzService", require("./commun/service/cmdPizzService.service"))
        .service("localeStorageService", require("./commun/service/localeStorageService.service"))
        // Load config
        .config(require("./commun/config/app.config"))
        // Load routes
        .config(require("./commun/config/routes.config"))
        // Load constant
        .constant("pizzConst", require("./commun/config/config.constant"))
        .constant("constantImg", require("./commun/config/img.constant"))
        /*
        * Load Lodash
        */
        .constant("_", require("lodash"))
        /*
        * Home
        */
        .controller("homeCtrl", require("./components/home/home.controller"))
        /*
        * Livreurs
        */
        .controller("livCtrl", require("./components/livreurs/livreurs.controller"))
        /*
        * Pizzas
        */
        .controller("pizzCtrl", require("./components/pizzas/pizzas.controller"))
        .controller("pizzDetailsCtrl", require("./components/pizzas/pizzaDetails.controller"))
        .directive("imgUrl", require("./components/pizzas/pizzas.directive"))

        /*
        * Client
        */
        .controller("insriCtrl", require("./components/client/inscription.controller"))
        .controller("clientCtrl", require("./components/client/connection.controller"))
        .controller("decoCtrl", require("./components/client/deconnection.controller"))
        /** 
        * Translate 
        */
        .controller("translateCtrl", require("./commun/translate/translate.controller"))
        .config(require("./commun/config/translate.config"))

        /**
         * Commande 
         */
        .controller("commandeCtrl", require("./components/commande/commande.controller"))
        .directive("panierCmd", require("./components/commande/commande.directive"))
        .controller("comCtrl", require("./components/commande/listCommande.controller"))

        // Load directive
        .directive("monPanier", require("./components/panier/monpanier.directive"));




} ());

