package agh.edu.pl.visca.service.command;

public final class ZoomTeleStdCmd implements Cmd {
    private static final byte[] ptTeleStdCommandData = new byte[]{1, 4, 7, 2};

    public ZoomTeleStdCmd() {
    }

    @Override
    public byte[] createCommandData() {
        return ptTeleStdCommandData.clone();
    }

    @Override
    public byte[] createCommandData(Integer additional) {
        return createCommandData();
    }

}
