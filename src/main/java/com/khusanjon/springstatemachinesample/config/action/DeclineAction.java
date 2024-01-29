package com.khusanjon.springstatemachinesample.config.action;

import com.khusanjon.springstatemachinesample.domain.offer.OfferEvent;
import com.khusanjon.springstatemachinesample.domain.offer.OfferState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class DeclineAction implements Action<OfferState, OfferEvent> {
    @Override
    public void execute(StateContext<OfferState, OfferEvent> context) {
        System.out.println("Declined!");
    }
}
