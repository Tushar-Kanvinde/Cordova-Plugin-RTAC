package in.rtac.otpl;

import com.pos.sdk.printer.POIPrinterManager;
import com.pos.sdk.printer.PosPrinter;
import com.pos.sdk.printer.PosPrinterInfo;
import com.pos.sdk.printer.models.BitmapPrintLine;
import com.pos.sdk.printer.models.PrintLine;
import com.pos.sdk.printer.models.TextPrintLine;
import com.pos.sdk.printer.POIPrinterManager;
import com.pos.sdk.printer.PosPrinter;
import com.pos.sdk.printer.PosPrinterInfo;
import com.pos.sdk.printer.models.BitmapPrintLine;
import com.pos.sdk.printer.models.PrintLine;
import com.pos.sdk.printer.models.TextPrintLine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import android.util.Log;
public class otpl{
    private Activity mactivity=null;
    POIPrinterManager printerManage;

    public otpl(Activity callingA){
        mactivity=callingA;
        printerManage = new POIPrinterManager(mactivity);
    }
    public boolean ifHavePaper() {
        PosPrinterInfo info = new PosPrinterInfo();
        PosPrinter.getPrinterInfo(0, info);
        if (info.mHavePaper > 0) {
            return true;
        } else {
            return false;
        }
    }
    public void printBitmap(String imageString) {
        printerManage.open();
        printerManage.setPrintGray(2000);
        printerManage.setLineSpace(2);

        //printerManage.setPrintFont(String.valueOf(new File(Environment.getExternalStorageDirectory()+"fonts/Mogra-Regular.ttf")));
        printerManage.cleanCache();
        BitmapPrintLine bitmapPrintLine = new BitmapPrintLine();
        bitmapPrintLine.setType(PrintLine.BITMAP);
        bitmapPrintLine.setPosition(PrintLine.CENTER);


        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);

/*
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(mactivity.getExternalFilesDir(null), "test.png");
            Log.e("File",file.toString());
            try {
                if(!file.exists()) {
                    file.createNewFile();
                    Log.e("File","File created");
                }else{
                    Log.e("File","File already exists");
                    if(file.delete()) {//deleted successfully
                        Log.e("File","File deleted");
                        file.createNewFile();
                        Log.e("File","File created");
                    }else {//couldnt delete
                        Log.e("File","File could not be deleted");
                    }
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(imageBytes);
                fos.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
        }
*/
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        if (bitmap==null){
            Log.e("Bitmap","Bitmap is null");
        }else {
            bitmapPrintLine.setBitmap(bitmap);
            printerManage.addPrintLine(bitmapPrintLine);
            printerManage.beginPrint(new POIPrinterManager.IPrinterListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {
                    printerManage.close();
                }

                @Override
                public void onError(int i, String s) {
                    printerManage.close();
                }
            });
        }
    }

}
