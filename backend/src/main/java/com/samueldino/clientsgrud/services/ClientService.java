package com.samueldino.clientsgrud.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samueldino.clientsgrud.dto.ClientDTO;
import com.samueldino.clientsgrud.entities.Client;
import com.samueldino.clientsgrud.repositories.ClientRepository;
import com.samueldino.clientsgrud.services.exceptions.DatabaseException;
import com.samueldino.clientsgrud.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){	
		return repository.findAll(pageRequest).map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		return new ClientDTO(obj.orElseThrow(()-> new ResourceNotFoundException("ID não encontrado !")));
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		return new ClientDTO(repository.save(new Client(dto)));
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = new Client(id, dto);
			return new ClientDTO(repository.save(entity));
		}
		catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("ID não encontrado !");
		}

	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("ID não encontrado !");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de Integridade");
		}
	}
}
