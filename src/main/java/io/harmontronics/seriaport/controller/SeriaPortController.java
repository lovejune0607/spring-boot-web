package io.harmontronics.seriaport.controller;

import io.harmontronics.seriaport.entity.WatchMessage;
import io.harmontronics.seriaport.repository.MessageRepository;
import io.harmontronics.seriaport.serialException.*;
import io.harmontronics.seriaport.serialPort.SerialTool;
import io.harmontronics.seriaport.util.ByteUtil;
import gnu.io.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
//@RestController
public class SeriaPortController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping( "/seria")
    @ResponseBody
    public String sendMessage(@RequestBody WatchMessage message){

        String content = message.getContent();
        String address = message.getAddress();


        SerialTool tool = SerialTool.getSerialTool();
        ArrayList ports = tool.findPort();

        WatchMessage watchMessage = new WatchMessage();
        watchMessage.setContent(content);
        watchMessage.setAddress(address);
        watchMessage.setStatus("1");
        watchMessage = messageRepository.save(watchMessage);


        String portName = null;
        for(int i=0;i<ports.size();i++) {
            portName = (String)ports.get(i);
        }

        if(portName != null){
            SerialPort port = null;
            try {
               port = SerialTool.openPort(portName,9600);

                byte[] order = ByteUtil.converStr2Byte(content,address);
                tool.sendToPort(port, order);
                //tool.closePort(port);

                watchMessage.setStatus("0");
                //messageRepository.save(watchMessage);
            } catch (SerialPortParameterFailure serialPortParameterFailure) {
                serialPortParameterFailure.printStackTrace();
                watchMessage.setStatus("1");
                watchMessage.setFailReason(serialPortParameterFailure.toString());
            } catch (NotASerialPort notASerialPort) {
                notASerialPort.printStackTrace();
                watchMessage.setStatus("1");
                watchMessage.setFailReason(notASerialPort.toString());
            } catch (NoSuchPort noSuchPort) {
                noSuchPort.printStackTrace();
                watchMessage.setStatus("1");
                watchMessage.setFailReason(noSuchPort.toString());
            } catch (PortInUse portInUse) {
                portInUse.printStackTrace();
                watchMessage.setStatus("1");
                watchMessage.setFailReason(portInUse.toString());
            } catch (SendDataToSerialPortFailure sendDataToSerialPortFailure) {
                sendDataToSerialPortFailure.printStackTrace();
                watchMessage.setStatus("1");
                watchMessage.setFailReason(sendDataToSerialPortFailure.toString());
            } catch (SerialPortOutputStreamCloseFailure serialPortOutputStreamCloseFailure) {
                serialPortOutputStreamCloseFailure.printStackTrace();
                watchMessage.setStatus("1");
                watchMessage.setFailReason(serialPortOutputStreamCloseFailure.toString());
            }finally{
                tool.closePort(port);

                messageRepository.save(watchMessage);
            }
        }else{
            watchMessage.setStatus("1");
            watchMessage.setFailReason("无可用的串口!");
        }

        return content;
    }
}
