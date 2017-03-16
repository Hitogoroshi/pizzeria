(function() {
    'use strict';

    var directive = function(pizzService, constantImg) {
        return {
            restrict: "A",
            scope: {
                imgUrl: "="
            },
            template: "<img style=\"width : 100% \" ng-src=\"{{urlImg}}\">",
            link: function(scope, element, attrs) {
                scope.urlImg = constantImg.apiUrlImg + scope.imgUrl ;
                /*console.log("URL de l'image : " + scope.urlImg);*/
            }
        };
    };
    module.exports = directive;
}());