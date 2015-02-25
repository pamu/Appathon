$(function() {

    var call = $.get("/hits", function(data) {
        $("#hits").text(data);
    });
    
    call.fail(function() {
        $("#hits").text(0);
    });
    
    if(!!window.EventSource) {
        var source = new EventSource("/hitsStream")
        source.addEventListener('message', function(e) {
            var data = e.data;
            $("#hits").text(data);
        });
    } else {
        $("#hits_section").html("Sorry. This browser doesn't seem to support Server sent event.Check <a href='http://html5test.com/compare/feature/communication-eventSource.html'>html5test</a> for browser compatibility.");
    }
});