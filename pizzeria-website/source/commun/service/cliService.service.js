(function () {
    'use strict';
    var service = function($http, pizzConst, $cookies) {

        var clientConnecte;

        this.getAllClient = function(params) {
            return $http.get(pizzConst.apiUrl + "clients", params);
        };
        this.getOneClient = function(id) {
            return $http.get(pizzConst.apiUrl + "clients/" + id);
        };
        this.postClient = function(data) {
            return $http.post(pizzConst.apiUrl + "clients", data);
        };
        this.connectionClient = function(data) {
            return $http.post(pizzConst.apiUrl + "clients/connection", data).then(function(result) {
                localStorage.setItem("client", JSON.stringify(result.data));
                return result;  
            });
        };
        this.deconnectionClient = function() {
            localStorage.removeItem("client");
        };
        this.isConnected = function() {
            return localStorage.getItem("client") !== null;
        };

        this.getConnected = function() {
            return JSON.parse(localStorage.getItem("client"));
        };

        this.getClientConnecte = function() {
           return JSON.parse(localStorage.getItem("client"));
        };
    };

    module.exports = service;
}());
