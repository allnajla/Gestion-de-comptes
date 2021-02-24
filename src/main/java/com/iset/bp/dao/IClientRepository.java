package com.iset.bp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iset.bp.entities.Client;

public interface IClientRepository extends JpaRepository<Client, Long>{

}
