package agh.edu.pl.visca.service.command;


public final class PanTiltDownCmd implements Cmd {
    private static final byte[] ptUpCommandData = new byte[]{1, 6, 1, 0, 0, 3, 2};

    public PanTiltDownCmd() {
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
