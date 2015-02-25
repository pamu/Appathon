$(function() {
    if(!!window.EventSource) {
        var source = new EventSource("/h")
    } else {
    }
});