$.material.init();
$("#dropdown-menu select").dropdown();

(function() {
    'use strict';

    angular

        .module('morepianer', [
            // 'ui.select',
            'ui.router',
            'ui.validate',
            'ngSanitize',
            'ui.bootstrap'
        ])

        .config(function($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise('/');
        })

        .run(function($rootScope, $state) {
            $rootScope.$on('$stateChangeStart', function(event, toState, toStateParams) {
                $rootScope.toState = toState;
                $rootScope.toStateParams = toStateParams;
            });

            $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
                $rootScope.previousStateName = fromState.name;
                $rootScope.previousStateParams = fromParams;
            });

            $rootScope.back = function() {
                // If previous state is 'activate' or do not exist go to 'home'
                if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
                    $state.go('home');
                } else {
                    $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                }
            };

        });

})();
