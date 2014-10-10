/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.ItemOptionTO;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.ParticipantTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.logic.to.TokenTO;
import jjlm.votes.persistence.entities.Item;
import jjlm.votes.persistence.entities.ItemType;
import jjlm.votes.persistence.entities.PollState;

@Named
@SessionScoped
public class PollVotingBean implements Serializable {

    private class ItemState {

        private ItemTO item;
        private boolean abstained;

        public ItemTO getItem() {
            return item;
        }

        public void setItem(ItemTO item) {
            this.item = item;
        }

        public boolean isAbstained() {
            return abstained;
        }

        public void setAbstained(boolean abstained) {
            this.abstained = abstained;
        }

    }

    @EJB
    protected VotesLogic logic;

    private String tokenSignature;
    private boolean verified = false;

    private PollTO poll;
    private TokenTO token;
    private Map<Integer, OptionState> optionStates;
    private Map<Integer, ItemState> itemStates;

    public PollVotingBean() {

        optionStates = new HashMap<Integer, OptionState>();
        itemStates = new HashMap<Integer, ItemState>();

    }

    public String getTokenSignature() {
        return tokenSignature;
    }

    public void setTokenSignature(String tokenSignature) {
        this.tokenSignature = tokenSignature;
    }

    public boolean isVerified() {
        return verified;
    }

    public PollTO getPoll() {
        return poll;
    }

    public void setPoll(PollTO poll) {
        this.poll = poll;
    }

    public TokenTO getToken() {
        return token;
    }

    public void setToken(TokenTO token) {
        this.token = token;
    }

    public String updateOptionState(int optionId) {

        OptionState opState = this.optionStates.get(optionId);
        System.out.println("setOptionSate");
        if (opState.getItem().getItemType() == ItemType.M_OF_N) {

            // toggle self
            System.out.println("setOptionSate1");
            opState.setSelected(!opState.isSelected());
            optionStates.put(optionId, opState);

        } else {
            System.out.println("setOptionSate2");
            // toggle others
            for (ItemOptionTO io : opState.getItem().getOptions()) {
                OptionState ops = this.optionStates.get(io.getId());
                ops.setSelected(false);
                optionStates.put(io.getId(), ops);
            }

            opState.setSelected(true);
            optionStates.put(optionId, opState);

            System.out.println("setOptionSate3");
        }

        System.out.println(this.optionStates);

        return "poll";

    }

    public String getOptionStyleClass(int optionId) {

        if (this.optionStates.get(optionId).isSelected()) {

            return "btn btn-success";

        } else {

            return "btn btn-primary";

        }

    }

    public String getOptionGlyphicon(int optionId) {

        if (this.optionStates.get(optionId).isSelected()) {

            return "glyphicon glyphicon-ok";

        } else {

            return "glyphicon glyphicon-remove";

        }

    }

    /**
     * initialize bean on request
     */
    public void init() {

    }

    public boolean itemIsAbstained(int itemId) {

        if (this.itemStates.containsKey(itemId)) {

            return this.itemStates.get(itemId).isAbstained();

        }

        return false;

    }

    public void abstainItem(int itemId) {

        if (this.itemStates.containsKey(itemId)) {

            ItemState itemState = this.itemStates.get(itemId);
            itemState.setAbstained(!itemState.isAbstained());

        }

    }

    public String verify() {

        token = logic.getTokenBySignature(tokenSignature);
        if (token != null) {
            this.verified = !token.isInvalid();
            poll = token.getPoll();
        } else {
            this.verified = false;
        }

        poll.setItems(logic.getItemsOfPoll(poll.getId()));

        for (ItemTO item : poll.getItems()) {
            item.setOptions(logic.getOptionsOfItem(item.getId()));
        }

        if (this.optionStates.isEmpty()
                && this.itemStates.size() == 0) {

            for (ItemTO item : poll.getItems()) {

                ItemState itemState = new ItemState();
                itemState.setItem(item);
                itemState.setAbstained(false);
                itemStates.put(item.getId(), itemState);

                for (ItemOptionTO io : item.getOptions()) {

                    OptionState opState = new OptionState();
                    opState.setItem(item);
                    opState.setSelected(false);
                    optionStates.put(io.getId(), opState);

                }

            }

        }

        return "poll";

    }

    public String submit() {

        boolean allValid = true;

        if (allValid) {

            ParticipantTO participant = token.getParticipant();
            participant.setHasVoted(true);
            logic.storeParticipant(participant);

            long participation = logic.getParticipation(token.getPoll().getId());
            int participants = logic.getParticipantsOfPoll(token.getPoll().getId()).size();

            if (participation == participants) {
                token.getPoll().setPollState(PollState.FINISHED);
            } else {

                token.getPoll().setPollState(PollState.VOTING);
            }

            logic.storePoll(token.getPoll());

            token.setInvalid(true);
            logic.storeToken(token);

            for (Integer i : itemStates.keySet()) {

                ItemState is = itemStates.get(i);

                if (is.isAbstained()) {
                    logic.incrementAbstainedItem(is.getItem().getId());
                } else {

                    for (ItemOptionTO io : is.getItem().getOptions()) {

                        if (optionStates.containsKey(io.getId())
                                && optionStates.get(io.getId()).isSelected()) {

                            logic.incrementItemOptionCount(io.getId());

                        }
                    }
                }
            }
        }
        verified = false;
        tokenSignature = "";

        return "poll";

    }

    public String abstainPoll() {

        return "poll";

    }

    public String cancel() {

        verified = false;
        tokenSignature = "";

        return "poll";

    }

    private boolean foo = false;

    public void toggleFoo() {

        System.out.println(this.foo);
        this.foo = !this.foo;

    }

    public boolean getFoo() {

        return this.foo;

    }

}
