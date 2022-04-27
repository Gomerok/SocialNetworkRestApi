$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/v1/users/",
        headers: {
            Authorization: 'Bearer_' + $.cookie('token')
        },
        dataType: 'json',


        success: function (response) {
            $.each(response, function (key, value) {
                $('#users').append("<tr>\
										<td>" + value.id + "</td>\
										<td>" + value.username + "</td>\
										<td>" + value.email + "</td>\
										<td>" + "<a title=\"Blah\" href=\"http://localhost:8080/messagesPage\">Message</a>" + "</td>\
										</tr>");
            })
        },
    // <td>" + "<a title=\"Blah\" href=\"http://localhost:8080/messagesPage?userId=" + value.id + "\">Message</a>" + "</td>\

        error: function(httpObj, textStatus) {
            console.log(httpObj.status);
        }


    });
});





