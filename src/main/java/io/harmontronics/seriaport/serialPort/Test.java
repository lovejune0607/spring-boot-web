package io.harmontronics.seriaport.serialPort;

import java.util.ArrayList;

import gnu.io.SerialPort;
import io.harmontronics.seriaport.serialException.NoSuchPort;
import io.harmontronics.seriaport.serialException.NotASerialPort;
import io.harmontronics.seriaport.serialException.PortInUse;
import io.harmontronics.seriaport.serialException.SendDataToSerialPortFailure;
import io.harmontronics.seriaport.serialException.SerialPortOutputStreamCloseFailure;
import io.harmontronics.seriaport.serialException.SerialPortParameterFailure;


public class Test {

    private static SerialPort port = null;

    public static void main(String[] args) {
        System.out.println("---------------------------------------------");

        SerialTool tool = SerialTool.getSerialTool();
        ArrayList ports = tool.findPort();

        String portName = null;
        for(int i=0;i<ports.size();i++) {
            portName = (String)ports.get(i);
            System.out.println(portName);
        }




        try {
            if(portName != null) {


                port = SerialTool.openPort(portName,9600);


                //5A0001010101E1  31 326A617661CAD6B6AFB2E2CAD4
                String head = "5A0001010101E13A000000000A00000B0000";
                String mess = "5A0001010101E13A000000000A00000B00006A617661CAD6B6AFB2E2CAD4000000000000000000000000000000000000000000000000000000000000000000000000";
                //3A 00 00 00 00 0A 00 00 0B 00 00 BC F2 CC E5 D6 D0 CE C4 45 6E 67 6C 69 73 68 31 32 33 34 35 36 37 38 39 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 FC";
                //String s = "5A 11 2C 90 02 32 E5 01 00 41";

                String message = "java第二次手动测试";
                String hexMessage = str2HexStr(message);


                int length = hexMessage.length()/2;


                for(int i=0;i<48-length;i++) {
                    hexMessage = hexMessage+"00";
                }
                String orderMessage = head + hexMessage ;
                System.out.println("============================== hexMessage = " + orderMessage);


                byte[] order = hexStrToBinaryStr(orderMessage);

                byte sum = 0;
                for(int i=0;i<order.length;i++) {
                    System.out.println("i=================" + i);
                    System.out.println(order[i]);
                    sum += order[i];
                    System.out.println("sum = " + sum);
                }
                int len = order.length;
                order[len-1] = sum;
                tool.sendToPort(port, order);

                tool.closePort(port);
            }
        } catch (SerialPortParameterFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotASerialPort e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPort e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PortInUse e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SendDataToSerialPortFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SerialPortOutputStreamCloseFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }


    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
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


        byte[] bytes = new byte[len / 2];

        while (index < len) {

            String sub = hexString.substring(index, index + 2);

            bytes[index/2] = (byte)Integer.parseInt(sub,16);

            index += 2;
        }


        return bytes;
    }

    private final static String[] hexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public static String byteToHex(int n) {
        if (n < 0) {
            n = n + 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexArray[d1] + hexArray[d2];
    }



}
