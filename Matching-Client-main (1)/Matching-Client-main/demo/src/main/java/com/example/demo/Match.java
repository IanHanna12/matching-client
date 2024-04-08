package com.example.demo;

public class Match {

        private String initiator;
        private User matchedUser;


        public String getInitiator() {
            return initiator;
        }

        public void setInitiator(String initiator) {
            this.initiator = initiator;
        }

        public User getMatchedUser() {
            return matchedUser;
        }

        public void setMatchedUser(User matchedUser) {
            this.matchedUser = matchedUser;
        }

        public Match(String initiator, User matchedUser) {
            this.initiator = initiator;
            this.matchedUser = matchedUser;
        }
    }
