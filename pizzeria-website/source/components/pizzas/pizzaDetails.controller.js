(function () {
    'use strict';
    var controller = function (pizzService, $log, $routeParams,constantImg) {
        var pizzId = $routeParams.id;
        var vm = this;
        var pizza;
        var url;
        this.constImg = constantImg.apiUrlImg ;
        console.log("constImg", vm.constImg );

        pizzService.getAllPizzas().then(function (result) {
            vm.allPizzas = result.data;
            /*console.log("all", vm.allPizzas);*/
            vm.pizza = vm.allPizzas.find(function (maPizza) {
                return maPizza.id == pizzId;
            });
            /*console.log("pizza", vm.pizza);*/
            /*console.log("pizza ingrediants", vm.pizza.ingredients);*/

            // suprimer le dernier / pour éviter d'avoir 2 lors de la concatenation
            vm.constImg = vm.constImg.substring(0,vm.constImg.length-1);
            console.log("constImg2", vm.constImg);
            vm.url = vm.constImg + vm.pizza.urlImage;
            console.log("URL", vm.url);
    
        });

       /* vm.replaceUnderScoreToSpace = function (nomAvecUnderScore){
            var nomSansUnderScore = nomAvecUnderScore.replace("_"," ");
            return nomSansUnderScore;
        };*/

       
    };

    module.exports = controller;
} ());
