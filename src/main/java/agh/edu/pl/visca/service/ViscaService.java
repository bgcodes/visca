package agh.edu.pl.visca.service;

import agh.edu.pl.visca.exception.type.ViscaException;
import agh.edu.pl.visca.service.command.AddressCmd;
import agh.edu.pl.visca.service.command.ClearAllCmd;
import agh.edu.pl.visca.service.command.Cmd;
import agh.edu.pl.visca.service.command.PanTiltDownCmd;
import agh.edu.pl.visca.service.command.PanTiltHomeCmd;
import agh.edu.pl.visca.service.command.PanTiltLeftCmd;
import agh.edu.pl.visca.service.command.PanTiltRightCmd;
import agh.edu.pl.visca.service.command.PanTiltUpCmd;
import agh.edu.pl.visca.service.command.ViscaCommand;
import agh.edu.pl.visca.service.command.ZoomTeleStdCmd;
import agh.edu.pl.visca.service.command.ZoomWideStdCmd;
import agh.edu.pl.visca.service.util.ViscaResponseReader;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service("viscaService")
public class ViscaService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private SerialPort serialPort;

    @PostConstruct
    private void init() {
        serialPort = new SerialPort("/dev/COM1");
        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
        } catch (SerialPortException e) {
            log.error(e.getMessage());
//            e.printStackTrace();
        }
        log.info("ViscaService successfully initialized!");
    }

    public String executeCommand(String command) throws SerialPortException, ViscaException {
        command = command.toLowerCase();

        if (command.startsWith("wait") && command.endsWith("s")) {
            Integer sec = parseIntegerArgument(command);
            if (sec != null) {
                try {
                    TimeUnit.SECONDS.sleep(sec);
                    return "Wait end.";
                } catch (InterruptedException e) {
                    throw new ViscaException("InterruptedException");
                }
            }
        }

        Integer additionalParameter = null;
        String[] commandSegments = command.split("\\s+");
        if (commandSegments.length > 2) {
            additionalParameter = parseIntegerArgument(commandSegments[2]);
        }

        Cmd cmd;
        switch (commandSegments[0]) {
            case "address":
                cmd = new AddressCmd();
                break;
            case "clear":
                cmd = new ClearAllCmd();
                break;
            case "down":
                cmd = new PanTiltDownCmd();
                break;
            case "home":
                cmd = new PanTiltHomeCmd();
                break;
            case "left":
                cmd = new PanTiltLeftCmd();
                break;
            case "right":
                cmd = new PanTiltRightCmd();
                break;
            case "up":
                cmd = new PanTiltUpCmd();
                break;
            case "zoomin":
                cmd = new ZoomTeleStdCmd();
                break;
            case "zoomout":
                cmd = new ZoomWideStdCmd();
                break;
            default:
                throw new ViscaException('\'' + commandSegments[0] + "' unknown command.");
        }

        byte[] cmdData = cmd.createCommandData(additionalParameter);


        if (commandSegments.length == 1) {
            throw new ViscaException("Address is missing.");
        }
        byte destinationAdr = Byte.parseByte(commandSegments[1]);

        sendCommand(cmdData, destinationAdr);
        return readResponse();
    }

    private Integer parseIntegerArgument(String command) {
        String value = command.replaceAll("[^\\d]+", "");
        if (!value.isEmpty()) {
            return new Integer(value);
        }
        return null;
    }

    private void sendCommand(byte[] cmdData, byte destinationAdr) throws SerialPortException {
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = destinationAdr;
        cmdData = vCmd.getCommandData();
        log.info("sending: '" + ViscaResponseReader.byteArrayToString(cmdData) + "' to: '" + destinationAdr + "'");
        serialPort.writeBytes(cmdData);
    }

    private String readResponse() throws SerialPortException, ViscaException {
        byte[] responseInBytes = ViscaResponseReader.readResponse(serialPort);
        String response = ViscaResponseReader.byteArrayToString(responseInBytes);
        log.info("received: '" + response + "'");
        return response;
    }

}
