(function() {
    'use strict';

    angular.module('morepianer').config(function($stateProvider) {
        $stateProvider.state("panel", {
            parent:  'site',
            url:     "/",
            views:   {
                'content@': {
                    templateUrl: 'scripts/page/home/home.html',
                    controller:  'HomeController'
                }
            },
            resolve: {}
        });
    });

})();
