package com.khusanjon.springstatemachinesample.config;

import com.khusanjon.springstatemachinesample.context.ContextValue;
import com.khusanjon.springstatemachinesample.domain.offer.Offer;
import com.khusanjon.springstatemachinesample.domain.offer.OfferEvent;
import com.khusanjon.springstatemachinesample.domain.offer.OfferState;
import com.khusanjon.springstatemachinesample.storage.OfferStore;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class OfferStateMachineInterceptor extends StateMachineInterceptorAdapter<OfferState, OfferEvent> {

    private final OfferStore offerStore;

    @Override
    public void postStateChange(State<OfferState, OfferEvent> state, Message<OfferEvent> message, Transition<OfferState, OfferEvent> transition,
                                StateMachine<OfferState, OfferEvent> stateMachine, StateMachine<OfferState, OfferEvent> rootStateMachine) {
        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(ContextValue.OFFER_ID_HEADER, -1L)))
                    .ifPresent(offerId -> {
                        Offer offer = offerStore.getById(offerId);
                        offer.setOfferState(state.getId());
                        offerStore.store(offer);
                    });
        });
    }
}
