package com.br.bookflix.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.br.bookflix.exception.BookflixException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomError {
	
	@JsonProperty(index = 0, value="error_message")
	@ApiModelProperty(value = "Error message", name = "error_message", dataType = "String", example = "Unexpected error")
	private String errorMessage;
	
	@JsonProperty(index = 1, value="error_details")
	@ApiModelProperty(value = "Error details", name = "error_details", dataType = "String", example = "Something unexpected happened while processing the request")
	private String errorDetails;
	
	@JsonProperty(index = 2)
	@ApiModelProperty(value = "Status code", name = "status", dataType = "long", example = "INTERNAL_SERVER_ERROR")
	private HttpStatus status;
	
	@JsonProperty(index = 3)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@ApiModelProperty(value = "Request date", name = "timestamp", dataType = "String", example = "2021-09-21T17:32:28Z")
    private LocalDateTime timestamp;
	
	public CustomError(final HttpStatus status, final Exception ex, final String error) {
        super();
        loadDetails(ex);
        this.status = status;
    }
	
    private void loadDetails(Exception ex) {
    	errorMessage = ex.getMessage();
    	
    	if(ex instanceof BookflixException) {
    		BookflixException be = (BookflixException) ex;
    		errorDetails = be.getDetails();
		}
    }
	
}