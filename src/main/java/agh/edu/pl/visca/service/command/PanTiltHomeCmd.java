package agh.edu.pl.visca.service.command;


public final class PanTiltHomeCmd implements Cmd {
    private static final byte[] ptHomeCommandData = new byte[]{1, 6, 4};

    public PanTiltHomeCmd() {
    }

    @Override
    public byte[] createCommandData() {
        return ptHomeCommandData.clone();
    }

    @Override
    public byte[] createCommandData(Integer additional) {
        return createCommandData();
    }

}
