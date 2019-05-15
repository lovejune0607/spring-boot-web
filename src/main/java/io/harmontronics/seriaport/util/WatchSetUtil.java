package io.harmontronics.seriaport.util;

import java.io.UnsupportedEncodingException;

/**
 * Create by zhou
 * Date: 2019/5/14
 * Time: 16:09
 */
public class WatchSetUtil {

    //协议包头
    //5A 03 7D 77 C7 B9 E3 06 01 02 03 04 00 0A CE
    private final static String  head ="5A03";
    private final static String  order ="E306";

    private final static String[] hexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};


    //将消息转成符合协议的byte数组
    public static byte[] converStr2Byte(String watchAddress,String address){

        String address1 = address.substring(0,3);
        String address2 = address.substring(3,6);
        String address3 = address.substring(6,9);
        String address4 = address.substring(9,12);

        String hexAddress1 = intToHex(Integer.parseInt(address1));
        String hexAddress2 = intToHex(Integer.parseInt(address2));
        String hexAddress3 = intToHex(Integer.parseInt(address3));
        String hexAddress4 = intToHex(Integer.parseInt(address4));

        String watchAddress1 = watchAddress.substring(0,3);
        String watchAddress2 = watchAddress.substring(3,6);
        String watchAddress3 = watchAddress.substring(6,9);
        String watchAddress4 = watchAddress.substring(9,12);

        String hexWatchAddress1 = intToHex(Integer.parseInt(watchAddress1));
        String hexWatchAddress2 = intToHex(Integer.parseInt(watchAddress2));
        String hexWatchAddress3 = intToHex(Integer.parseInt(watchAddress3));
        String hexWatchAddress4 = intToHex(Integer.parseInt(watchAddress4));

        byte[] addressByte = hexStrToBinaryStr( hexAddress1+hexAddress2+hexAddress3+hexAddress4);
        byte sum = 0;
        for(int i =0;i<addressByte.length;i++){
            System.out.println(addressByte[i]);
            sum += addressByte[i];
        }


        String hexSum = toHex(sum);
        String orderMessage = head + hexWatchAddress1+hexWatchAddress2+hexWatchAddress3+hexWatchAddress4 + order + hexAddress1+hexAddress2+hexAddress3+hexAddress4 +"00" + hexSum;


        //String orderMessage = head +address + content + hexMessage ;

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
        String watchAddress = "125119199185";
        String address ="001002003004";


        byte[] order = WatchSetUtil.converStr2Byte(watchAddress,address);
        for(int i = 0;i<order.length;i++){
            System.out.println("---------------------------- order[i] " + order[i]);
        }

    }


}
