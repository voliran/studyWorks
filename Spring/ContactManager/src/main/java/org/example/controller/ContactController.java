package org.example.controller;

import org.example.model.Contact;
import org.example.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    public List<Contact> allContacts() {
        return contactService.findAllContacts();
    }

    @GetMapping("/contact/{contactId}")
    public Contact findContactById(@PathVariable("contactId") Long contactId) {
        return contactService.findContactById(contactId);
    }

    @PostMapping("/contacts")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        Contact created = contactService.addContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/contacts/{contactId}/phone")
    public ResponseEntity<Void> updatePhoneNumber(@PathVariable Long contactId, @RequestParam String phoneNumber) {
        contactService.updatePhoneNumber(contactId, phoneNumber);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/contacts/{contactId}/email")
    public ResponseEntity<Void> updateEmail(@PathVariable Long contactId, @RequestParam String email) {
        contactService.updateEmail(contactId, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/contacts/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable("contactId") Long contactId) {
        contactService.deleteContactById(contactId);
        return ResponseEntity.noContent().build();
    }
}
