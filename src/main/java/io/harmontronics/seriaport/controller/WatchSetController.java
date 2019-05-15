package io.harmontronics.seriaport.controller;


import io.harmontronics.seriaport.entity.WatchInfo;
import io.harmontronics.seriaport.repository.MessageRepository;
import io.harmontronics.seriaport.serialPort.SerialTool;
import io.harmontronics.seriaport.util.WatchSetUtil;
import gnu.io.SerialPort;
import io.harmontronics.seriaport.serialException.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * Create by zhou
 * Date: 2019/5/14
 * Time: 10:04
 */
@Controller
public class WatchSetController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping( "/watchSet")
    @ResponseBody
    public String watchSet(@RequestBody WatchInfo info){
        String watchAddress = info.getWatchAddress();
        String address = info.getAddress();

        SerialTool tool = SerialTool.getSerialTool();
        ArrayList ports = tool.findPort();

        String portName = null;
        for(int i=0;i<ports.size();i++) {
            portName = (String)ports.get(i);
        }

        if(portName != null){
            SerialPort port = null;
            try {
                port = SerialTool.openPort(portName,9600);

                byte[] order = WatchSetUtil.converStr2Byte(watchAddress,address);
                tool.sendToPort(port, order);



            } catch (SerialPortParameterFailure serialPortParameterFailure) {
                serialPortParameterFailure.printStackTrace();

            } catch (NotASerialPort notASerialPort) {
                notASerialPort.printStackTrace();

            } catch (NoSuchPort noSuchPort) {
                noSuchPort.printStackTrace();

            } catch (PortInUse portInUse) {
                portInUse.printStackTrace();

            } catch (SendDataToSerialPortFailure sendDataToSerialPortFailure) {
                sendDataToSerialPortFailure.printStackTrace();

            } catch (SerialPortOutputStreamCloseFailure serialPortOutputStreamCloseFailure) {
                serialPortOutputStreamCloseFailure.printStackTrace();

            }finally{
                tool.closePort(port);

            }
        }


        return info.getAddress();
    }
}
