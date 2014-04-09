require(["jquery", "dataTables"], function(jquery, dataTables) {

    var setUpDefaults = function() {
        jquery("#get").prop("checked", true);
        jquery(".url").attr("placeholder", "URL");
        jquery(".go").val("Search");
    };

    setUpDefaults();

    jquery(":radio").click(function() {
         var getRequest = jquery("#get").is(":checked");
         var goVal = getRequest ? "Search" : "Index";

         jquery(".go").val(goVal);
    });

    var createNewTable = function(data) {
        var formatGenres = function(genres, type, full) {
            return genres.join(",");
        };

        var formatReleaseDate = function(releaseDate, type, full) {
            var jsDate = new Date(releaseDate);

            return jsDate.getFullYear();
        };

        return jquery('#searchResults').dataTable({
            "aaData": data,
            "aoColumns": [
                { "mData": "title" },
                { "mData": "genres" },
                { "mData": "releaseDate" }
            ],
            "aoColumnDefs": [
                {
                    "aTargets": [1],
                    "mRender": formatGenres
                },
                {
                    "aTargets": [2],
                    "mRender": formatReleaseDate
                }
            ],
            "bDestroy": true
        });
    };

    var displayResults = function(data) {
        return createNewTable(data);
    };

    jquery("form").submit(function(event) {
        var getRequest = jquery("#get").is(":checked");

        var type = getRequest ? "GET" : "POST";
        var url = getRequest ? jquery(".url").val() : "http://localhost:8080/movies";
        var data = getRequest ? {} : { "dir" : jquery(".url").val() };
        var contentType = getRequest ? "application/json; charset=UTF-8"
        : "application/x-www-form-urlencoded; charset=UTF-8";
        var dataType = "json";

        var success = function(data, textStatus, jqXHR) {
            jquery(".searchResults").show();

            displayResults(data);
        };
        var error = function(jqXHR, textStatus, errorThrown) {
            jquery(".searchResults").hide();
            alert(textStatus);
        };

        jquery.ajax({
            contentType: contentType,
            data: data,
            dataType: dataType,
            error: error,
            success: success,
            type: type,
            url: url
        });

        return false;
    });
});