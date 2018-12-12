package agh.edu.pl.visca.dto.input;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

public class CommandsDTO {

    @Valid
    private List<SingleCommandDTO> commands;


    public List<SingleCommandDTO> getCommands() {
        return commands;
    }

    public void setCommands(List<SingleCommandDTO> commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "[" + commands.stream().map(c -> "'" + c.getCommand() + "'").collect(Collectors.joining(",")) + "]";
    }

}
