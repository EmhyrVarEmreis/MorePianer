(function() {
    'use strict';

    angular.module('morepianer').controller('HeaderController',

        function($scope) {

            $scope.exit = function() {
                systemService.exit();
            };

        });

})();
