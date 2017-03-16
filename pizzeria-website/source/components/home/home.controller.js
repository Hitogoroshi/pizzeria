(function () {
    'use strict';
    var controller = function(pizzService, cmdPizzService, cmdService, livService, cliService, $log, $q) {
        var vm = this;
        vm.clients = {};
        pizzService.getAllPizzas().then(function(result) {
            vm.pizzas = result.data;
            pizzService.allPizzas = result.data;
        });
        var command = {
            "livreur_id": "",
            "client_id": "",
            "etat_commande": "",
            "date_commande": ""
        };
        var requestPizzas = [];
        vm.commandPizza = function() {
            var date = new Date();
            /*
             * Set date + state command
             */
            command.date_commande = date.toISOString();
            command.etat_commande = "En cours";
            /*
             * Post new client
             */
            cliService.postClient(vm.clients).then(function(resultClient) {
                command.client_id = resultClient.data.id;
                /*
                 * Get all free "livreurs"
                 */
                livService.getAllLivreurs({
                    params: {
                        "disponibilite": true
                    }
                }).then(function(resultLivreur) {
                    command.livreur_id = resultLivreur.data[0].id;
                    /*
                     * Post new command
                     */
                    cmdService.postCommand(command).then(function(resultCommand) {
                        /*
                         * Create our command pizza object and promise
                         */
                        for (var id in vm.command_pizza.pizza) {
                            var command_pizza = {
                                "command_id" : resultCommand.data.id,
                                "pizza_id": id,
                                "quantite": vm.command_pizza.pizza[id].quantite,
                                "heure_commande": command.date_commande
                            };
                            requestPizzas.push(cmdPizzService.postCommandPizza(command_pizza));
                        }
                        /*
                         * Promise with all command pizza
                         */
                        $q.all(requestPizzas).then(function() {
                            /*
                             * Update our "livreur"
                             */
                            livService.updateOne(command.livreur_id, {disponibilite: false});
                        });
                    });
                });
            });
        };

        
        
        //Tout ce qui est en dessous concerne le carrousel
        vm.myInterval = 5000;
        vm.noWrapSlides = false;
        vm.active = 0;
        var slides = vm.slides = [
            {
                image: 'cd ../../assets/img/pizzeria.jpg',
                text: "HOME",
                id: 0
            },
            {
                image: "cd ../../assets/img/restaurant-pizzeria.jpg",
                text: "RESTAURANT",
                id: 1
            },
            {
                image: "cd ../../assets/img/pizza.jpg",
                text: "PIZZA",
                id: 2
            },{
                image: "cd ../../assets/img/pizzaspe.jpg",
                text: "PIZZA_MOIS",
                id:3
            },{
                image: "cd ../../assets/img/pate.jpg",
                text: "PATES",
                id: 4
            },{
                image: "cd ../../assets/img/salade.jpg",
                text: "SALADES",
                id: 5
            },
            
        ];
        var currIndex = 0;

        function assignNewIndexesToSlides(indexes) {
            for (var i = 0, l = slides.length; i < l; i++) {
                slides[i].id = indexes.pop();
            }
        }

        function generateIndexesArray() {
            var indexes = [];
            for (var i = 0; i < currIndex; ++i) {
                indexes[i] = i;
            }
            return shuffle(indexes);
        }

        // http://stackoverflow.com/questions/962802#962890
        function shuffle(array) {
            var tmp, current, top = array.length;

            if (top) {
                while (--top) {
                    current = Math.floor(Math.random() * (top + 1));
                    tmp = array[current];
                    array[current] = array[top];
                    array[top] = tmp;
                }
            }

            return array;
        }
    };

    module.exports = controller;
}());
