package agh.edu.pl.visca.service.command;

public final class AddressCmd implements Cmd {
    private static final byte[] adrCommmandData = new byte[]{48, 1};

    public AddressCmd() {
    }

    @Override
    public byte[] createCommandData() {
        return adrCommmandData.clone();
    }

    @Override
    public byte[] createCommandData(Integer additional) {
        return createCommandData();
    }

}
