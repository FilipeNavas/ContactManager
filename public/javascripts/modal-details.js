$(document).ready(function () {

    $(".btn-details").click(function () {

        var id = $(this).data('id');
        if (id <= 0) {
            showErrorMessage();
            return;
        }

        $.ajax({
            url: "/contacts/" + id + "/json ",
            method: "GET"
        }).fail(function () {
            showErrorMessage();
        }).done(function (data) {
            
            //populates the modal with the data
            $(".modal-firstname").text(data.firstname);
            $(".modal-lastname").text(data.lastname);
            $(".modal-phone").text(data.phone);
            $(".modal-email").text(data.email);
            $(".modal-dob").text(data.dob);
            
            //adds the id to the input for item to be deleted
            $("#id-input-delete").val(data.id);
            
            //adds the href to the edit link
            $("#id-link-edit").attr("href", "/contacts/" + data.id);
            
            $("#modalDetails").modal("toggle"); //shows modal
        });

    });

    function showErrorMessage(){
        
        $("#id-message-modal-details").fadeIn();
            
        //hides message after 2 seconds
        setTimeout(function(){
            $("#id-message-modal-details").fadeOut();
        }
        , 2500);        
    }
    
});




