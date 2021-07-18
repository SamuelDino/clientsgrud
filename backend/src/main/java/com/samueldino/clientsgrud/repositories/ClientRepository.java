package com.samueldino.clientsgrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samueldino.clientsgrud.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

}
