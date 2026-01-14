package com.bank.api.service.impl;

import com.bank.api.entity.Cliente;
import com.bank.api.exception.ClienteExistenteException;
import com.bank.api.exception.NotFoundException;
import com.bank.api.repository.ClienteRepository;
import com.bank.api.service.ClienteService;
import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Cliente create(Cliente cliente) {
        //Generemos un clientId, para no mandar por postman y menos desde la app-web
        //

        repository.findByIdentificacion(cliente.getIdentificacion())
        .ifPresent(c -> {
            throw new ClienteExistenteException();
        });

        if (cliente.getPassword() == null || cliente.getPassword().isBlank()) {
            String rawPassword = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 8);
            cliente.setPassword(passwordEncoder.encode(rawPassword));
        }

        if (cliente.getPassword() != null && !cliente.getPassword().isBlank()) {
            cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        }
        

        Cliente saved = repository.save(cliente);
        System.out.println("cliente llego");
        if (saved.getClientId() == null || saved.getClientId().isBlank()) {
            saved.setClientId(String.format("CL-%06d", saved.getId()));
            saved = repository.save(saved);
        }
        return saved;
    }

    //@Override
    //public Cliente create(Cliente cliente) {
    //    Cliente saved = repository.save(cliente);
    //    return saved;
    //}

    @Override
    public Cliente getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
    }

    @Override
    public List<Cliente> list(String q) {
        if (q == null || q.isBlank()) {
            return repository.findByEstadoTrue();
        }
        return repository.quickSearch(q);
    }

    @Override
    public Cliente update(Long id, Cliente cliente) {
        Cliente existing = getById(id);
        System.out.println("Entra en el update");
        cliente.setId(existing.getId()); // asegura que es el mismo
        return repository.save(cliente);
    }

    @Override
    public Cliente patch(Long id, Cliente partial) {
        Cliente existing = getById(id);
        
        if (partial.getNombre() != null) existing.setNombre(partial.getNombre());
        if (partial.getDireccion() != null) existing.setDireccion(partial.getDireccion());
        if (partial.getTelefono() != null) existing.setTelefono(partial.getTelefono());
        if (partial.getEstado() != null) existing.setEstado(partial.getEstado());
        if (partial.getClientId() == null) existing.setClientId(existing.getClientId());
        if (partial.getPassword() == null) existing.setPassword(existing.getPassword());
        //Estos no se deben poder cambiar
        //if (partial.getPassword() != null) existing.setPassword(partial.getPassword());
        //if (partial.getIdentificacion() != null) existing.setIdentificacion(partial.getIdentificacion());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado");
        }
        Cliente existing = getById(id);
        existing.setEstado(false);
        repository.save(existing);
    }
}
