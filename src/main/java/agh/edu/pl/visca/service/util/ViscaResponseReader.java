package agh.edu.pl.visca.service.util;

import agh.edu.pl.visca.exception.type.ViscaException;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class ViscaResponseReader {
    private static final long TIMEOUT_MS = 5000L;

    public static byte[] readResponse(SerialPort serialPort) throws SerialPortException, ViscaException {
        ArrayList<Byte> data = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        long timeDiff;
        do {
            while (serialPort.getInputBufferBytesCount() != 0) {
                byte[] responseData = serialPort.readBytes(1);
                byte b = responseData[0];
                data.add(b);
                if (b == -1) {
                    responseData = new byte[data.size()];
                    int idx = 0;

                    Byte b2;
                    for (Iterator var7 = data.iterator(); var7.hasNext(); responseData[idx++] = b2) {
                        b2 = (Byte) var7.next();
                    }

                    return responseData;
                }
            }

            long currentTime = System.currentTimeMillis();
            timeDiff = currentTime - startTime;
        } while (timeDiff <= TIMEOUT_MS);
        throw new ViscaException("Timeout Exception.");
    }

    public static String byteArrayToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }

        String response = sb.toString();
        if (Pattern.matches(".. 4. FF.*", response)) {
            return "ACK.";
        } else if (Pattern.matches(".. 5. FF.*", response)) {
            return "Command completion.";
        } else if (Pattern.matches(".. 50 .* FF.*", response)) {
            return "Information return.";
        } else if (Pattern.matches(".0 60 02 FF.*", response)) {
            return "Syntax exception.";
        } else if (Pattern.matches(".0 60 03 FF.*", response)) {
            return "Command buffer full.";
        } else if (Pattern.matches(".0 60 04 FF.*", response)) {
            return "Command cancel.";
        } else if (Pattern.matches(".0 60 05 FF.*", response)) {
            return "No sockets.";
        } else if (Pattern.matches(".0 60 41 FF.*", response)) {
            return "Command not executable.";
        } else if (Pattern.matches(".. 30 0. FF.*", response)) {
            return "Camera address assigned.";
        }

        return response;
    }

}
