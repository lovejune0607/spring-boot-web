package com.zhou.controller;

import com.zhou.entity.Message;
import com.zhou.repository.MessageRepository;
import com.zhou.serialException.*;
import com.zhou.serialPort.SerialTool;
import com.zhou.util.ByteUtil;
import gnu.io.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

//@Controller
@RestController
public class SeriaPortController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping( "/seria")
    //@ResponseBody
    public String sendMessage( String message){
        System.out.println("----------------- from server message= " + message);
        SerialTool tool = SerialTool.getSerialTool();
        ArrayList ports = tool.findPort();

        String portName = null;
        for(int i=0;i<ports.size();i++) {
            portName = (String)ports.get(i);
        }

        if(portName != null){
            try {
                SerialPort port = SerialTool.openPort(portName,9600);
                byte[] order = ByteUtil.converStr2Byte(message);
                tool.sendToPort(port, order);
                tool.closePort(port);

                Message msg = new Message();
                msg.setContent(message);
                msg.setAdress("00000000");
                messageRepository.save(msg);
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
            }
        }

        return message;
    }
}
