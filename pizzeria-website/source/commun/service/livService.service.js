(function () {
    'use strict';
    var service = function($http, pizzConst) {
        this.getAllLivreurs = function(params) {
            return $http.get(pizzConst.apiUrl + "livreurs", params);
        };
        this.postLivreur = function(data) {
            return $http.post(pizzConst.apiUrl + "livreurs", data);
        };
        this.getOneLivreur = function(id) {
            return $http.get(pizzConst.apiUrl + "livreurs/" + id);
        };
        this.updateOne = function(id, data) {
            return $http.patch(pizzConst.apiUrl + "livreurs/" + id, data);
        };
    };

    module.exports = service;
}());
