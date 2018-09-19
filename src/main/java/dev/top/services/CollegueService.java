package dev.top.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.top.entities.Avis;
import dev.top.entities.Collegue;
import dev.top.exceptions.PseudoInvalideException;
import dev.top.exceptions.ServiceException;
import dev.top.repos.CollegueRepo;

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

		return this.collRepo.findByPseudo(pseudo + "dddd").map(collegueTrouve -> {

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

}