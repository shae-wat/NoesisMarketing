<!-- Cookie Handling -->
<script type="text/javascript">

    function userController($scope) {

        // autofill functionality using angular, runs on page load
        $scope.$parent.First_Name_Casual__c = getCookie("firstName");
        $scope.$parent.LastName  = getCookie("lastName");
        $scope.$parent.Email  = getCookie("email");

        $scope.setCookies = function(firstName, lastName, email)
        {
            document.cookie = "firstName = " + firstName + "; ";
            document.cookie = "lastName = " + lastName + "; ";
            document.cookie = "email = " + email + "; ";
        };
    }

    function getCookie(cookieVar) {
        var name = cookieVar + "=";
        var ca = document.cookie.split(';');
        //console.log("ca = " + ca);
        for(var i=0; i<ca.length; i++) {
            var c = ca[i];
            //console.log("c = " + c);
            while (c.charAt(0)==' ') c = c.substring(1);
            if (c.indexOf(name) != -1) {
                //console.log("c.substring(name.length, c.length) = " + c.substring(name.length, c.length));
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }



</script>