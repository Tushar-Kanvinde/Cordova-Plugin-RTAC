package in.rtac.RTACOTPL;

import android.content.Intent;
import android.net.Uri;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.rtac.otpl.otpl;
import android.app.Activity;
import android.view.WindowManager;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class RTAC  extends CordovaPlugin {
    private in.rtac.RTACOTPL.RTAC mContext;
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        switch(action){
            case "printBitmap":
                String imageString = args.getString(0);
                printOTPLBitmap(callbackContext, imageString);
                return true;
            case  "openNavigation":
                openNavigation(callbackContext, args.getDouble(0), args.getDouble(1));
                return true;
            case "checkOTPLPrinterPaper":
                checkOTPLPrinterPaper(callbackContext);
                return true;
            case "enableScreenshot":
                enableScreenshot(callbackContext);
                return true;
            case "disableScreenshot":
                disableScreenshot(callbackContext);
                return true;
            default:
                return false;
        }
    }
    void enableScreenshot(CallbackContext callbackContext){
        mContext=this;
        this.cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try{
                    // Allow to make screenshots removing the FLAG_SECURE
                    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                        mContext.cordova.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                    }
                    callbackContext.success("Success");
                }catch(Exception e){
                    callbackContext.error(e.toString());
                }
            }
        });
    }
    void disableScreenshot(CallbackContext callbackContext){
        mContext=this;
        this.cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try{
                    // Allow to make screenshots removing the FLAG_SECURE
                    // Disable the creation of screenshots adding the FLAG_SECURE to the window
                    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                        mContext.cordova.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                                WindowManager.LayoutParams.FLAG_SECURE);
                    }
                    callbackContext.success("Success");
                }catch(Exception e){
                    callbackContext.error(e.toString());
                }
            }
        });
    }
    void openNavigation(CallbackContext callbackContext, Double latitude, Double longitude) {

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
