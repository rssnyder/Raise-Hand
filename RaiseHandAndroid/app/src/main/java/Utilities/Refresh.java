package Utilities;

import java.util.ArrayList;

/**
 * This is a class of methods to enable swipe down for request
 * @author sae1
 */

public class Refresh {
    /**
     * Given a parent topicID, the list of questions will be refreshed using android volley
     * @param parentTopicID
     * @return an array list of questions in this topic
     */
    public ArrayList<Question> refreshQuestions(String parentTopicID){
        return null;
    }

    /**
     * Given a parent question id, it will return a list of questions that directly correspond
     * to the topic (not replies to replies)
     * @param parentQuestionID
     * @return an array list of replies directly to a question
     */
    public ArrayList<Reply> refreshReplies(String parentQuestionID){
        return null;
    }

    /**
     * Given a parent reply id, it will return a list of replies to that reply
     * @param parentReplyID
     * @return an array list of replies to a reply
     */
    public ArrayList<Reply> refreshRepliesOfReplies(String parentReplyID){
        return null;
    }
}
