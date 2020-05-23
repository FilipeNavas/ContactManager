$(document).ready(function () {
   
    //hides main alert after a few seconds
    $(".main-alert").delay(2500).fadeOut(200, function() {
        $(this).alert('close');
    });
    
});