package com.cjhsoft.tarefa.api.exceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cjhsoft.tarefa.domain.exception.BusinessException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	public ApiExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	private final MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ProblemDetail problemDetail = ProblemDetail.forStatus(status);		
		problemDetail.setTitle("Um ou mais campos estão invalidos !!!");		
		problemDetail.setType(URI.create("https://localhost/erros/campo-chave-ou-valor-invalido"));	

		var fields =  ex.getBindingResult().getAllErrors().stream()
				.collect(Collectors.toMap(error -> ((FieldError) error).getField(),
						error -> messageSource.getMessage(error, LocaleContextHolder.getLocale())));
		
		problemDetail.setProperty("fields", fields);					
				
		return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ProblemDetail capturaExcessao(BusinessException e){
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);		
		problemDetail.setTitle(e.getMessage());						
		problemDetail.setType(URI.create("https://localhost/erros/erro-de-regra-de-negocio"));	
		
		return problemDetail;	
	}
	
}
