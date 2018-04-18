var ACCOUNT_NAME = "accountName";
var PASSWORD = "password";
var ACCOUNT_ID = "accountId";
var HEADSHOT = "headshot";

var accountJson = {};

window.onload = function () {
    
}

function sendMsg() {

}

function editTips() {
    $("#optText").val("edit" + "\n" + ACCOUNT_NAME + ":\n" + PASSWORD + ":");
}

function getCurrAccount() {
    $.get("/account/get"
        ,function(result) {
            var resJson = $.parseJSON(result);
            if(resJson.code == 0){
                accountJson = $.parseJSON(result);
            }else {
                alert(resJson.msg);
            }
        }
    );
}