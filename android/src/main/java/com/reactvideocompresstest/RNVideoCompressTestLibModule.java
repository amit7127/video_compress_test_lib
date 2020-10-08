
package com.reactvideocompresstest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.iceteck.silicompressorr.CompressionException;
import com.iceteck.silicompressorr.SiliCompressor;
import com.iceteck.silicompressorr.VideoConversionProgressListener;

import java.io.File;
import java.util.Locale;

public class RNVideoCompressTestLibModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private Callback errorCallback;
    private Callback successCallback;

    public RNVideoCompressTestLibModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "CompressModule";
    }

    @ReactMethod
    public void compressVideo(String uriString, Callback errorCallback, Callback successCallback) {
        this.errorCallback = errorCallback;
        this.successCallback = successCallback;
        String inputText = "/storage/emulated/0/Download/20201003_095653.mp4";
        String output = inputText.replace(".mp4", "_Compressed.mp4");
        new VideoCompressAsyncTask(getReactApplicationContext(), 0.1F).execute(inputText, output);
    }

    class VideoCompressAsyncTask extends AsyncTask<String, Float, String> {

        Context mContext;
        Float amountToCompressToLocal;

        public VideoCompressAsyncTask(Context context) {
            mContext = context;
            this.amountToCompressToLocal = null;
        }

        public VideoCompressAsyncTask(Context context, float percentToCompressDownToLocal) {
            mContext = context;
            this.amountToCompressToLocal = percentToCompressDownToLocal;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            if (values == null) {
                return;
            }
            if (values.length <= 0) {
                return;
            }
            if (true) { //Flip to false to stop printing here
                try {
                    if (values != null) {
                        if (values[0] != null) {
                            float flt = ((values[0]) * 100);
                            Log.d("SelectPictureActivity", "Progress Complete: " + (flt) + "%");
//                            progressBar.setProgress((int) flt);
//                            progress_tv.setText("Progress: " + ((int) flt) + "%");
//                            progressCallback.invoke("Progress: " + ((int) flt) + "%");
//                            errorCallback.invoke("Progress: " + ((int) flt) + "%");
                            getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                    .emit("EVENT_TAG", ((int) flt));

                            if (flt > 40) {
                                if (false) { //Flip this to true to foce manual cancel when progress hits 40%
                                    Log.d("1", "Attempting manual cancel of progress conversion @ >= 40%");
                                    SiliCompressor.with(this.mContext).cancelVideoCompression();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
//                progressBar.setIndeterminate(true);
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... paths) {
            String filePath = null;
            Log.d("d", "Do in background, amount to compress local == " + amountToCompressToLocal);
            if (amountToCompressToLocal == null) {
                amountToCompressToLocal = 0.5F;
            }
            if (amountToCompressToLocal < 0.0 || amountToCompressToLocal > 1.0) {
                amountToCompressToLocal = 0.5F;
            }
            try {
                //Old Method
//                filePath = SiliCompressor.with(mContext).compressVideo(mContext, paths[0], paths[1]);

                //New Method
                try {
                    filePath = SiliCompressor.with(mContext, true).compressVideo(new VideoConversionProgressListener() {
                        @Override
                        public void videoConversionProgressed(float progressPercentage, Long estimatedNumberOfMillisecondsLeft) {
                            publishProgress(progressPercentage);
                        }
                    }, paths[0], paths[1], this.amountToCompressToLocal, 0.5F);
                } catch (CompressionException ce) {
                    ce.printStackTrace();
                    errorCallback.invoke(ce.getMessage());
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                errorCallback.invoke(e.getMessage());
            }
            return (filePath == null) ? "" : filePath;
        }


        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);
            try {
                File imageFile = new File(compressedFilePath);
                float length = imageFile.length() / 1024f; // Size in KB
                String value;
                if (length >= 1024)
                    value = length / 1024f + " MB";
                else
                    value = length + " KB";
                String text = String.format(Locale.US, "%s\nName: %s\nSize: %s", "Complete", imageFile.getName(), value);
                successCallback.invoke(imageFile.getName(), value, compressedFilePath, text);
                Log.i("Silicompressor", "Path: " + compressedFilePath);
            } catch (Exception e) {
                errorCallback.invoke(e.getMessage());
            }
        }
    }
}