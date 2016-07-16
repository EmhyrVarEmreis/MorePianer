if (CONST_DEV) {
    var systemService = {
        exit: function() {
            console.log("systemService: exit");
        }
    };
} else {
    alert("__BACKEND__systemService");
}