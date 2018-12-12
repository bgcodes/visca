package agh.edu.pl.visca.controller;

import agh.edu.pl.visca.dto.input.CommandsDTO;
import agh.edu.pl.visca.dto.input.SingleCommandDTO;
import agh.edu.pl.visca.dto.output.ResponseMessageDTO;
import agh.edu.pl.visca.exception.type.ViscaException;
import agh.edu.pl.visca.service.ViscaService;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ViscaController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ViscaService viscaService;

    @Autowired
    public ViscaController(@Qualifier("viscaService") ViscaService viscaService) {
        this.viscaService = viscaService;
    }


    @RequestMapping(method = POST, path = "/app/visca/single")
    @ResponseBody
    public ResponseEntity<ResponseMessageDTO> executeSingleCommand(@RequestBody @Valid SingleCommandDTO singleCommandDTO) throws SerialPortException, ViscaException {
        log.info("received command '" + singleCommandDTO.getCommand() + "'");
        String response = singleCommandDTO.getCommand() + " => " + viscaService.executeCommand(singleCommandDTO.getCommand());
        return ResponseEntity.ok(new ResponseMessageDTO(response));
    }

    @RequestMapping(method = POST, path = "/app/visca")
    @ResponseBody
    public ResponseEntity<ResponseMessageDTO> executeCommands(@RequestBody @Valid CommandsDTO commandsDTO) {
        log.info("received commands " + commandsDTO.toString());

        List<String> responses = new ArrayList<>();

        for (SingleCommandDTO singleCommandDTO : commandsDTO.getCommands()) {
            String response = "...";
            try {
                response = viscaService.executeCommand(singleCommandDTO.getCommand());
            } catch (SerialPortException | ViscaException e) {
                response = e.getMessage();
            } finally {
                responses.add(singleCommandDTO.getCommand().toLowerCase() + " => " + response);

            }
        }

        String response = String.join("<>", responses);
        return ResponseEntity.ok(new ResponseMessageDTO(response));
    }

}
