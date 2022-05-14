$(document).ready(function() {
    if ($('#alertSuccess').text().trim() == "") {
        $('#alertSuccess').hide();
    }

    $('#alertError').hide();
})

// SAVE
$(document).on("click","#btnSave", function(event) {
    // Clear alerts
    $("#alertSuccess").text(""); 
    $("#alertSuccess").hide(); 
    $("#alertError").text(""); 
    $("#alertError").hide();

    // Form validation
    var status = validateIssueForm(); 
    if (status != true) 
    { 
        $("#alertError").text(status); 
        $("#alertError").show(); 
        return; 
    } 

    // if hidIssueIDSave value is null set as POST else set as PUT
    var type = ($("#hidIssueIDSave").val() == "") ? "POST" : "PUT";

    // ajax communication
    $.ajax({
        url: "IssuesAPI",
        type: type,
        data: $("#formIssue").serialize(),
        dataType: "text",
        complete: function(response, status) {
            onIssueSaveComplete(response.responseText, status);
        }
    });
});

// after completing save request
function onIssueSaveComplete(response, status) {

    if (status == "success") { //if the response status is success
        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() === "success") { //if the json status is success
            //display success alert
            $("#alertSuccess").text("Successfully saved");
            $("#alertSuccess").show();
    
            //load data in json to html
            $("#divIssuesGrid").html(resultSet.data);

        } else if (resultSet.status.trim() === "error") { //if the json status is error
            //display error alert
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") { 
        //if the response status is error
        $("#alertError").text("Error while saving");
        $("#alertError").show();
    } else { 
        //if an unknown error occurred
        $("#alertError").text("Unknown error occurred while saving");
        $("#alertError").show();
    } 

    //resetting the form
    $("#hidIssueIDSave").val("");
    $("#formIssue")[0].reset();
}

// UPDATE
//to identify the update button we didn't use an id we used a class
$(document).on("click", ".btnUpdate", function(event) 
{ 
    //get issue id from the data-issueid attribute in update button
    $("#hidIssueIDSave").val($(this).data('issueid')); 
    //get data from <td> element
    $("#issueCode").val($(this).closest("tr").find('td:eq(0)').text()); 
    $("#empAllocated").val($(this).closest("tr").find('td:eq(1)').text()); 
    $("#systemID").val($(this).closest("tr").find('td:eq(2)').text()); 
    $("#issueDesc").val($(this).closest("tr").find('td:eq(3)').text()); 
}); 

// DELETE
$(document).on("click",".btnRemove", function(event) {
    // ajax communication
    $.ajax({
        url: "IssuesAPI",
        type: "DELETE",
        data: "issueID=" + $(this).data("issueid"),
        dataType: "text",
        complete: function(response, status) {
            onIssueDeleteComplete(response.responseText, status);
        }
    });
});

// after completing delete request
function onIssueDeleteComplete(response, status) {

    if (status == "success") { //if the response status is success
        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() === "success") { //if the json status is success
            //display success alert
            $("#alertSuccess").text("Successfully deleted");
            $("#alertSuccess").show();
    
            //load data in json to html
            $("#divIssuesGrid").html(resultSet.data);

        } else if (resultSet.status.trim() === "error") { //if the json status is error
            //display error alert
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") { 
        //if the response status is error
        $("#alertError").text("Error while deleting");
        $("#alertError").show();
    } else { 
        //if an unknown error occurred
        $("#alertError").text("Unknown error occurred while deleting");
        $("#alertError").show();
    } 
}

// VALIDATION
function validateIssueForm() { 
    // CODE 
    if ($("#issueCode").val().trim() == "") 
    { 
        return "Insert Issue Code."; 
    } 
    
    // NAME 
    if ($("#empAllocated").val().trim() == "") 
    { 
        return "Insert Issue Name."; 
    } 
    
    // PRICE
    if ($("#systemID").val().trim() == "") 
    { 
        return "Insert Issue Price."; 
    } 
    
    // is numerical value 
    var tmpPrice = $("#systemID").val().trim(); 
    if (!$.isNumeric(tmpPrice)) 
    { 
        return "Insert a numerical value for Issue Price."; 
    } 
    
    // convert to decimal price 
    $("#systemID").val(parseFloat(tmpPrice).toFixed(2)); 
    
    // DESCRIPTION
    if ($("#issueDesc").val().trim() == "") 
    { 
        return "Insert Issue Description."; 
    } 
    
    return true; 
} 
 