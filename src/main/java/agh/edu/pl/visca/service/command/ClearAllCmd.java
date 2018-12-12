package agh.edu.pl.visca.service.command;

public final class ClearAllCmd implements Cmd {
    private static final byte[] clearAllCommandData = new byte[]{1, 0, 1};

    public ClearAllCmd() {
    }

    @Override
    public byte[] createCommandData() {
        return clearAllCommandData.clone();
    }

    @Override
    public byte[] createCommandData(Integer additional) {
        return createCommandData();
    }

}
