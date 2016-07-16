(function() {
    'use strict';

    angular.module('morepianer').config(function($stateProvider) {
        $stateProvider.state("about", {
            parent:  'site',
            url:     "/about",
            views:   {
                'content@': {
                    templateUrl: 'scripts/page/about/about.html',
                    controller:  'AboutController'
                }
            },
            resolve: {}
        });
    });

})();
