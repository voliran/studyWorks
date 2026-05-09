package org.example.repository;

import org.example.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Contact> findAllContacts() {
        String sql = "SELECT * FROM public.contacts";
        return jdbcTemplate.query(sql, (rs, n) -> new Contact(rs.getLong("id"), rs.getString("name"), rs.getString("surname"), rs.getString("phone_number"), rs.getString("email")));
    }

    public Optional<Contact> findContactById(Long id) {
        String sql = "SELECT * FROM public.contacts WHERE id = ?";
        try {
            Contact contact = jdbcTemplate.queryForObject(sql, (rs, n) -> new Contact(rs.getLong("id"), rs.getString("name"), rs.getString("surname"), rs.getString("phone_number"), rs.getString("email")), id);
            return Optional.ofNullable(contact);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Contact addContact(Contact contact) {
        String sql = "INSERT INTO public.contacts(name, surname, phone_number, email) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getSurname());
            ps.setString(3, contact.getPhoneNumber());
            ps.setString(4, contact.getEmail());
            return ps;
        }, keyHolder);
        contact.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return contact;
    }

    public int updatePhoneNumber(Long id, String phoneNumber) {
        String sql = "UPDATE public.contacts SET phone_number = ? WHERE id = ?";
        return jdbcTemplate.update(sql, phoneNumber, id);
    }

    public int updateEmail(Long id, String email) {
        String sql = "UPDATE public.contacts SET email = ? WHERE id = ?";
        return jdbcTemplate.update(sql, email, id);
    }

    public void deleteContactById(Long id) {
        String sql = "DELETE FROM public.contacts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
