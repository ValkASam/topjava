$(document).ready(function () {
        var jsAtt = $("#error").html();
        if (jsAtt) failNoty(jsAtt);
    }
);

function failNoty(text) {
    noty({
        text: text,
        type: 'error',
        layout: 'bottomRight',
        timeout: false
    });
}


