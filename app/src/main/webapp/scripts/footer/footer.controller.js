(function() {
    'use strict';

    angular.module('morepianer').controller('FooterController',

        function($scope) {

            $scope.exit = function() {
                console.log("EXIT");
            };

        });

})();
