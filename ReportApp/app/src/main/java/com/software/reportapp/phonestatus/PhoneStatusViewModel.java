package com.software.reportapp.phonestatus;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.LongSparseArray;

import androidx.lifecycle.ViewModel;

import java.util.List;

public class PhoneStatusViewModel extends ViewModel {

    public String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    public String getWIFIStatus(Context context) {
        @SuppressLint("ServiceCast")
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // WIFI 연결 상태 확인
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiOn = networkInfo.isConnected();

        return isWifiOn ? "ON" : "OFF";
    }

    public String getLTEStatus(Context context) {
        @SuppressLint("ServiceCast")
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // WIFI 연결 상태 확인
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isLteOn = networkInfo.isConnected();

        return isLteOn ? "ON" : "OFF";
    }

    public String getSCCardInfo() {
        String sdcard = Environment.getExternalStorageDirectory().getPath();
        StatFs stat = new StatFs(sdcard);

        // 블록 크기 및 블록 수를 가져옵니다.
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        long availableBlocks = stat.getAvailableBlocksLong();

        // 전체 용량과 사용 가능한 용량을 계산합니다.
        long totalSize = totalBlocks * blockSize;
        long availableSize = availableBlocks * blockSize;

        return formatSize(totalSize)+"/"+ formatSize(availableSize);
    }

    // 용량을 읽기 쉬운 형태로 변환하는 메서드
    private String formatSize(long size) {
        String suffix = null;
        float fSize = size;

        if (fSize >= 1024) {
            suffix = "KB";
            fSize /= 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
                if (fSize >= 1024) {
                    suffix = "GB";
                    fSize /= 1024;
                }
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Float.toString(fSize));

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    public int getInstalledAppsCount(Context context) {
        PackageManager pm = context.getPackageManager();
        @SuppressLint("QueryPermissionsNeeded")
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        return apps.size();  // 설치된 앱의 수 반환
    }

    public int getRunningServicesCount(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Integer.MAX_VALUE);
        return services.size();  // 실행 중인 서비스의 수 반환
    }

}
