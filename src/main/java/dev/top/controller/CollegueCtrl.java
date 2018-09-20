package dev.top.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.top.Converters;
import dev.top.controller.views.AvisView;
import dev.top.controller.views.CollegueView;
import dev.top.controller.views.FormulaireView;
import dev.top.entities.Collegue;
import dev.top.services.CollegueService;

@CrossOrigin
@RestController()
@RequestMapping("/accueil")
public class CollegueCtrl {

	private CollegueService collegueService;

	public CollegueCtrl(CollegueService collegueService) {
		this.collegueService = collegueService;
	}

	// *************************************GET***********************************************

	// Exposer le service GET /collegues qui récupère la liste des collègues au
	// format JSON
	@GetMapping
	public ResponseEntity<List<CollegueView>> findAll() {
		return ResponseEntity.ok(this.collegueService.listerCollegues().stream()
				.map(col -> Converters.COLLEGUE_TO_COLLEGUE_VIEW.convert(col)).collect(Collectors.toList()));
	}

	// *************************************PATCH***********************************************

	// Exposer un service PATCH /collegues/PSEUDO/ qui permet de mettre à jour
	// le score d'un collègue
	@PatchMapping("/{pseudo}")
	public ResponseEntity<CollegueView> patch(@PathVariable String pseudo, @RequestBody AvisView avis) {

		Collegue collegueModifie = this.collegueService.modifierScore(pseudo, avis.getAction());

		return ResponseEntity.ok(Converters.COLLEGUE_TO_COLLEGUE_VIEW.convert(collegueModifie));
	}

	// *************************************POST***********************************************

	// Récupérer les données envoyées par le formulaire d'ajout d'un collègue
	@PostMapping("/formulaire")
	public ResponseEntity<CollegueView> post(@RequestBody FormulaireView formulaire) {

		Collegue collegueAjoute = this.collegueService.ajouterCollegue(formulaire);

		return ResponseEntity.ok(Converters.COLLEGUE_TO_COLLEGUE_VIEW.convert(collegueAjoute));

	}

}