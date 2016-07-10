(function() {
    'use strict';

    angular.module('morepianer').controller('NavController',

        function($scope) {

            $scope.exit = function() {
                console.log("EXIT");
            };

        });

})();
