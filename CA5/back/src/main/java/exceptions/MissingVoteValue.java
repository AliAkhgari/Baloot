package exceptions;

import static defines.Errors.MISSING_VOTE_VALUE;

public class MissingVoteValue extends Exception {
    public MissingVoteValue() {
        super(MISSING_VOTE_VALUE);
    }

}
