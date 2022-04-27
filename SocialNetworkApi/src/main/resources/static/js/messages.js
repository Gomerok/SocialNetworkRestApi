$(document).ready(function () {
    var recipientId = 3;
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/v1/users/" + recipientId + "/messages",
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
});


function saveMessage() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/v1/users/3/messages",
        headers: {
            Authorization: 'Bearer_' + $.cookie('token')
        },
        datatype: 'json',
        data: JSON.stringify({'value': $("#name").val()}),
        contentType: "application/json; charset=utf-8",

        success: function (response) {
            $('#messages').append("<tr>\
										<td>" + response.recipientId + "</td>\
										<td>" + response.value + "</td>\
										</tr>");
            // getParameter('userId')
        },
        error: function (httpObj, textStatus) {
            console.log(httpObj.status);
        }
    });
}


// function getPathVariable(param) {
// let searchParams = new URLSearchParams(window.location.search)
// searchParams.has(param)
// let id = searchParams.get('userId')
// console.log("userId:");
// console.log(id);
//
// }

// function getParameter(p)
// {
//     var url = window.location.search.substring(1);
//     var varUrl = url.split('&');
//     for (var i = 0; i < varUrl.length; i++)
//     {
//         var parameter = varUrl[i].split('=');
//         if (parameter[0] == p)
//         {
//             console.log(parameter[1]);
//             return parameter[1];
//         }
//     }
// }


$(function () {
    $("#send").click(function () {
        saveMessage();
    });
});
