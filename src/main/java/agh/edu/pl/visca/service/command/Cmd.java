package agh.edu.pl.visca.service.command;

public interface Cmd {

    byte[] createCommandData();

    byte[] createCommandData(Integer additional);

}
