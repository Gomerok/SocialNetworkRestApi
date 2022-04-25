$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/v1/users/?pageSize=10",
        headers: {
            Authorization: 'Beare_'+ $.cookie('token')
        },
        dataType: 'json'
    }).then(function(data) {
        // // $('.greeting-id').append(data[1].id);
        // // $('.greeting-content').append(data[1].username);
        //
        // for (var i = 0; i < data.length; i++){
        //     var id = data[i].id;
        //     var name = data[i].username;
        // };



        $.each(data, function (key, value) {
            $('#users').append("<tr>\
										<td>"+value.id+"</td>\
										<td>"+value.username+"</td>\
										<td>"+value.email+"</td>\
										</tr>");
        })

    });
});

