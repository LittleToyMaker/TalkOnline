var ACCOUNT_NAME = "accountName";
var PASSWORD = "password";
var ACCOUNT_ID = "accountId";
var HEADSHOT = "headshot";

function signInTips() {
    $("#text").val("signIn" + "\n" + ACCOUNT_NAME + ":\n" + PASSWORD + ":");
}

function signUpTips() {
    $("#text").val("signUp" + "\n" + ACCOUNT_NAME + ":\n" + PASSWORD + ":");
}

function confirm() {
    var text = $("#text").val();
    text = text.replace(/\r/g, "");
    var lines = text.split("\n");
    if(lines[0] == "signIn"){
        signIn(JSON.stringify(lines.slice(1)));
    }else if(lines[0] == "signUp"){
        signUp(JSON.stringify(lines.slice(1)));
    }else if(lines[0] == "edit"){
        edit(JSON.stringify(lines.slice(1)));
    }
}

function signIn(lines) {
    $.post("/account/signIn"
        ,{lines:lines}
        ,function(result) {
            var resJson = $.parseJSON(result);
            if(resJson.code == 0){
                location.href = "/html/home.html";
            }else {
                alert(resJson.msg);
            }
        }
    );
}

function signUp(lines) {
    $.post("/account/signUp"
        ,{lines:lines}
        ,function(result) {
            var resJson = $.parseJSON(result);
            if(resJson.code == 0){
                alert("注册成功");
            }else {
                alert(resJson.msg);
            }
        }
    );
}

function edit(lines) {
    $.post("/account/edit"
        ,{lines:lines}
        ,function(result) {
            var resJson = $.parseJSON(result);
            if(resJson.code == 0){
                alert("修改成功");
            }else {
                alert(resJson.msg);
            }
        }
    );
}