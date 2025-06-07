package br.edu.utfpr.exemplo.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    private String codeDescription;
    private String message;

    public String getCodeDescription() {
        return codeDescription;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
