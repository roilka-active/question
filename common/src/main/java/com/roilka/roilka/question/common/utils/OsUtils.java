package com.roilka.roilka.question.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @ClassName OsUtils
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/1/6 14:52
 **/
@Slf4j
public class OsUtils {

    public static String getHostNameForLiunx(){
        try {
            return getInetAddress().getHostName();
        } catch (SocketException e) {
            log.error("获取本地地址失败", e);
            return " ";
        }
    }
    public static InetAddress getInetAddress() throws SocketException {
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ipHost = null;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ipHost = (InetAddress) addresses.nextElement();
                if (ipHost != null && ipHost instanceof Inet4Address) {
                    System.out.println("本机的HOSTIP = " + ipHost.getHostAddress());
                    System.out.println("本机的HOSTNAME = " + ipHost.getHostName());
                    return ipHost;
                }
            }
        }
        return ipHost;
    }
}
