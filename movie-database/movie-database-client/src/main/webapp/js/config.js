requirejs.config({
    "paths": {
        "jquery": "//code.jquery.com/jquery-2.1.0.min",
        "dataTables": "./lib/jquery-dataTables-1.9.4.min",
        "lib": "./lib",
        "app": "./app"
    }
});

requirejs(["app/main"]);