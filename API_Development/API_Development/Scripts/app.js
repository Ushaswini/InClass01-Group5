function ViewModel() {
    var self = this;

    var tokenKey = 'accessToken';

    self.result = ko.observable();
    self.user = ko.observable();

    self.registerEmail = ko.observable();
    self.registerPassword = ko.observable();
    self.registerPassword2 = ko.observable();

    self.name = ko.observable();
    self.age = ko.observable();
    self.address = ko.observable();
    self.weight = ko.observable();

    self.newname = ko.observable();
    self.newage = ko.observable();
    self.newweight = ko.observable();
    self.newaddress = ko.observable();
    self.newpassword = ko.observable();
    self.newpassword2 = ko.observable();
    self.oldpassword = ko.observable();

    self.loginEmail = ko.observable();
    self.loginPassword = ko.observable();
    self.errors = ko.observableArray([]);

    function showError(jqXHR) {

        self.result(jqXHR.status + ': ' + jqXHR.statusText);

        var response = jqXHR.responseJSON;
        if (response) {
            if (response.Message) self.errors.push(response.Message);
            if (response.ModelState) {
                var modelState = response.ModelState;
                for (var prop in modelState)
                {
                    if (modelState.hasOwnProperty(prop)) {
                        var msgArr = modelState[prop]; // expect array here
                        if (msgArr.length) {
                            for (var i = 0; i < msgArr.length; ++i) self.errors.push(msgArr[i]);
                        }
                    }
                }
            }
            if (response.error) self.errors.push(response.error);
            if (response.error_description) self.errors.push(response.error_description);
        }
    }

    self.callApi = function () {
        self.result('');
        self.errors.removeAll();

        var token = sessionStorage.getItem(tokenKey);
        var headers = {};
        if (token) {
            headers.Authorization = 'Bearer ' + token;
        }

        $.ajax({
            type: 'GET',
            url: 'api/Account/UserInfo',
            //url: '/api/values',
            headers: headers
        }).done(function (data) {
            self.result(data.Name + " of age "+ data.Age + " and weight "+ data.Weight +" kgs stays at " + data.Address + ".");
        }).fail(showError);
    }

    self.register = function () {
        self.result('');
        self.errors.removeAll();

        var data = {
            Email: self.registerEmail(),
            Password: self.registerPassword(),
            ConfirmPassword: self.registerPassword2(),            
            Name: self.name(),
            Age: self.age(),
            Address: self.address(),
            Weight: self.weight()
           
        };

        $.ajax({
            type: 'POST',
            url: '/api/Account/Register',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (data) {
            self.result("Done!");
        }).fail(showError);
    }

    self.login = function () {
        self.result('');
        self.errors.removeAll();

        var loginData = {
            grant_type: 'password',
            username: self.loginEmail(),
            password: self.loginPassword()
        };

        $.ajax({
            type: 'POST',
            url: '/Token',
            data: loginData
        }).done(function (data) {
            self.user(data.userName);
            console.log(data);
            // Cache the access token in session storage.
            sessionStorage.setItem(tokenKey, data.access_token);
        }).fail(showError);
    }

    self.logout = function () {
        // Log out from the cookie based logon.
        var token = sessionStorage.getItem(tokenKey);
        var headers = {};
        if (token) {
            headers.Authorization = 'Bearer ' + token;
        }

        $.ajax({
            type: 'POST',
            url: '/api/Account/Logout',
            headers: headers
        }).done(function (data) {
            // Successfully logged out. Delete the token.
            self.user('');
            sessionStorage.removeItem(tokenKey);
        }).fail(showError);
    }

    self.change = function () {

        var token = sessionStorage.getItem(tokenKey);
        var headers = {};
        if (token) {
            headers.Authorization = 'Bearer ' + token;
        }

        var data = {
            Email: "testing@gmail.com",
            
            
            Name: self.newname(),
            Age: self.newage(),
            Address: self.newaddress(),
            Weight: self.newweight(),
           
        };

        $.ajax({
            type: 'POST',
            url: 'api/Account/UpdateProfile',
            contentType: 'application/json; charset=utf-8',
            headers: headers,
            data: JSON.stringify(data)
        }).done(function (data) {
            self.result("Done!");
        }).fail(showError);
    }

    self.changePassword = function () {

        var token = sessionStorage.getItem(tokenKey);
        var headers = {};
        if (token) {
            headers.Authorization = 'Bearer ' + token;
        }
        var data = {
           

            OldPassword: self.oldpassword(),
            NewPassword: self.newpassword(),
            ConfirmPassword: self.newpassword2()
        };

        $.ajax({
            type: 'POST',
            url: 'api/Account/ChangePassword',
            contentType: 'application/json; charset=utf-8',
            headers: headers,
            data: JSON.stringify(data)
        }).done(function (data) {
            self.result("Done!");
        }).fail(showError);
    }

}

var app = new ViewModel();
ko.applyBindings(app);