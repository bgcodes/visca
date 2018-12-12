package agh.edu.pl.visca.service.command;

public final class PanTiltRightCmd implements Cmd {
    private static final byte[] ptRightCommandData = new byte[]{1, 6, 1, 0, 0, 2, 3};

    public PanTiltRightCmd() {
    }

    @Override
    public byte[] createCommandData() {
        byte[] cmdData = ptRightCommandData.clone();
        cmdData[3] = 4;
        cmdData[4] = 1;
        return cmdData;
    }

    @Override
    public byte[] createCommandData(Integer additional) {
        if (additional != null) {
            byte[] cmdData = ptRightCommandData.clone();
            cmdData[3] = additional.byteValue();
            cmdData[4] = 1;
            return cmdData;
        } else {
            return createCommandData();
        }
    }

}
