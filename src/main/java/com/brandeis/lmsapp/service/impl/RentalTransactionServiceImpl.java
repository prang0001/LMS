package com.brandeis.lmsapp.service.impl;

import com.brandeis.lmsapp.domain.Patron;
import com.brandeis.lmsapp.service.MailService;
import com.brandeis.lmsapp.service.RentalTransactionService;
import com.brandeis.lmsapp.domain.RentalTransaction;
import com.brandeis.lmsapp.repository.RentalTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing RentalTransaction.
 */
@Service
@Transactional
public class RentalTransactionServiceImpl implements RentalTransactionService {

    private final Logger log = LoggerFactory.getLogger(RentalTransactionServiceImpl.class);

    private final RentalTransactionRepository rentalTransactionRepository;

    @Autowired
    MailService mailService;

    public RentalTransactionServiceImpl(RentalTransactionRepository rentalTransactionRepository) {
        this.rentalTransactionRepository = rentalTransactionRepository;
    }

    /**
     * Save a rentalTransaction.
     *
     * @param rentalTransaction the entity to save
     * @return the persisted entity
     */
    @Override
    public RentalTransaction save(RentalTransaction rentalTransaction) {
        log.debug("Request to save RentalTransaction : {}", rentalTransaction);
        return rentalTransactionRepository.save(rentalTransaction);
    }

    /**
     * Get all the rentalTransactions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RentalTransaction> findAll(Pageable pageable) {
        log.debug("Request to get all RentalTransactions");
        return rentalTransactionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findOverdueRentals() {
        log.debug("Request to get patron emails to send reminder of overdue library resources");
        List<String> patronsToContact = new ArrayList<>();
        patronsToContact = rentalTransactionRepository.findOverdueRentals();
        log.info(patronsToContact.toString());
        if(patronsToContact.size()>0){
            sendEmailForOverdueResources(patronsToContact);
        }
        return patronsToContact;
    }


    @Transactional(readOnly = true)
    public List<RentalTransaction> findByPatron(String login) {
        log.debug("Request to get rentals by patron login--"+login);
        List<RentalTransaction> rtl = new ArrayList<>();
        rtl = rentalTransactionRepository.findByPatron(login);
        log.info(rtl.toString());
        return rtl;
    }

    @Transactional(readOnly = true)
    public void sendEmailForOverdueResources(List<String> patrons) {
        String content = "Greetings, \n\nYou have overdue library resources. Please return your overdue items as soon as possible or late fees will be applied.\n\n" +
            "You can visit the LMS application to review: http://localhost:9000/#/ \n\n"+
            " Thank you. \n\n -LMS App Team";
        log.info("Sending emails to patrons with overdue resources...");
        for (String p : patrons) {
            System.out.println(p);
            mailService.sendEmailFromTemplateForOverdueResource(p, "You have overdue library resources - LMS", content, false, true);
        }
    }

    /**
     * Get all the RentalTransaction with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<RentalTransaction> findAllWithEagerRelationships(Pageable pageable) {
        return rentalTransactionRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one rentalTransaction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RentalTransaction> findOne(Long id) {
        log.debug("Request to get RentalTransaction : {}", id);
        return rentalTransactionRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the rentalTransaction by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RentalTransaction : {}", id);
        rentalTransactionRepository.deleteById(id);
    }
}
