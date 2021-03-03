//start function when document is ready
$(function () {
    tableUser()
    authenticationUser()
})

//function to add user
$(document).on("click", "#btnAddNewUser", function () {
    let user =$("#formNewUser").serialize();

    $.ajax({
        url: "/users",
        type: "POST",

        data: user,
        timeout: 3000,
        success: function (data){
            tableUser(data)
            $('#table-tab').trigger('click');
        }
    })
    $("input[id*='NewUserI']").val('');
})


//get all users
function tableUser(){
    $.ajax({
        type: "GET",
        url: "/users",
        response: "json",
        success: function (data){
            setUsersList(data)
        }
    })
}

//
function authenticationUser(){
    $.ajax({
        type: "GET",
        url: "/users/now",
        response: "json",
        success: function (data) {
            tableAuth(data);
        }
    })
}
//get table for authentication user
function tableAuth(user){
$('<tr>').append(
    $('<td>').text(user.id),
    $('<td>').text(user.name),
    $('<td>').text(user.lastName),
    $('<td>').text(user.age),
    $('<td>').text(user.email),
    $('<td>').text((user.role).map(r => r.role.replace('ROLE_','')).join(', ')),
).appendTo("#tableForActiveUser")}

//filling in the table
function setUsersList(users) {
    $("#users-list").empty();
    $.each(users, function (i, user) {
        $('<tr>').append(
            $('<td>').text(user.id),
            $('<td>').text(user.name),
            $('<td>').text(user.lastName),
            $('<td>').text(user.age),
            $('<td>').text(user.email),
            $('<td>').text((user.role).map(r => r.role.replace('ROLE_','')).join(', ')),
            $('<td>').append($('<button>').text("Edit").attr({
                "type": "button",
                "class": "btn btn-primary btn-sm edit",
                "style": "background-color: royalblue",
                "data-toggle": "modal",
                "data-target": "#editModal",
            }).data("user", user)),
            $('<td>').append($('<button>').text("Delete").attr({
                "type": "button",
                "class": "btn btn-danger btn-sm delete",
                "data-toggle": "modal",
                "data-target": "#delModal",
            }).data("user", user))
        ).appendTo('#users-list')
    })
}

//modal window to edit the user
$(document).on("click", ".edit", function () {
    let user = $(this).data("user");
    $("#idEdit").val(user.id);
    $("#nameEdit").val(user.name);
    $("#lastEdit").val(user.lastName);
    $("#ageEdit").val(user.age);
    $("#emailEdit").val(user.email);
    $("#passwordEdit").val(user.password);
})

//post to edit user
$(document).on("click", "#btnEdit", function () {
    let user = $("#formToEdit").serialize();

    $.ajax({
        type: "PUT",
        url: "/users/edit",
        data: user,
        timeout: 3000,
        success: function (){
            $('#editModal').modal('hide')
            tableUser()
        }
    })
})

//modal window to delete thr user
$(document).on("click", ".delete", function () {
    let user = $(this).data("user");
    $("#idDel").val(user.id);
    $("#nameDel").val(user.name);
    $("#lastDel").val(user.lastName);
    $("#ageDel").val(user.age);
    $("#emailDel").val(user.email);
})

//post to delete user
$(document).on("click", "#btnDel", function () {
    let user = $("#formDel").serialize();

    $.ajax({
        type: "DELETE",
        url: "/users/del",
        data: {id : $('#idDel').val()},
        timeout: 3000,
        success: function (){
            $('#delModal').modal('hide')
            tableUser()
        }
    })
})