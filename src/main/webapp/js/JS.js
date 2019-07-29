include("/jquery-ui-1.12.1js.js");
include("/jquery-1.12.4.js");

$(function () {
    $("#datepicker").datepicker({dateFormat: "dd.mm.yy"}).val()
});

$(function () {
    $("#datepicker2").datepicker({dateFormat: "dd.mm.yy"}).val()
});

