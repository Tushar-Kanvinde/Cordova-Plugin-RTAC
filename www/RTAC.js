var exec = require('cordova/exec');
var RTAC={
    openNavigation:function (fnSuccess, fnError,  latitude, longitude){
        exec(fnSuccess, fnError, "RTAC", "openNavigation", [latitude, longitude]);
    },
    checkOTPLPrinterPaper:function(fnSuccess, fnError){
        exec(fnSuccess, fnError, "RTAC", "checkOTPLPrinterPaper");
    },
    printOTPLImage:function(fnSuccess, fnError,imageString){
        exec(fnSuccess, fnError, "RTAC", "printBitmap",[imageString]);
    },
    enableScreenshot:function(fnSuccess, fnError,imageString){
        exec(fnSuccess, fnError, "RTAC", "enableScreenshot");
    },
    disableScreenshot:function(fnSuccess, fnError,imageString){
        exec(fnSuccess, fnError, "RTAC", "disableScreenshot");
    }
}
module.exports = RTAC;
