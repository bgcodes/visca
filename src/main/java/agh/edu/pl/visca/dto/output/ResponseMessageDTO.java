package agh.edu.pl.visca.dto.output;

public class ResponseMessageDTO {

    private String responseMessage;


    public ResponseMessageDTO(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

}
