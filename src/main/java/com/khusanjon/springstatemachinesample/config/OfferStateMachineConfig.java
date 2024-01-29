package com.khusanjon.springstatemachinesample.config;

import com.khusanjon.springstatemachinesample.domain.offer.OfferEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import com.khusanjon.springstatemachinesample.domain.offer.OfferState;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableStateMachine
public class OfferStateMachineConfig extends EnumStateMachineConfigurerAdapter<OfferState, OfferEvent> {

    private final Guard<OfferState, OfferEvent> eventGuard;
    private final Action<OfferState, OfferEvent> offerAction, updateAction, declineAction, cancelAction, acceptAction, approveAction;

    @Override
    public void configure(StateMachineConfigurationConfigurer<OfferState, OfferEvent> config) throws Exception {
        StateMachineListenerAdapter<OfferState, OfferEvent> adapter = new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<OfferState, OfferEvent> from, State<OfferState, OfferEvent> to) {
                log.info(String.format("stateChanged(from: %s, to: %s", from, to));
            }
        };

        config.withConfiguration().listener(adapter);
    }

    @Override
    public void configure(StateMachineStateConfigurer<OfferState, OfferEvent> states) throws Exception {
        states.withStates()
                .initial(OfferState.OFFERED)
                .states(EnumSet.allOf(OfferState.class))
                .end(OfferState.SOLD)
                .end(OfferState.CANCELED)
                .end(OfferState.DECLINED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OfferState, OfferEvent> transitions) throws Exception {
        transitions
                //states changing logic with transitions
                .withExternal().source(OfferState.OFFERED).target(OfferState.OFFERED).event(OfferEvent.OFFER).action(offerAction).guard(eventGuard)
                .and()
                .withExternal().source(OfferState.OFFERED).target(OfferState.ACCEPTED).event(OfferEvent.ACCEPT).action(updateAction).guard(eventGuard)
                .and()
                .withExternal().source(OfferState.OFFERED).target(OfferState.UPDATED).event(OfferEvent.UPDATE).action(updateAction).guard(eventGuard)
                .and()
                .withExternal().source(OfferState.UPDATED).target(OfferState.UPDATED).event(OfferEvent.UPDATE).action(updateAction).guard(eventGuard)
                .and()
                .withExternal().source(OfferState.UPDATED).target(OfferState.ACCEPTED).event(OfferEvent.ACCEPT).action(acceptAction).guard(eventGuard)
                .and()
                .withExternal().source(OfferState.UPDATED).target(OfferState.DECLINED).event(OfferEvent.DECLINE).action(declineAction).guard(eventGuard)
                .and()
                .withExternal().source(OfferState.OFFERED).target(OfferState.CANCELED).event(OfferEvent.CANCEL).action(cancelAction).guard(eventGuard)
                .and()
                .withExternal().source(OfferState.UPDATED).target(OfferState.CANCELED).event(OfferEvent.CANCEL).action(cancelAction).guard(eventGuard)
                .and()
                .withExternal().source(OfferState.ACCEPTED).target(OfferState.CANCELED).event(OfferEvent.CANCEL).action(cancelAction).guard(eventGuard)
                .and()
                .withExternal().source(OfferState.ACCEPTED).target(OfferState.SOLD).event(OfferEvent.APPROVE).action(approveAction).guard(eventGuard);
    }
}
