# Mandatory Requirements

## Functioinal Requirements

###1. Polls
- 1.1 The system must support electronic polls with one or more items.
- 1.2 Each poll must have a title. **The title has to be unique.**
- 1.3 Each poll must have a description. a title. **The title has to be unique.**
- 1.4 Each poll must have a voting period (start and end date with time).
- 1.5 Each poll must have at least one item.
- 1.6 The system must allow to group arbitrary many items into one poll.

### 2. Poll States
- 2.1 The system must implement poll states. Polls be in one of four States:
  - PREPARED
  - STARTED
  - VOTING
  - FINISHED
- 2.2 When all participants submitted their votes, the system must set the poll to FINISHED

### 3. Organizers
- 3.1 The system must allow all university members to act as organizers.
- 3.2 Organizers must identify themselves with username and password.
- 3.3 An organizer must be able to conduct arbitrary many polls.

### 5. Participants
5.1 The organizer of a poll must be able to invite 3 to arbitrary many participants. In polls with less than 3 participants, anonymity can not be asserted.
5.2 Each participant must be identified by her email address.
5.3 The information mail must include the title of the poll, the start and end dates, the number of participants, and a token.

### 6. Participant lists
- 6.1 The organizer must be able to modify the participant list until a poll is STARTED.

### 7. Tokens
- 7.1 The token must be randomly chosen.
- 7.2 The token must be unique (scope is system).
- 7.3 The token must be long enough to make it ver very improbable that anybody can forge a valid token.

### 8. Anonymity
- 8.1 The system must ensure anonymity.
- 8.2 At any point in time it must be impossible to identify which participant submitted which vote. This also must to be guaranteed for polls with participation tracking.
- 8.3 The system must ensure that a token cannot be associated with a vote.

### 10. Submitting a vote
- 10.1 The system must provide a web page to submit a vote.
- 10.2 The voting page must present an input field for a participant's token
- 10.4 After the token was verified, the system must display the items.
- 10.5 The system must present a button to submit a vote.
- 10.6 After a vote was submitted, the token used for that vote must be invalidated (i.e. it can't be re-used, participants cannot chang their vote after submitting).
- 10.7 The system must allow to cancel a voting (e.g. by closing the browser, or by clicking a cancel button).
- 10.8 The token used in cancelled voting must be re-useable later.

### 11. Abstain from voting
- 11.1 the system must provide a means to abstain from voting (Enthaltung oder ungültige Stimme) for each item of a poll.

### 12. Type of items
- 12.1 The system must support different types of items.
- 12.2 The items of apoll must have atitle (**titles have to be unique with scope poll**).
- 12.3 The options of an item must have a short name and a description
- 12.4 The system must supoort **YES/NO** items. In this case, the short names of the options are "yes" and "no".
- 12.5 The system must support **1 OF N** items. Participants can choose exactly one of two or more options (M<=N).

### 13. Results
- 13.1 An organizer must be able to view the results of a poll after the voting period is FINISHED.
- 13.2 Nobody must be able to view (intermediate) results in the STARTED and VOTING states.
- 13.4 The system must not show results of polls with less than 3 submitted votes.
- 13.5 The results view must show the number of votes for each option of each item of a poll as well as the number of abstentions.


## Non-Functionls Requirements
### 14. User Interface
- 14.4 The system must provide a user interface suitable for desktop/laptop browsers.
- 14.6 **The user interface mustbe realized with JSF.**

### 15. Security, encrypted communication
- 15.1 The system must store passwords in encrypted form (unless LDAP is used for identification).


### 17. Browser support
- 17.1 The system must support at least one of the following web browsers in the most recent stable version available at delivery of the project:
  - FireFox
  - Safari
  - Chrome
