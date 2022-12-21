package com.polaroid.universalapp;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtil {

    private static final String TAG = AppUtil.class.getSimpleName();

    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = null;
        if (activityManager != null) {
            appProcesses = activityManager.getRunningAppProcesses();
        }
        if (appProcesses == null) {
            //App is closed
            return false;
        }
        final String packageName = "com.polaroid.universalapp";
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                //                Log.e("app",appPackageName);
                return true;
            } else {
                //App is closed
            }
        }
        return false;

    }

    //Is String Empty
    public static boolean isStringEmpty(String stringRes) {
        return stringRes == null || stringRes.trim().isEmpty();
    }

    public static String isStringNull(String stringRes) {
        if (stringRes == null || stringRes.trim().isEmpty()) {
            return "";
        }

        return stringRes;
    }

    public static boolean isIntegerNull(int value) {
        return value == -1;
    }

    public static boolean isStringTextOnly(String stringRes) {
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(stringRes);
        boolean bs = ms.matches();
        return bs == false;
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    //Check Collection for Empty
    public static boolean isCollectionEmpty(Collection<? extends Object> collection) {
        return collection == null || collection.isEmpty();
    }

    //Check Int Array for Empty
    public static boolean isIntArrayEmpty(int[] collection) {
        return collection == null || collection.length == 0;
    }

    //Set String Not NULL
    public static String setStringNotNull(String stringRes) {
        return ((stringRes == null) ? "" : stringRes.trim());
    }

    //Check for NULL - Integer
    public static Integer setIntegerNotNull(Integer value) {
        return ((value == null) ? 0 : value);
    }

    //Check for NULL - Long
    public static Long setLongNotNull(Long value) {
        return ((value == null) ? 0 : value);
    }




    public static boolean writeFileToStream(File file, OutputStream os) throws IOException {
        if (file == null) {
            return false;
        }
        try (InputStream is = new FileInputStream(file)) {
            byte[] buff = new byte[1024];
            int read = 0;
            while ((read = is.read(buff)) != -1) {
                os.write(buff, 0, read);
            }
        }
        return true;
    }



    public static Boolean isInternetAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            Boolean wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            Boolean mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {
                return true;
            } else if (mobileConnected) {
                return true;
            }
            return true;
        } else {
            return false;
        }
    }

    // returns unique id
    public static String getUniqueId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system

        return dateTime;
    }

    public static String getTimeStampWithSecond() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system

        return dateTime;
    }

    public static int getFileCount(String dirPath) {
        File[] files = new File(dirPath).listFiles();
        int count = 0;
        for (File f : files)
            if (f.isDirectory()) {
                count += getFileCount(f.toString());
            } else {
                String extension = f.toString().substring(f.toString().lastIndexOf("."));
                if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")
                        || extension.equalsIgnoreCase(".jpeg")) {
                    count++;
                }
            }

        return count;
    }

    //Change Date format
    public static String dateFormat(String selectDate, String selectDateFormat, String chnageDateFormat) {
        String dateString = selectDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat(selectDateFormat);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat(chnageDateFormat);
        String reportDate = df.format(convertedDate);

        return reportDate;
    }



    public static Bitmap getResizedBitmap(final Bitmap bm, final int newHeight, final int newWidth) {

       /* Bitmap bitmap = null;
        AsyncTask bitmpaAsycTask = new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... voids) {*/
        Bitmap resizedBitmap = null;
        try {
            int width = bm.getWidth();

            int height = bm.getHeight();

            float scaleWidth = ((float) newWidth) / width;

            float scaleHeight = ((float) newHeight) / height;

            // create a matrix for the manipulation
            Matrix matrix = new Matrix();
            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight);

            // recreate the new Bitmap
            resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        } catch (Exception e) {
            resizedBitmap = bm;
        }

        return resizedBitmap;
    }

    // return bitmap;

    //}
    //.execute();

       /* try {
            bitmap = (Bitmap) bitmpaAsycTask.get(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }*/

    // }


    public static File SaveImage(Bitmap finalBitmap, File path) {

        if (!path.exists()) {
            path.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(path);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String getBaseUrlFromUrl(String fileUrl) {
        String base = "";
        try {
            URL url = new URL(fileUrl);
            String path = url.getFile().substring(0, url.getFile().lastIndexOf('/'));
            base = url.getProtocol() + "://" + url.getHost() + path + "/";
        } catch (Exception e) {

        }
        return base;
    }

    public static String getFileNameFromUrl(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        return fileName;
    }


    public static View findOneVisibleChild(int fromIndex, RecyclerView.LayoutManager layoutManager, boolean completelyVisible,
                                           boolean acceptPartiallyVisible) {
        OrientationHelper helper;
        if (layoutManager.canScrollVertically()) {
            helper = OrientationHelper.createVerticalHelper(layoutManager);
        } else {
            helper = OrientationHelper.createHorizontalHelper(layoutManager);
        }

        final int start = helper.getStartAfterPadding();
        final int end = helper.getEndAfterPadding();
        final int next = layoutManager.getChildCount() > fromIndex ? 1 : -1;
        View partiallyVisible = null;
        for (int i = fromIndex; i != layoutManager.getChildCount(); i += next) {
            final View child = layoutManager.getChildAt(i);
            final int childStart = helper.getDecoratedStart(child);
            final int childEnd = helper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child;
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child;
                    }
                } else {
                    return child;
                }
            }
        }
        return partiallyVisible;
    }
}



