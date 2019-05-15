package io.harmontronics.seriaport.controller;


import gnu.io.SerialPort;
import io.harmontronics.seriaport.entity.ServerStatus;
import io.harmontronics.seriaport.serialException.*;
import io.harmontronics.seriaport.serialPort.SerialTool;
import io.harmontronics.seriaport.util.TimeSetUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * Create by zhou
 * Date: 2019/5/15
 * Time: 16:42
 */
@Controller
public class serverStatusController {
    @RequestMapping(value = "/serverStatus")
    @ResponseBody
    public Object getServerStatus(){
        ServerStatus status = new ServerStatus();
        SerialTool tool = SerialTool.getSerialTool();
        ArrayList ports = tool.findPort();


        String portName = null;
        for(Object port:ports){
            portName =  (String)port;
        }

        if(portName != null){
            SerialPort port = null;
            try {
                status.setPortName(portName);
                port = SerialTool.openPort(portName,9600);
                status.setCanOpen(true);
            } catch (SerialPortParameterFailure serialPortParameterFailure) {
                serialPortParameterFailure.printStackTrace();
                status.setCanOpen(false);
                status.setFailReason(serialPortParameterFailure.toString());
            } catch (NotASerialPort notASerialPort) {
                notASerialPort.printStackTrace();
                status.setCanOpen(false);
                status.setFailReason(notASerialPort.toString());
            } catch (NoSuchPort noSuchPort) {
                noSuchPort.printStackTrace();
                status.setCanOpen(false);
                status.setFailReason(noSuchPort.toString());
            } catch (PortInUse portInUse) {
                portInUse.printStackTrace();
                status.setCanOpen(false);
                status.setFailReason(portInUse.toString());
            }finally{
                tool.closePort(port);

            }
        }

        return status;
    }
}
