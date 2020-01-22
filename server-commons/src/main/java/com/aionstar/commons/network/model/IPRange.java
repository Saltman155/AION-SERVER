package com.aionstar.commons.network.model;

import java.util.Arrays;

/**
 * 玩家允许登录的IP区间
 * @author saltman155
 * @date 2020/1/18 20:22
 */

public class IPRange {

    private final long min;

    private final long max;

    private final byte[] address;

    public IPRange(String min, String max, String address) {
        this.min = toLong(toByteArray(min));
        this.max = toLong(toByteArray(max));
        this.address = toByteArray(address);
    }

    public IPRange(byte[] min, byte[] max, byte[] address) {
        this.min = toLong(min);
        this.max = toLong(max);
        this.address = address;
    }

    public boolean isInRange(String address) {
        long addr = toLong(toByteArray(address));
        return addr >= min && addr <= max;
    }

    public byte[] getAddress() {
        return address;
    }

    public byte[] getMinAsByteArray() {
        return toBytes(min);
    }

    public byte[] getMaxAsByteArray() {
        return toBytes(max);
    }

    private static long toLong(byte[] bytes) {
        long result = 0;
        result += (bytes[3] & 0xFF);
        result += ((bytes[2] & 0xFF) << 8);
        result += ((bytes[1] & 0xFF) << 16);
        result += (bytes[0] << 24);
        return result & 0xFFFFFFFFL;
    }

    private static byte[] toBytes(long val) {
        byte[] result = new byte[4];
        result[3] = (byte) (val & 0xFF);
        result[2] = (byte) ((val >> 8) & 0xFF);
        result[1] = (byte) ((val >> 16) & 0xFF);
        result[0] = (byte) ((val >> 24) & 0xFF);
        return result;
    }

    public static byte[] toByteArray(String address) {
        byte[] result = new byte[4];
        String[] strings = address.split("\\.");
        for (int i = 0, n = strings.length; i < n; i++) {
            result[i] = (byte) Integer.parseInt(strings[i]);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof IPRange))
            return false;
        IPRange ipRange = (IPRange) o;
        return max == ipRange.max && min == ipRange.min && Arrays.equals(address, ipRange.address);
    }

    @Override
    public int hashCode() {
        int result = (int) (min ^ (min >>> 32));
        result = 31 * result + (int) (max ^ (max >>> 32));
        result = 31 * result + Arrays.hashCode(address);
        return result;
    }

}
