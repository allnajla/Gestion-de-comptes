package com.iset.bp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iset.bp.entities.Compte;

public interface ICompteRepository extends JpaRepository<Compte, String> {

}
