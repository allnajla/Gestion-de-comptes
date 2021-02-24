package com.iset.bp.dao;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iset.bp.entities.Operation;

public interface IOperationRepository extends JpaRepository<Operation, Long> {
	@Query("select o from Operation o where o.compte.codeCompte=:x  order by o.dateOperation desc")  //
	public Page<Operation>  listOperation(@Param("x")String codeCompte,Pageable pageable); // a importer :import org.springframework.data.domain.Pageable; et import org.springframework.data.domain.Page;
 
	@Query("select o from Operation o where o.compte.codeCompte=:x")  //
	public Collection<Operation>  listOperationx(@Param("x")String codeCompte); 
	
	// l'espace est non autoriser apres les ':'  et il faut "=:x"  et non pas ":=x"--> une erreur dans la requete va introduire plusieurs erreurs
	/*@Query("select o from Operation o where o.compte.codeCompte=:x")  // order by o.dateOperation desc
	public List<Operation>  listOperation(@Param("x")  String codeCompte); // a importer :import org.springframework.data.domain.Pageable; et import org.springframework.data.domain.Page;
	*/

}
