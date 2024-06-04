package com.software.reportapp.phonestatus;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import androidx.lifecycle.ViewModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public int getRunningAppCount(Context context) {
        // UsageStatsManager 선언
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        // 얼마만큼의 시간동안 수집한 앱의 이름을 가져오는지 정하기 (begin ~ end 까지의 앱 이름을 수집한다)
        final long INTERVAL = 10000;
        final long end = System.currentTimeMillis();
        // 1 minute ago
        final long begin = end - INTERVAL;

        // 중복된 패키지 명을 제외하기 위하 Set에 담는다
        Set<String> packageNames = new HashSet<>();

        // 수집한 이벤트들을 담기 위한 UsageEvents
        final UsageEvents usageEvents = usageStatsManager.queryEvents(begin, end);

        // 이벤트가 여러개 있을 경우 반복하여 이벤트 추출
        while (usageEvents.hasNextEvent()) {
            // 현재 이벤트를 가져오기
            UsageEvents.Event event = new UsageEvents.Event();
            usageEvents.getNextEvent(event);

            // 현재 이벤트가 포그라운드 상태라면 = 현재 화면에 보이는 앱이라면
            if(isForeGroundEvent(event)) {
                // Set에 add
                packageNames.add(event.getPackageName());
            }
        }

        return packageNames.size();
    }

    private static boolean isForeGroundEvent(UsageEvents.Event event) {
        if(event == null) return false;

        if(Build.VERSION.SDK_INT >= 29)
            return event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED;

        return event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND;
    }


}
