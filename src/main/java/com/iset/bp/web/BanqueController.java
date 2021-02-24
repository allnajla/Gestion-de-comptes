package com.iset.bp.web;
 

import com.iset.bp.entities.Compte;
import com.iset.bp.entities.Operation;
import com.iset.bp.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BanqueController {    //ceci est un controlleur Spring MVC

 //la couche web a besoin de la couche metier --> d'où
	@Autowired
	private IBanqueMetier iBanqueMetier;
	
	
	@RequestMapping("/operations")  //càd , quand je tape : localhost/operations j'aurai une vue qui s'appelle comptes.html
	public String getOperations() {   //cette methode retourne une vue tous simplement
		 return "comptesstep1"; //càd : le nom de la vue est : comptes.html
	 }
	
	@RequestMapping("/consultercompte1")  //càd , quand je tape : localhost/consulterCompte j'aurai une vue qui s'appelle comptes.html
	public String consulterCompte1(Model model, String codeCompte) {   //cette methode retourne une vue tous simplement
		
		model.addAttribute("codeCompte",codeCompte);
		
		try {
			
			Compte compte=iBanqueMetier.getCompte(codeCompte);  //le bloc de try catch est à cause de la generation d'exception de la methode getCompte
			System.out.println(compte.getCodeCompte()+ "  "+ compte.getSolde());
			model.addAttribute("compte",compte);
	 
		}catch (Exception e) {
			model.addAttribute("exception",e);
		}
		

		return "comptesstep1"; //càd : le nom de la vue est : comptes.html
	}	
	@RequestMapping("/consultercompte")  //càd , quand je tape : localhost/consulterCompte j'aurai une vue qui s'appelle comptes.html
	public String consulterCompte(Model model, String codeCompte,@RequestParam(name="page",defaultValue="0")int page,@RequestParam(name="size",defaultValue="5")int size) {   //cette methode retourne une vue tous simplement
		
		model.addAttribute("codeCompte",codeCompte);
		
		try {
			
			Compte compte=iBanqueMetier.getCompte(codeCompte);  //le bloc de try catch est à cause de la generation d'exception de la methode getCompte
			System.out.println(compte.getCodeCompte()+ "  "+ compte.getSolde());
			Page<Operation>  pageOperations = iBanqueMetier.listOperationsCompte(codeCompte ,page, size);
			model.addAttribute("listOperations",pageOperations.getContent());
			int[] pages=new int[pageOperations.getTotalPages()];
			model.addAttribute("pages", pages);
			model.addAttribute("compte",compte);
	 
		}catch (Exception e) {
			model.addAttribute("exception",e);
		}
		

		return "comptes"; //càd : le nom de la vue est : comptes.html
	}
	
	@RequestMapping(value="/saveOperation",method=RequestMethod.POST)   
	public String saveOperation(Model model,String typeOperation, String codeCompte,double montant, String codeCompte2) {   //cette methode retourne une vue tous simplement
		
		try {
			if(typeOperation.equals("VERS")) {
				
				iBanqueMetier.versement(codeCompte,montant);
			} else if(typeOperation.equals("RETR")) {
				
				iBanqueMetier.retrait(codeCompte,montant);
			} else  {
				
				iBanqueMetier.virement(codeCompte,codeCompte2,montant);
			} 
		}catch (Exception e) {
			model.addAttribute("error",e);
			return "redirect:/consultercompte?codeCompte="+codeCompte+"&error="+e.getMessage();  
		}
		 
		
		return "redirect:/consultercompte?codeCompte="+codeCompte;  
	}
	

}
