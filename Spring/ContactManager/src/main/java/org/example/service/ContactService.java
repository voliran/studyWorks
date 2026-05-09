package org.example.service;

import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> findAllContacts() {
        return contactRepository.findAllContacts();
    }

    public Contact findContactById(Long id) {
        return contactRepository.findContactById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found with id: " + id));
    }

    @Transactional
    public Contact addContact(Contact contact) {
        return contactRepository.addContact(contact);
    }

    @Transactional
    public void updatePhoneNumber(Long id, String phoneNumber) {
        int rowsAffected = contactRepository.updatePhoneNumber(id, phoneNumber);
        if (rowsAffected == 0) {
            throw new RuntimeException("Contact not found with id: " + id);
        }
    }

    @Transactional
    public void updateEmail(Long id, String email) {
        int rowsAffected = contactRepository.updateEmail(id, email);
        if (rowsAffected == 0) {
            throw new RuntimeException("Contact not found with id: " + id);
        }
    }

    @Transactional
    public void deleteContactById(Long id) {
        contactRepository.deleteContactById(id);
    }
}
