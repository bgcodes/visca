package agh.edu.pl.visca.service.command;

public final class PanTiltUpCmd implements Cmd {
    private static final byte[] ptUpCommandData = new byte[]{1, 6, 1, 0, 0, 3, 1};

    public PanTiltUpCmd() {
    }

    @Override
    public byte[] createCommandData() {
        byte[] cmdData = ptUpCommandData.clone();
        cmdData[3] = 1;
        cmdData[4] = 2;
        return cmdData;
    }

    @Override
    public byte[] createCommandData(Integer additional) {
        return createCommandData();
    }

}
