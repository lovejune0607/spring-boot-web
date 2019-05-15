package io.harmontronics.seriaport.util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * Create by zhou
 * Date: 2019/5/15
 * Time: 11:46
 */
public class TimeSetUtil {

    //协议包头
    //5A 00 FF FF FF FF E4 08 12 04 0D 0E 1C 0D 04 5E FE
    private final static String  head ="5A00FFFFFFFFE409";
    //private final static String  order ="E306";



    //将消息转成符合协议的byte数组
    public static byte[] converStr2Byte(){

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);


        String strYear = String.valueOf(year);
        String hi = strYear.substring(0,2);
        String low = strYear.substring(2,4);

        String hexHi = intToHex(Integer.parseInt(hi));
        String hexLow = intToHex(Integer.parseInt(low));
        String hexMonth = intToHex(month);
        String hexDay = intToHex(day);
        String hexHour = intToHex(hour);
        String hexMinute = intToHex(minute);
        String hexSecond = intToHex(second);
        String hexDayOfWeek = intToHex(dayOfWeek);

       String hexTime = hexHi+hexLow+hexMonth+hexDay+hexHour+hexMinute+hexSecond+hexDayOfWeek;
        byte[] timeBytes = hexStrToBinaryStr( hexTime);
        byte sum = 0;
        for(int i =0;i<timeBytes.length;i++){
            System.out.println(timeBytes[i]);
            sum += timeBytes[i];
        }


        String hexSum = toHex(sum);
        String orderMessage = head + hexTime + hexSum;

        //将拼接成的16进制字符串转成btye数组
        byte[] order = hexStrToBinaryStr(orderMessage);


        //根据具体内容计算校验码
        sum = 0;
        for(int i=0;i<order.length;i++) {
            sum += order[i];
        }

        int len = order.length;
        order[len-1]=sum;

        return order;
    }


    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    public static String str2HexStr(String str)  {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = new byte[0];
        try {
            //转为GBK
            bs = str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }



    /**
     * 将十六进制的字符串转换成字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStrToBinaryStr(String hexString) {


        if(hexString == null) {
            return null;
        }

        hexString = hexString.replaceAll(" ", "");

        int len = hexString.length();
        int index = 0;

        byte[] bytes = new byte[len/2 + 1];

        while (index < len) {
            String sub = hexString.substring(index, index + 2);
            bytes[index/2] = (byte)Integer.parseInt(sub,16);
            index += 2;
        }


        return bytes;
    }

    public static String intToHex(int n) {
        //StringBuffer s = new StringBuffer();
        if(n == 0){
            return "00";
        }
        StringBuilder sb = new StringBuilder(8);
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            sb = sb.append(b[n%16]);
            n = n/16;
        }
        a = sb.reverse().toString();
        if(a.length()==1){
            a="0"+a;
        }
        return a;
    }

    public static String toHex(byte b) {
        String result = Integer.toHexString(b & 0xFF);
        if (result.length() == 1) {
            result = '0' + result;
        }
        return result;
    }

    public static void main(String[] args){
        Calendar cal = Calendar.getInstance();
        System.out.println("========================" + cal.get(Calendar.YEAR));


        byte[] order = converStr2Byte();
        for(int i = 0;i<order.length;i++){
            System.out.println("---------------------------- order[i] " + order[i]);
        }

    }
}
