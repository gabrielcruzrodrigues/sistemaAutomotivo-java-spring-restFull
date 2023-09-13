package com.example.sistema.controllers;

import com.example.sistema.models.personModels.Client;
import com.example.sistema.services.personServices.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/client")
@Validated
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @Validated(Client.CreatePerson.class)
    public ResponseEntity<Client> create (@Valid @RequestBody Client client) {
        this.clientService.create(client);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(client.getId())
                .toUri();

        return ResponseEntity.created(uri).body(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Client client = this.clientService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update (@Valid @RequestBody Client clientObj, @PathVariable Long id) {
        clientObj.setId(id);
        Client client = this.clientService.update(clientObj);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        this.clientService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado!");
    }
}
