package org.example.repository;

import org.example.model.Contact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql("classpath:contact.sql")
public record ContactRepositoryTest(@Autowired ContactRepository contactRepository ) {

    private static final Contact IVAN = new Contact(
            1000L, "Ivan", "Ivanov", "1234567", "iivanov@gmail.com"
    );

    private static final Contact MARIA = new Contact(
            2000L, "Maria", "Ivanova", "7654321", "mivanova@gmail.com"
    );

    /**
     * There are two contacts inserted in the database in contact.sql.
     */
    private static final List<Contact> PERSISTED_CONTACTS = List.of(IVAN, MARIA);

    @Test
    void addContact() {
        var contact = new Contact("Jackie", "Chan", "1234567890", "jchan@gmail.com");
        var contactId = contactRepository.addContact(contact).getId();
        contact.setId(contactId);

        var contactInDb = contactRepository.findContactById(contactId);


        assert contactInDb.orElse(null) != null;
        assertThat(contactInDb.orElse(null)).isEqualTo(contact);
    }

    @Test
    void getContact() {
        Optional<Contact> contact = contactRepository.findContactById(IVAN.getId());

        assert contact.orElse(null) != null;
        assertThat(contact.orElse(null)).isEqualTo(IVAN);
    }

    @Test
    void getAllContacts() {
        var contacts = contactRepository.findAllContacts();

        assertThat(contacts).containsAll(PERSISTED_CONTACTS);
    }

    @Test
    void updatePhoneNumber() {
        var contact = new Contact("Jekyll", "Hide", "0000000001", "jhide@gmail.com");
        var contactId = contactRepository.addContact(contact);

        var newPhone = "777-77-77";
        contactRepository.updatePhoneNumber(contactId.getId(), newPhone);

        var updatedContact = contactRepository.findContactById(contactId.getId());
        assert updatedContact.orElse(null) != null;
        assertThat(updatedContact.orElse(null).getPhoneNumber()).isEqualTo(newPhone);
    }

    @Test
    void updateEmail() {
        var contact = new Contact("Captain", "America", "0000000002", "");
        var contactId = contactRepository.addContact(contact);

        var newEmail = "cap@gmail.com";
        contactRepository.updateEmail(contactId.getId(), newEmail);

        var updatedContact = contactRepository.findContactById(contactId.getId());

        assert updatedContact.orElse(null) != null;
        assertThat(updatedContact.orElse(null).getEmail()).isEqualTo(newEmail);
    }

    @Test
    void deleteContact() {
        var contact = new Contact("To be", "Deleted", "0000000003", "");
        var contactId = contactRepository.addContact(contact);

        contactRepository.deleteContactById(contactId.getId());

        assertThat(contactRepository.findContactById(contactId.getId())).isEmpty();

    }
}