(function () {
    'use strict';
    var service = function($http, pizzConst) {

        this.getAllPizzas = function() {
            return $http.get(pizzConst.apiUrl + "pizzas");
        };
        this.postPizza = function(data) {
            return $http.post(pizzConst.apiUrl + "pizzas", data);
        };
        this.getOne = function(id) {
            return $http.get(pizzConst.apiUrl + "pizzas/" + id);
        };
        //Fonction ajout d'une nouvelle pizza dans le panier
        this.ajoutpizza = function(pizz, panier, pizza) {
            pizz.id = pizza.id;
            pizz.pizza = pizza;
            pizz.quan = 1;
            panier.push(pizz);
            return panier;
        };
    };

    module.exports = service;
}());
