(function() {
    'use strict';

    angular.module('morepianer').config(function($stateProvider) {
        $stateProvider.state("site", {
            abstract: true,
            views:    {
                'header@': {
                    templateUrl:  'scripts/header/header.html',
                    controller:   'HeaderController',
                    controllerAs: 'header'
                },
                'footer@': {
                    templateUrl:  'scripts/footer/footer.html',
                    controller:   'FooterController',
                    controllerAs: 'footer'
                }
            }
        });
    });

})();