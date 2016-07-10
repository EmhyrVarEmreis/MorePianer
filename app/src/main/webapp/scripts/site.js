(function() {
    'use strict';

    angular.module('morepianer').config(function($stateProvider) {
        $stateProvider.state("site", {
            abstract: true,
            views:    {
                'nav@': {
                    templateUrl:  'scripts/nav/nav.html',
                    controller:   'NavController',
                    controllerAs: 'nav'
                }
            }
        });
    });

})();