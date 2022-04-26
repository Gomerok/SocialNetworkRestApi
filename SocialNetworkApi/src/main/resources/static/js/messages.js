$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/v1/users/3/messages",
        headers: {
            Authorization: 'Bearer_' + $.cookie('token')
        },
        dataType: 'json',
        success: function (response) {
            $.each(response, function (key, value) {
                $('#messages').append("<tr>\
										<td>" + value.recipientId + "</td>\
										<td>" + value.value + "</td>\
										</tr>");
            })
        },
        error: function (httpObj, textStatus) {
            console.log(httpObj.status);
        }

    });


    $("#send").click(function () {

        $.ajax({
            type: "POST",
            url: "http://localhost:8080/api/v1/users/3/messages",
            datatype: 'json',
            data: JSON.stringify({'value': $("#name").val()}),
            contentType: "application/json; charset=utf-8",

            success: function (response) {
                $('#messages').append("<tr>\
										<td>" + value.recipientId + "</td>\
										<td>" + value.value + "</td>\
										</tr>");
            },
            error: function (xhr, status, error) {
                var err = JSON.parse(xhr.responseText);
                $("#message").html(err.message);
            }
        });
    });
});
