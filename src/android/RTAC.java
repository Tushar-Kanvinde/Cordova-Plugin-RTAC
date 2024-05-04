package in.rtac.RTAC;

import android.content.Intent;
import android.net.Uri;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.rtac.otpl.otpl;

public class RTAC  extends CordovaPlugin {
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        switch(action){
            case "printBitmap":
                String imageString = args.getString(0);
                printOTPLBitmap(callbackContext, imageString);
                return true;
            case  "openNavigation":
                openNavigation(callbackContext, (float) args.getDouble(0), (float) args.getDouble(1));
                return true;
            case "checkOTPLPrinterPaper":
                checkOTPLPrinterPaper(callbackContext);
                return true;
            default:
                return false;
        }
    }
    void openNavigation(CallbackContext callbackContext, Float latitude, Float longitude) {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&mode=b"));
        this.cordova.getActivity().startActivity(intent);
        callbackContext.success("true");
    }
    void checkOTPLPrinterPaper(CallbackContext callbackContext) throws JSONException {
        otpl OTPL = new otpl(this.cordova.getActivity());
        JSONObject r = new JSONObject();
        r.put("hasPaper", OTPL.ifHavePaper());
        callbackContext.success(r.toString());
    }
    void printOTPLBitmap(CallbackContext callbackContext,String base64) throws JSONException {
        otpl OTPL = new otpl(this.cordova.getActivity());
        OTPL.printBitmap(base64);
        callbackContext.success();
        return;
    }
}
