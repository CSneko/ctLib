package org.cneko.ctlib.common.network;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class PingTest {
    private String url;
    private long totalDelay;
    private int pingCount;

    public PingTest(String url) {
        this.url = url;
        this.totalDelay = 0;
        this.pingCount = 0;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void ping(int times) {
        for (int i = 0; i < times; i++) {
            long start = System.currentTimeMillis();
            try {
                URL obj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    long delay = System.currentTimeMillis() - start;
                    totalDelay += delay;
                    pingCount++;
                }
            } catch (IOException e) {
                // 发生异常时不做任何操作，跳过本次 ping 测试
            }
        }
    }

    public void ping() {
        ping(1); // 默认只 ping 一次
    }
    // 获取延迟的平均值
    public double getAverageDelay() {
        if (pingCount <= 0) {
            return 9999;
        }
        return (double) totalDelay / pingCount;
    }

    public static double simplePing(String url){
        PingTest pingTest = new PingTest(url);
        pingTest.ping();
        return pingTest.getAverageDelay();
    }
}
