var app = angular.module('app', ['pascalprecht.translate'])
    .config(function($translateProvider) {
        $translateProvider.useStaticFilesLoader({
            prefix: '/static/lang/',
            suffix: '.json'
        });

        $translateProvider.preferredLanguage('et');
    });

app.controller('ServiceInfoController', function($scope, $http) {
    updateServiceInfo($scope, $http);
    setInterval(function() {
        updateServiceInfo($scope, $http);
    }, 1000);
});

function updateServiceInfo($scope, $http) {
    $http.get("/getServiceInfo").success(function (data) {
        $scope.serviceInfo = data;
    });
}