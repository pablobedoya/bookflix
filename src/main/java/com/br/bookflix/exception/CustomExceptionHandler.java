package com.br.bookflix.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br.bookflix.error.CustomError;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		final CustomError error = new CustomError(HttpStatus.BAD_REQUEST, ex, errors.stream().collect(Collectors.joining(", ")));
		return handleExceptionInternal(ex, error, headers, error.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		
		final CustomError error = new CustomError(HttpStatus.BAD_REQUEST, ex, errors.stream().collect(Collectors.joining(", ")));
		return handleExceptionInternal(ex, error, headers, error.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		
		final String errorStr = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
		final CustomError error = new CustomError(HttpStatus.BAD_REQUEST, ex, errorStr);
		
		return new ResponseEntity<Object>(errorStr, new HttpHeaders(), error.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String errorStr = ex.getRequestPartName() + " part is missing";
		final CustomError error = new CustomError(HttpStatus.BAD_REQUEST, ex, errorStr);

		return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String errorStr = ex.getParameterName() + " parameter is missing";
		final CustomError error = new CustomError(HttpStatus.BAD_REQUEST, ex, errorStr);

		return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String errorStr = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		final CustomError error = new CustomError(HttpStatus.BAD_REQUEST, ex, errorStr);
		
		return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
	}

	// 404
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String errorStr = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		final CustomError error = new CustomError(HttpStatus.NOT_FOUND, ex, errorStr);
		
		return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
	}

	// 405
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		final CustomError error = new CustomError(HttpStatus.METHOD_NOT_ALLOWED, ex, builder.toString());
		return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
	}

	// 415
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		final CustomError error = new CustomError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex, builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
	}

	// 500
	@ExceptionHandler({ BookflixException.class, Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		logger.info(ex.getClass().getName());
		logger.error("error", ex);
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if(ex instanceof BookflixException) {
			status = ((BookflixException) ex).getStatus();
		}
		
		final CustomError error = new CustomError(status, ex, "error occurred");
		return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
	}

}
