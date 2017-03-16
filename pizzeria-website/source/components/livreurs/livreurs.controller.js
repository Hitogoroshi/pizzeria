(function () {
    'use strict';
    var controller = function(livService, $log) {
        var vm = this;
        livService.getAllLivreurs().then(function(result) {
            vm.allLivreurs = result.data;
        });
    };

    module.exports = controller;
}());
