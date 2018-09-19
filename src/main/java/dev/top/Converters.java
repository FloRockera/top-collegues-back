package dev.top;

import org.springframework.core.convert.converter.Converter;

import dev.top.controller.views.CollegueView;
import dev.top.entities.Collegue;

public interface Converters {

	Converter<Collegue, CollegueView> COLLEGUE_TO_COLLEGUE_VIEW = source -> {
		CollegueView collegueView = new CollegueView();

		collegueView.setPseudo(source.getPseudo());
		collegueView.setScore(source.getScore());
		collegueView.setPhoto(source.getPhoto());

		return collegueView;
	};

	Converter<CollegueView, Collegue> COLLEGUE_VIEW_TO_COLLEGUE = source -> {
		Collegue collRepo = new Collegue();

		collRepo.setPseudo(source.getPseudo());
		collRepo.setScore(source.getScore());
		collRepo.setPhoto(source.getPhoto());

		return collRepo;
	};

}