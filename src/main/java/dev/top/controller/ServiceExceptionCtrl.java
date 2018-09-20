package dev.top.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

import dev.top.controller.views.ErrorCode;
import dev.top.controller.views.ErrorView;
import dev.top.exceptions.CollegueIntrouvableViaMatriculeException;
import dev.top.exceptions.MatriculeExistantException;
import dev.top.exceptions.PseudoInvalideException;
import dev.top.exceptions.ServiceException;

@ControllerAdvice
public class ServiceExceptionCtrl {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<?> serviceException() {
		return ResponseEntity.badRequest().body(new ErrorView(ErrorCode.SERVICE, "Erreur côté service"));
	}

	@ExceptionHandler(PseudoInvalideException.class)
	public ResponseEntity<?> pseudoException() {
		return ResponseEntity.badRequest()
				.body(new ErrorView(ErrorCode.PSEUDO_INVALID, "Le pseudo n'a pas été trouvé en base de données"));
	}

	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<?> invalidFormatException() {
		return ResponseEntity.badRequest().body(new ErrorView(ErrorCode.JSON_PARSE,
				"Erreur dans la conversion Java <> JSON (vérifier vos paramètres d'entrée)"));
	}

	@ExceptionHandler(CollegueIntrouvableViaMatriculeException.class)
	public ResponseEntity<?> collegueIntrouvable() {
		return ResponseEntity.badRequest()
				.body(new ErrorView(ErrorCode.COLLEGUE_INTROUVABLE, "Collegue non trouvé via matricule"));
	}

	@ExceptionHandler(MatriculeExistantException.class)
	public ResponseEntity<?> matriculeExistant() {
		return ResponseEntity.badRequest()
				.body(new ErrorView(ErrorCode.MATRICULE_EXISTANT, "Le matricule a déjà été utilisé"));
	}

}