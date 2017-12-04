package Utilities;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * This class handles all of the string parsing that
 * must be done due to android volley string responses
 * @author sae1
 */

public class StringParse {

    /**
     * Used within the classes object to parse through the information
     * returned by Volley regarding the topics within the class
     *
     * @param phpResponse
     * @return an array list of the topics that are in a given class
     */
    public static ArrayList<Topics> parseTopicsVolley(String phpResponse) {
        ArrayList<Topics> topics = new ArrayList<>();
        String[] seperated = phpResponse.split(" ");
        int max = seperated.length;
        int i = 0;
        //The string can contain multiple parts to indicate when we start reading new information
        while (i < max) {
            if (i < max && seperated[i].equals("NEWTOPIC")) {
                //NEWTOPIC indicates the start of a new topic, make a new topic object
                Topics tempTopic = new Topics();
                ArrayList<Question> q = new ArrayList<Question>();
                tempTopic.setQuestions(q);
                i++;
                while (i < max && !(seperated[i].equals("NEWTOPIC"))) {
                    if (i < max && seperated[i].equals("CREATETIME")) {
                        i++;
                        String Time = "";
                        while (!(seperated[i].equals("TOPICNAME")) && i < max) {
                            //I'm not sure if we need to add a space here or not
                            Time = Time + " " + seperated[i];
                            i++;
                        }
                        tempTopic.setTime(Time);
                    }
                    if (i < max && seperated[i].equals("TOPICNAME")) {
                        i++;
                        String Topic = "";
                        while (i < max && !(seperated[i].equals("ID"))) {
                            Topic = Topic + " " + seperated[i];
                            i++;
                        }
                        tempTopic.setTitle(Topic);
                    }
                    if (i < max && seperated[i].equals("ID")) {
                        //id of the topics
                        i++;
                        if (seperated[i].equals("DESCRIPTION")) {
                            //do nothing
                        } else {
                            tempTopic.setID(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("DESCRIPTION")) {
                        i++;
                        String Description = "";
                        while (i < max && !(seperated[i].equals("NEWQUESTION")) && !(seperated[i].equals("NEWTOPIC"))) {
                            Description = Description + " " + seperated[i];
                            i++;
                        }
                        tempTopic.setDescription(Description);
                    }
                    //TODO: Figure out what this isn't working
                    if (i < max && seperated[i].equals("NEWQUESTION")) {
                        //NEWQUESTION means the start of the new question within this topic, add to array list
                        Question tempQuestion = new Question();
                        ArrayList<Reply> replies = new ArrayList<Reply>();
                        tempQuestion.setReplies(replies);
                        i++;
                        //cannot be a new topic or new question starting (maybe need to add in new reply too)?
                        while (i < max && !(seperated[i].equals("NEWTOPIC")) && !(seperated[i].equals("NEWQUESTION"))) {
                            //Add new question to the array list for the topic
                            if (i < max && seperated[i].equals("QUESTIONTITLE")) {
                                //header for question
                                i++;
                                String title = "";
                                while (i < max && !(seperated[i].equals("QUESTIONDESCRIPTION"))) {
                                    title = title + seperated[i] + " ";
                                    i++;
                                }
                                tempQuestion.setQuestionTitle(title);

                            }
                            if (i < max && seperated[i].equals("QUESTIONDESCRIPTION")) {
                                //question
                                i++;
                                String desc = "";
                                while (i < max && !(seperated[i].equals("QUESTIONUSER"))) {
                                    desc = desc + seperated[i] + " ";
                                    i++;
                                }
                                tempQuestion.setQuestionDescription(desc);

                            }
                            if (i < max && seperated[i].equals("QUESTIONUSER")) {
                                //username who created it
                                i++;
                                if (seperated[i].equals("QUESTIONUSERID")) {
                                    //do nothing
                                } else {
                                    tempQuestion.setQuestionUsername(seperated[i]);
                                    i++;
                                }
                            }
                            if (i < max && seperated[i].equals("QUESTIONUSERID")) {
                                //user id who created it
                                i++;
                                if (seperated[i].equals("POINTS")) {
                                    //do nothing
                                } else {
                                    tempQuestion.setOwnerID(seperated[i]);
                                    i++;
                                }
                            }
                            if (i < max && seperated[i].equals("POINTS")) {
                                //upvotes
                                i++;
                                if (seperated[i].equals("ENDORSED")) {
                                    //do nothing
                                } else {
                                    tempQuestion.setStudentRating(seperated[i]);
                                    i++;
                                }
                            }
                            if (i < max && seperated[i].equals("ENDORSED")) {
                                //if it is endorsed or not
                                i++;
                                if (seperated[i].equals("Yes")) {
                                    //this question is endorsed
                                    tempQuestion.setEndorsed(true);
                                    i++;
                                } else if (seperated[i].equals("No")) {
                                    i++;
                                } else {
                                    //do nothing
                                }
                            }
                            if (i < max && seperated[i].equals("CREATION")) {
                                //timestamp
                                i++;
                                String time = "";
                                while (i < max && !(seperated[i].equals("QUESTIONID"))) {
                                    time = time + seperated[i] + " ";
                                    i++;
                                }
                                tempQuestion.setCreationTime(time);

                            }
                            if (i < max && seperated[i].equals("QUESTIONID")) {
                                //ID for the question
                                i++;
                                tempQuestion.setQuestionID(seperated[i]);
                                i++;
                            }
                            if (i < max && seperated[i].equals("NEWREPLY")) {
                                //Get all of the replies
                                Reply tempR = new Reply();
                                i++;
                                while (i < max && !(seperated[i].equals("NEWREPLY")) && !(seperated[i].equals("NEWTOPIC")) && !(seperated[i].equals("NEWQUESTION"))) {
                                    //Build a new reply
                                    if (i < max && seperated[i].equals("REPLYTXT")) {
                                        i++;
                                        String reply = "";
                                        while (i < max && !(seperated[i].equals("REPLYUSER"))) {
                                            reply = reply + seperated[i] + " ";
                                            i++;
                                        }
                                        tempR.setReply(reply);

                                    }
                                    if (i < max && seperated[i].equals("REPLYUSER")) {
                                        //username of author
                                        i++;
                                        if (seperated[i].equals("REPLYUSERID")) {
                                            //do nothing
                                        } else {
                                            tempR.setReplyUsername(seperated[i]);
                                            i++;
                                        }

                                    }
                                    if (i < max && seperated[i].equals("REPLYUSERID")) {
                                        //id of user
                                        i++;
                                        if (seperated[i].equals("POINTS")) {
                                            //do nothing
                                        } else {
                                            tempR.setReplyUserID(seperated[i]);
                                            i++;
                                        }
                                    }
                                    if (i < max && seperated[i].equals("POINTS")) {
                                        i++;
                                        if (seperated[i].equals("ENDORSED")) {
                                            //do nothing
                                        } else {
                                            tempR.setReplyRating(seperated[i]);
                                            i++;
                                        }
                                    }
                                    if (i < max && seperated[i].equals("ENDORSED")) {
                                        i++;
                                        if (seperated[i].equals("Yes")) {
                                            tempR.setReplyEndorsed(true);
                                            i++;
                                        } else if (seperated[i].equals("No")) {
                                            i++;
                                        } else {
                                            //do nothing
                                        }
                                    }
                                    if (i < max && seperated[i].equals("CREATION")) {
                                        //timestamp
                                        i++;
                                        String time = "";
                                        while (i < max && !(seperated[i].equals("PARENT"))) {
                                            time = time + seperated[i] + " ";
                                            i++;
                                        }
                                        tempR.setReplyTimestamp(time);
                                    }
                                    if (i < max && seperated[i].equals("PARENT")) {
                                        //timestamp
                                        i++;
                                        String replyParent = seperated[i];
                                        i++;
                                        if (replyParent.equals("0")) {
                                            //this is not a reply but a place holder
                                            replyParent = null;
                                        }
                                        tempR.setReplyIDParent(replyParent);
                                    }
                                    if (i < max && seperated[i].equals("REPLYID")) {
                                        i++;
                                        String replyID = seperated[i];
                                        i++;
                                        tempR.setReplyID(replyID);
                                    }

                                }
                                //NEWREPLY means the start of a new reply within this question, add to the question's array list
                                replies.add(tempR);
                            }
                        }
                        q.add(tempQuestion);
                    }

                }
                topics.add(tempTopic);
            }
        }
        return topics;
    }

    /**
     * Used during login to parse through the information return by volley
     * about the user who logged in
     *
     * @param phpResponse
     * @return current user
     */
    public static User parseUserVolley(String phpResponse) {

        String[] seperated = phpResponse.split(":");

        if (seperated[1].contains("true")) {
            //concat strings to make it so that the array is properly read from the php response
            String reset = seperated[2];
            reset = reset.substring(1, reset.indexOf(",") - 1);
            String unique_id = seperated[3];
            unique_id = unique_id.substring(1, unique_id.indexOf(",") - 1);
            String roleID = seperated[4];
            roleID = roleID.substring(1, roleID.indexOf(",") - 1);
            String usern = seperated[5];
            usern = usern.substring(1, usern.indexOf(",") - 1);
            String first = seperated[6];
            first = first.substring(1, first.indexOf(",") - 1);
            String last = seperated[7];
            last = last.substring(1, last.indexOf(",") - 1);
            String class_ids = seperated[8];
            ArrayList<Classes> classes = new ArrayList<Classes>();
            class_ids = class_ids.substring(1, class_ids.indexOf("}"));
            Scanner s = new Scanner(class_ids);
            s.useDelimiter(",");
            while (s.hasNext()) {
                String current = s.next();
                current= current.trim();
                if(current.isEmpty()){
                    break;
                }
                String id= current.substring(0,current.indexOf(" "));
                if (id.equals("0")) {
                    //do nothing, this is not a class, just a place holder
                } else {
                    Classes c = new Classes(current.substring(current.indexOf(" ")+1), id);
                    System.out.println(c.getTitle()+" "+ c.getClassID());
                    classes.add(c);
                }
            }
            User temp = new User(reset, unique_id, roleID, usern, first, last, classes, true);
            return temp;
        }
        return null;
    }

    /**
     * @param timestamp the given timestamp that is in form 17:32:09 09/23/2017
     * @return the time stamp that is no longer in military hours
     */
    public static String parseTimeStamp(String timestamp) {
        String hours = timestamp.substring(timestamp.indexOf(":") - 2);
        hours = hours.substring(0, 5);
        if (hours.substring(0, 2).equals("12")) {
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("13")) {
            hours = "1" + hours.substring(2, 5);
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("14")) {
            hours = "2" + hours.substring(2, 5);
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("15")) {
            hours = "3" + hours.substring(2, 5);
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("16")) {
            hours = "4" + hours.substring(2, 5);
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("17")) {
            hours = "5" + hours.substring(2, 5);
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("18")) {
            hours = "6" + hours.substring(2, 5);
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("19")) {
            hours = "7" + hours.substring(2, 5);
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("20")) {
            hours = "8" + hours.substring(2, 5);
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("21")) {
            hours = "9" + hours.substring(2, 5);
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("22")) {
            hours = "10" + hours.substring(2, 5);
            hours = hours + " pm";
        } else if (hours.substring(0, 2).equals("23")) {
            hours = "11" + hours.substring(2, 5);
            hours = hours + " pm";
        } else {
            hours = hours + " am";
        }
        String day = timestamp.substring(5, 10);
        day = day.replaceAll("-", "/");
        String timeStamp = hours + " on " + day;
        return timeStamp;
    }

    public static ArrayList<Question> parseQuestions(String questions, Topics parent) {
        ArrayList<Question> question = new ArrayList<>();
        String[] seperated = questions.split(" ");
        int max = seperated.length;
        int i = 0;
        //The string can contain multiple parts to indicate when we start reading new information
        while (i < max) {
            if (i < max && seperated[i].equals("NEWQUESTION")) {
                //NEWQUESTION means the start of the new question within this topic, add to array list
                Question tempQuestion = new Question();
                i++;
                //cannot be a new topic or new question starting (maybe need to add in new reply too)?
                while (i < max && !(seperated[i].equals("NEWQUESTION"))) {
                    //Add new question to the array list for the topic
                    if (i < max && seperated[i].equals("QUESTIONTITLE")) {
                        //header for question
                        i++;
                        String title = "";
                        while (i < max && !(seperated[i].equals("QUESTIONDESCRIPTION"))) {
                            title = title + seperated[i] + " ";
                            i++;
                        }
                        tempQuestion.setQuestionTitle(title);

                    }
                    if (i < max && seperated[i].equals("QUESTIONDESCRIPTION")) {
                        //question
                        i++;
                        String desc = "";
                        while (i < max && !(seperated[i].equals("QUESTIONUSER"))) {
                            desc = desc + seperated[i] + " ";
                            i++;
                        }
                        tempQuestion.setQuestionDescription(desc);

                    }
                    if (i < max && seperated[i].equals("QUESTIONUSER")) {
                        //username who created it
                        i++;
                        if (seperated[i].equals("QUESTIONUSERID")) {
                            //do nothing
                        } else {
                            tempQuestion.setQuestionUsername(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("QUESTIONUSERID")) {
                        //user id who created it
                        i++;
                        if (seperated[i].equals("POINTS")) {
                            //do nothing
                        } else {
                            tempQuestion.setOwnerID(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("POINTS")) {
                        //upvotes
                        i++;
                        if (seperated[i].equals("ENDORSED")) {
                            //do nothing
                        } else {
                            tempQuestion.setStudentRating(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("ENDORSED")) {
                        //if it is endorsed or not
                        i++;
                        if (seperated[i].equals("Yes")) {
                            //this question is endorsed
                            tempQuestion.setEndorsed(true);
                            i++;
                        } else if (seperated[i].equals("No")) {
                            i++;
                        } else {
                            //do nothing
                        }
                    }
                    if (i < max && seperated[i].equals("CREATION")) {
                        //timestamp
                        i++;
                        String time = "";
                        while (i < max && !(seperated[i].equals("QUESTIONID"))) {
                            time = time + seperated[i] + " ";
                            i++;
                        }
                        tempQuestion.setCreationTime(time);

                    }
                    if (i < max && seperated[i].equals("QUESTIONID")) {
                        //ID for the question
                        i++;
                        tempQuestion.setQuestionID(seperated[i]);
                        i++;
                    }
                    tempQuestion.setParent(parent);
                    question.add(tempQuestion);
                }
            }
        }
        parent.setQuestions(question);
        return question;
    }
    public static ArrayList<Question> parseQuestions(String questions) {
        ArrayList<Question> question = new ArrayList<>();
        String[] seperated = questions.split(" ");
        int max = seperated.length;
        int i = 0;
        //The string can contain multiple parts to indicate when we start reading new information
        while (i < max) {
            if (i < max && seperated[i].equals("NEWQUESTION")) {
                //NEWQUESTION means the start of the new question within this topic, add to array list
                Question tempQuestion = new Question();
                i++;
                //cannot be a new topic or new question starting (maybe need to add in new reply too)?
                while (i < max && !(seperated[i].equals("NEWQUESTION"))) {
                    //Add new question to the array list for the topic
                    if (i < max && seperated[i].equals("QUESTIONTITLE")) {
                        //header for question
                        i++;
                        String title = "";
                        while (i < max && !(seperated[i].equals("QUESTIONDESCRIPTION"))) {
                            title = title + seperated[i] + " ";
                            i++;
                        }
                        tempQuestion.setQuestionTitle(title);

                    }
                    if (i < max && seperated[i].equals("QUESTIONDESCRIPTION")) {
                        //question
                        i++;
                        String desc = "";
                        while (i < max && !(seperated[i].equals("QUESTIONUSER"))) {
                            desc = desc + seperated[i] + " ";
                            i++;
                        }
                        tempQuestion.setQuestionDescription(desc);

                    }
                    if (i < max && seperated[i].equals("QUESTIONUSER")) {
                        //username who created it
                        i++;
                        if (seperated[i].equals("QUESTIONUSERID")) {
                            //do nothing
                        } else {
                            tempQuestion.setQuestionUsername(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("QUESTIONUSERID")) {
                        //user id who created it
                        i++;
                        if (seperated[i].equals("POINTS")) {
                            //do nothing
                        } else {
                            tempQuestion.setOwnerID(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("POINTS")) {
                        //upvotes
                        i++;
                        if (seperated[i].equals("ENDORSED")) {
                            //do nothing
                        } else {
                            tempQuestion.setStudentRating(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("ENDORSED")) {
                        //if it is endorsed or not
                        i++;
                        if (seperated[i].equals("Yes")) {
                            //this question is endorsed
                            tempQuestion.setEndorsed(true);
                            i++;
                        } else if (seperated[i].equals("No")) {
                            i++;
                        } else {
                            //do nothing
                        }
                    }
                    if (i < max && seperated[i].equals("CREATION")) {
                        //timestamp
                        i++;
                        String time = "";
                        while (i < max && !(seperated[i].equals("QUESTIONID"))) {
                            time = time + seperated[i] + " ";
                            i++;
                        }
                        tempQuestion.setCreationTime(time);

                    }
                    if (i < max && seperated[i].equals("QUESTIONID")) {
                        //ID for the question
                        i++;
                        tempQuestion.setQuestionID(seperated[i]);
                        i++;
                    }
                    question.add(tempQuestion);
                }
            }
        }
        return question;
    }
    public static ArrayList<Reply> parseReplies(String replies, Question parent) {
        ArrayList<Reply> reply = new ArrayList<>();
        String[] seperated = replies.split(" ");
        int max = seperated.length;
        int i = 0;
        //The string can contain multiple parts to indicate when we start reading new information
        while (i < max) {
            if (i < max && seperated[i].equals("NEWREPLY")) {
                //Get all of the replies
                Reply tempR = new Reply();
                i++;
                while (i < max && !(seperated[i].equals("NEWREPLY"))) {
                    //Build a new reply
                    if (i < max && seperated[i].equals("REPLYTXT")) {
                        i++;
                        String text = "";
                        while (i < max && !(seperated[i].equals("REPLYUSER"))) {
                            text = text + seperated[i] + " ";
                            i++;
                        }
                        tempR.setReply(text);

                    }
                    if (i < max && seperated[i].equals("REPLYUSER")) {
                        //username of author
                        i++;
                        if (seperated[i].equals("REPLYUSERID")) {
                            //do nothing
                        } else {
                            tempR.setReplyUsername(seperated[i]);
                            i++;
                        }

                    }
                    if (i < max && seperated[i].equals("REPLYUSERID")) {
                        //id of user
                        i++;
                        if (seperated[i].equals("POINTS")) {
                            //do nothing
                        } else {
                            tempR.setReplyUserID(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("POINTS")) {
                        i++;
                        if (seperated[i].equals("ENDORSED")) {
                            //do nothing
                        } else {
                            tempR.setReplyRating(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("ENDORSED")) {
                        i++;
                        if (seperated[i].equals("Yes")) {
                            tempR.setReplyEndorsed(true);
                            i++;
                        } else if (seperated[i].equals("No")) {
                            i++;
                        } else {
                            //do nothing
                        }
                    }
                    if (i < max && seperated[i].equals("CREATION")) {
                        //timestamp
                        i++;
                        String time = "";
                        while (i < max && !(seperated[i].equals("PARENT"))) {
                            time = time + seperated[i] + " ";
                            i++;
                        }
                        tempR.setReplyTimestamp(time);
                    }
                    if (i < max && seperated[i].equals("PARENT")) {
                        //timestamp
                        i++;
                        String replyParent = seperated[i];
                        i++;
                        if (replyParent.equals("0")) {
                            //this is not a reply but a place holder
                            replyParent = null;
                        }
                        tempR.setReplyIDParent(replyParent);
                    }
                    if (i < max && seperated[i].equals("REPLYID")) {
                        i++;
                        String replyID = seperated[i];
                        i++;
                        tempR.setReplyID(replyID);
                    }
                    tempR.setReplyQParent(parent);
                    reply.add(tempR);
                }

            }
        }
        return reply;
    }

    public static ArrayList<Reply> parseReplies(String replies, Reply parent) {
        ArrayList<Reply> reply = new ArrayList<>();
        String[] seperated = replies.split(" ");
        int max = seperated.length;
        int i = 0;
        //The string can contain multiple parts to indicate when we start reading new information
        while (i < max) {
            if (i < max && seperated[i].equals("NEWREPLY")) {
                //Get all of the replies
                Reply tempR = new Reply();
                i++;
                while (i < max && !(seperated[i].equals("NEWREPLY"))) {
                    //Build a new reply
                    if (i < max && seperated[i].equals("REPLYTXT")) {
                        i++;
                        String text = "";
                        while (i < max && !(seperated[i].equals("REPLYUSER"))) {
                            text = text + seperated[i] + " ";
                            i++;
                        }
                        tempR.setReply(text);

                    }
                    if (i < max && seperated[i].equals("REPLYUSER")) {
                        //username of author
                        i++;
                        if (seperated[i].equals("REPLYUSERID")) {
                            //do nothing
                        } else {
                            tempR.setReplyUsername(seperated[i]);
                            i++;
                        }

                    }
                    if (i < max && seperated[i].equals("REPLYUSERID")) {
                        //id of user
                        i++;
                        if (seperated[i].equals("POINTS")) {
                            //do nothing
                        } else {
                            tempR.setReplyUserID(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("POINTS")) {
                        i++;
                        if (seperated[i].equals("ENDORSED")) {
                            //do nothing
                        } else {
                            tempR.setReplyRating(seperated[i]);
                            i++;
                        }
                    }
                    if (i < max && seperated[i].equals("ENDORSED")) {
                        i++;
                        if (seperated[i].equals("Yes")) {
                            tempR.setReplyEndorsed(true);
                            i++;
                        } else if (seperated[i].equals("No")) {
                            i++;
                        } else {
                            //do nothing
                        }
                    }
                    if (i < max && seperated[i].equals("CREATION")) {
                        //timestamp
                        i++;
                        String time = "";
                        while (i < max && !(seperated[i].equals("PARENT"))) {
                            time = time + seperated[i] + " ";
                            i++;
                        }
                        tempR.setReplyTimestamp(time);
                    }
                    if (i < max && seperated[i].equals("PARENT")) {
                        //timestamp
                        i++;
                        String replyParent = seperated[i];
                        i++;
                        if (replyParent.equals("0")) {
                            //this is not a reply but a place holder
                            replyParent = null;
                        }
                        tempR.setReplyIDParent(replyParent);
                    }
                    if (i < max && seperated[i].equals("REPLYID")) {
                        i++;
                        String replyID = seperated[i];
                        i++;
                        tempR.setReplyID(replyID);
                    }
                    reply.add(tempR);
                }
            }
        }
        System.out.print("Replies size " + reply.size());
        return reply;
    }
}