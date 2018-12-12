package agh.edu.pl.visca.dto.input;

import javax.validation.constraints.NotEmpty;

public class SingleCommandDTO {

    @NotEmpty
    private String command;


    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }


}
