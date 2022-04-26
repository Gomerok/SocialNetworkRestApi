$(document).ready(function () {
    $("#but_submit").click(function () {
        var username = $("#txt_uname").val().trim();
        var password = $("#txt_pwd").val().trim();

        if (username != "" && password != "") {

            $.ajax({
                type: "POST",
                url: "http://localhost:8080/api/v1/auth/login",
                datatype: 'json',
                data: JSON.stringify({"username": username, "password": password}),
                contentType: "application/json; charset=utf-8",

                success: function (response) {
                    $.cookie("token", response.token)
                    // console.log( $.cookie('token'));
                    window.location = 'http://localhost:8080/home';
                },
                error: function (xhr, status, error) {
                    var err = JSON.parse(xhr.responseText);
                    $("#message").html(err.message);
                }
            });
        }
    });
});