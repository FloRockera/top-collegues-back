package dev.top.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.top.controller.views.FormulaireView;
import dev.top.entities.Avis;
import dev.top.entities.Collegue;
import dev.top.exceptions.CollegueIntrouvableViaMatriculeException;
import dev.top.exceptions.MatriculeExistantException;
import dev.top.exceptions.PseudoInvalideException;
import dev.top.exceptions.ServiceException;
import dev.top.repos.CollegueRepo;
import dev.top.services.domain.CollegueSI;

@Service
public class CollegueService {

	private CollegueRepo collRepo;

	public CollegueService(CollegueRepo collRepo) {
		super();
		this.collRepo = collRepo;
	}

	public List<Collegue> listerCollegues() {
		return this.collRepo.findAll();
	}

	/**
	 * 
	 * @param pseudo
	 * @param avisUtilisateur
	 * @return
	 * @throws ServiceException
	 * @throws PseudoInvalideException
	 */
	public Collegue modifierScore(String pseudo, Avis avisUtilisateur)
			throws ServiceException, PseudoInvalideException {

		return this.collRepo.findByPseudo(pseudo).map(collegueTrouve -> {

			if (Avis.AIMER.equals(avisUtilisateur)) {
				collegueTrouve.setScore(collegueTrouve.getScore() + 10);
			}

			if (Avis.DETESTER.equals(avisUtilisateur)) {
				collegueTrouve.setScore(collegueTrouve.getScore() - 5);
			}

			this.collRepo.save(collegueTrouve);

			return collegueTrouve;
		}).orElseThrow(() -> new PseudoInvalideException());

	}

	// si le matricule existe deja, ne pas insérer, sinon sauvegarder le
	// collegue
	public Collegue ajouterCollegue(FormulaireView formulaire) throws ServiceException {

		CollegueSI[] collegueSIs = new RestTemplate().getForObject(
				"http://collegues-api.cleverapps.io/collegues?matricule={matricule}", CollegueSI[].class,
				formulaire.getMatricule());

		if (collegueSIs.length > 0) {
			CollegueSI colSI = collegueSIs[0];

			Collegue nouveauCollegue = new Collegue();

			// faire un controle sur les doublons : utiliser la méthode
			// findByMatricule !!!!!!!!!!!!!!!

			if ((formulaire.getMatricule()) != (nouveauCollegue.getMatricule())) {
				nouveauCollegue.setMatricule(formulaire.getMatricule());

			} else {
				throw new MatriculeExistantException();
			}

			// nouveauCollegue.setMatricule(formulaire.getMatricule());

			nouveauCollegue.setPseudo(formulaire.getPseudo());
			nouveauCollegue.setNom(colSI.getNom());
			nouveauCollegue.setPrenom(colSI.getPrenom());
			nouveauCollegue.setEmail(colSI.getEmail());
			nouveauCollegue.setAdresse(colSI.getAdresse());
			nouveauCollegue.setScore(0);

			// faire un if pour récupérer la photo si pas d'url dans le
			// formulaire

			if (StringUtils.isBlank(formulaire.getUrlimage())) {
				nouveauCollegue.setPhoto(colSI.getPhoto());

			} else {
				nouveauCollegue.setPhoto(formulaire.getUrlimage());
			}

			this.collRepo.save(nouveauCollegue);

			return nouveauCollegue;

		} else {
			throw new CollegueIntrouvableViaMatriculeException();
		}
	}

}