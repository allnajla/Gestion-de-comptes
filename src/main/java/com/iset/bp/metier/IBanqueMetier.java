package com.iset.bp.metier;

  
import com.iset.bp.entities.Compte; 
import com.iset.bp.entities.Operation;
import org.springframework.data.domain.Page;

public interface IBanqueMetier {

	 public Compte getCompte(String codeCompte);
	 public void versement(String codeCompte, double montant );
	 public void retrait(String codeCompte, double montant );
	 public void virement(String codeCompteRetrait,String codeCompteVersement,double montant);
    

	  public Page<Operation> listOperationsCompte(String codeCompte,int page,int sizePage);
	/* 
	 public List<Operation> listOperationsCompte(String codeCompte);
	*/	
}
