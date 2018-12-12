package agh.edu.pl.visca.service.command;

public final class ZoomWideStdCmd implements Cmd {
    private static final byte[] ptWideStdCommandData = new byte[]{1, 4, 7, 3};

    public ZoomWideStdCmd() {
    }

    @Override
    public byte[] createCommandData() {
        return ptWideStdCommandData.clone();
    }

    @Override
    public byte[] createCommandData(Integer additional) {
        return createCommandData();
    }

}
