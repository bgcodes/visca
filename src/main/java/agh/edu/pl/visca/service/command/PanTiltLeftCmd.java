package agh.edu.pl.visca.service.command;

public final class PanTiltLeftCmd implements Cmd {
    private static final byte[] ptLeftCommandData = new byte[]{1, 6, 1, 0, 0, 1, 3};

    public PanTiltLeftCmd() {
    }

    @Override
    public byte[] createCommandData() {
        byte[] cmdData = ptLeftCommandData.clone();
        cmdData[3] = 8;
        cmdData[4] = 1;
        return cmdData;
    }

    @Override
    public byte[] createCommandData(Integer additional) {
        if (additional != null) {
            byte[] cmdData = ptLeftCommandData.clone();
            cmdData[3] = additional.byteValue();
            cmdData[4] = 1;

            return cmdData;
        } else {
            return createCommandData();
        }
    }

}
