cordova.define("cordova-plugin-rtac.RTAC", function(require, exports, module) {
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
    }
}
module.exports = RTAC;
});
