package agh.edu.pl.visca.dto.output;

public class ErrorMessageDTO {

    private String errorMessage;


    public ErrorMessageDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}