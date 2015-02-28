$(function() {
    function initialize() {
        var mapOptions = {
                          center: new google.maps.LatLng(31.773792, 76.984102),
                          zoom: 8,
                          mapTypeId: google.maps.MapTypeId.ROADMAP
                        };
        var map = new google.maps.Map($("#map-canvas")[0], mapOptions);
    }
    google.maps.event.addDomListener(window, 'load', initialize);
});