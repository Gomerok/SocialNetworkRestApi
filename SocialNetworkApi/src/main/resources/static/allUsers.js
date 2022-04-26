$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/v1/users/",
        headers: {
            Authorization: 'Bearer_' + $.cookie('token')
            // Authorization: 'Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY1MDk2MDgxMCwiZXhwIjoxNjUwOTYyNjEwfQ.aUEM7LdSrPb-N-xtrRJK5BenuLYmGqIJIwvq1V28V2s'
        },
        dataType: 'json',


        success: function (response) {
            $.each(response, function (key, value) {
                $('#users').append("<tr>\
										<td>" + value.id + "</td>\
										<td>" + value.username + "</td>\
										<td>" + value.email + "</td>\
										</tr>");
            })
        },
        error: function(httpObj, textStatus) {
            console.log(httpObj.status);
            // var err = JSON.parse(xhr.responseText);
            //
            // $("#message").html(err.message);

            // request.error(function(httpObj, textStatus) {
            //     if(httpObj.status==200)
            //         loginSuccess();
            //     else
            //         loginFail();
            // });

        }


    });
});

