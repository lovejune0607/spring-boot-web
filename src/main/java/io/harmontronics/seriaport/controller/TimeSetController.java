package io.harmontronics.seriaport.controller;

import gnu.io.SerialPort;
import io.harmontronics.seriaport.serialException.*;
import io.harmontronics.seriaport.serialPort.SerialTool;
import io.harmontronics.seriaport.util.TimeSetUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


/**
 * Create by zhou
 * Date: 2019/5/15
 * Time: 11:58
 */
@RestController
public class TimeSetController {

    @RequestMapping("/timeSet")
    public String timeSet(){
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

                byte[] order = TimeSetUtil.converStr2Byte();
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
        return "setSuccess";
    }

}
