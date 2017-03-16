(function () {
    'use strict';
    var service = function($http, pizzConst) {
        this.getAllCommands = function(data) {
            return $http.get(pizzConst.apiUrl + "commandes");
        };
        this.getOne = function(id) {
            return $http.get(pizzConst.apiUrl + "commandes?idClient=" + id);
        };
        this.postCommand = function(data) {
            return $http.post(pizzConst.apiUrl + "commandes", data);
        };
    };
    module.exports = service;
}());
