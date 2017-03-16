(function () {
    'use strict';
    var service = function($http, pizzConst) {
        this.postCommandPizza = function(data) {
            return $http.post(pizzConst.apiUrl + "commandes_pizza", data);
        };
    };

    module.exports = service;
}());
