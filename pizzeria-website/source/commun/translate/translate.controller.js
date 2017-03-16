(function () {
    'use strict';
    var controller = function ($translate, $scope, $log, $cookies) {
        if($cookies.get("language") !== null) {
            $translate.use($cookies.get("language"));
            this.cookievalue = $cookies.get("language");
        }
        else {
            $cookies.put("language","fr");
            $translate.use(key);
            this.cookievalue = $cookies.get("language");
        }
        this.changeLanguage = function (key) {
            $cookies.put("language",key);
            $translate.use(key);
            this.cookievalue = $cookies.get("language");
        };
    };
    module.exports = controller;
} ()); 
