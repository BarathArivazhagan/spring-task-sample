(function() {
    'use strict';

    var app = angular.module('adceEditor');

    app.controller('ArisServiceConfigurationCtrl', ['$scope', '$modal',


        $scope.isManual=false;
        $scope.isService=false;
        $scope.serviceType="";
        $scope.onActionChange=function(){

            if($scope.serviceType === "manual"){
              $scope.isManual=true;
            }else if($scope.serviceType === "service"){
                $scope.isService=true;
            }else{
              $scope.isManual=false;
              $scope.isService=false;
            }

        };

    ]);

})(); // ends main function
