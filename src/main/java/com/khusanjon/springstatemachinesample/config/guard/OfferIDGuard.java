package com.khusanjon.springstatemachinesample.config.guard;

import com.khusanjon.springstatemachinesample.context.ContextValue;
import com.khusanjon.springstatemachinesample.domain.offer.OfferEvent;
import com.khusanjon.springstatemachinesample.domain.offer.OfferState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
public class OfferIDGuard implements Guard<OfferState, OfferEvent> {
    @Override
    public boolean evaluate(StateContext<OfferState, OfferEvent> context) {
        return context.getMessageHeader(ContextValue.OFFER_ID_HEADER) != null;
    }
}
