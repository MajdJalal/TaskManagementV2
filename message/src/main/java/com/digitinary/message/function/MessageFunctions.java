package com.digitinary.message.function;


import com.digitinary.message.dto.UserMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;


@Configuration
public class MessageFunctions {

    private final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    /**
     * as this function is exposed, and there is a binding between it and a queue(which is connected to an exchange)
     * when any thing is pushed in the queue from the exchange it is gonna be passed as an input to this function
     * and it is gonna process and produce an output which in turn gonna be pushed through the output binding to an exchange
     * @return
     */
    @Bean
    public Function<UserMsgDto, Long> email(){
        return userMsgDto -> {
            log.info("sending email with details ");
            return userMsgDto.getUserId();
        };

    }

}
