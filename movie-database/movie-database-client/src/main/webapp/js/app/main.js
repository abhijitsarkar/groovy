require(["jquery", "dataTables"], function(jquery, dataTables) {

    var setUpDefaults = function() {
        jquery("#get").prop("checked", true);
        jquery("#url").attr("placeholder", "URL");
        jquery("#go").val("Search");
    };

    setUpDefaults();

    jquery(":radio").click(function() {
         var getRequest = jquery("#get").is(":checked");
         var goVal = getRequest ? "Search" : "Index";

         jquery("#go").val(goVal);
    });

    var displayResults = function(data) {
        return jquery('#searchResults').dataTable({
            "aaData": data,
            "aoColumns": [
                { "mData": "title" },
                { "mData": "genres[0]" },
                { "mData": "releaseDate" }
            ],
            "aoColumnDefs": [
                {
                    "aTargets": [2],
                    "mRender": function (obj) {
                        var jsDate = new Date(obj);
                        return jsDate.getFullYear();
                    }
                }
            ]
        });
    }

    jquery(":button").click(function() {
         var getRequest = jquery("#get").is(":checked");

         var type = getRequest ? "GET" : "POST";
         var url = getRequest ? jquery("#url").val() : "http://localhost:8080/movies";
         var data = getRequest ? {} : { "dir" : jquery("#url").val() };
         var contentType = getRequest ? "application/json; charset=UTF-8"
            : "application/x-www-form-urlencoded; charset=UTF-8";
         var dataType = "json";

         var success = function(data, textStatus, jqXHR) {
            console.log(jquery.makeArray(data));
            jquery(".searchResults").toggle();
            displayResults(data);
         };
         var error = function(jqXHR, textStatus, errorThrown) {
             alert(textStatus);
         };

         $.ajax({
            contentType: contentType,
            data: data,
            dataType: dataType,
            error: error,
            success: success,
            type: type,
            url: url
         });
    });
});