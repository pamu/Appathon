$(function() {
    $("#form_submit").click(function() {
        var name = $("#name").val();
        var email = $("#email").val();
        var message = $("#message").val();
        if(name.trim().length == 0) {
            $("#name_msg").text("Name is required");
            return;
        }
        if(email.trim().length == 0) {
            $("#email_msg").text("Email is required");
            return;
        }
        if(message.trim().length == 0) {
            $("#message_msg").text("Message is required");
            return;
        }
        
        var item = {};
        item["name"] = name;
        item["email"] = email;
        item["message"] = message;
        
        var json = JSON.stringify(item);

        $.ajax({
            url: '/contact',
            type: 'POST',
            data: json,
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: true,
            success: function(msg) {
                if(msg.status == 200) {
                    $("#form").empty();
                    $("#form").html("<p>Thank you for your interest :)</p>")
                }
            }
        });
    });
});