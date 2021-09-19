package com.brandeis.lmsapp.util;

import com.brandeis.lmsapp.service.RentalTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@EnableScheduling
public class LmsScheduler {

    @Autowired
    RentalTransactionService rentalTransactionService;

    private static final Logger logger = LoggerFactory.getLogger(LmsScheduler.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    //CH - Run job every hour to check for resources that require a communication sent to the Patron
    //@Scheduled(cron = "0 * * * * *")
    public void scheduleTaskForResourceDueDate() {
        logger.info("Task For Sending Resource Due Emails Triggered :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
        rentalTransactionService.findOverdueRentals();
    }

}
