package Utils;

import java.util.ArrayList;

/**
 * Created by sae1 on 11/3/17.
 */

public class StringParse {
    public static ArrayList<Topics> parseTopicsVolley(String phpResponse){
            ArrayList<Topics> topics=new ArrayList<>();
            String[] seperated=phpResponse.split(" ");
            int max=seperated.length;
            int i=0;
            //The string can contain multiple parts to indicate when we start reading new information
            while(i<max) {
                if(i<max && seperated[i].equals("NEWTOPIC")) {
                    //NEWTOPIC indicates the start of a new topic, make a new topic object
                    Topics tempTopic= new Topics();
                    ArrayList<Question> q= new ArrayList<Question>();
                    tempTopic.set_questions(q);
                    i++;
                    while(i<max && !(seperated[i].equals("NEWTOPIC"))) {
                        if(i<max && seperated[i].equals("CREATETIME")){
                            i++;
                            String Time="";
                            while(!(seperated[i].equals("TOPICNAME")) && i<max){
                                //I'm not sure if we need to add a space here or not
                                Time=Time+" "+seperated[i];
                                i++;
                            }
                            tempTopic.set_time(Time);
                        }
                        if(i<max && seperated[i].equals("TOPICNAME")){
                            i++;
                            String Topic="";
                            while(i<max && !(seperated[i].equals("ID"))){
                                Topic=Topic+" "+seperated[i];
                                i++;
                            }
                            tempTopic.set_title(Topic);
                        }
                        if(i<max && seperated[i].equals("ID")){
                            //id of the topics
                            i++;
                            if(seperated[i].equals("DESCRIPTION")){
                                //do nothing
                            }
                            else {
                                tempTopic.set_ID(seperated[i]);
                                i++;
                            }
                        }
                        if(i<max && seperated[i].equals("DESCRIPTION")){
                            i++;
                            String Description="";
                            while(i<max && !(seperated[i].equals("NEWQUESTION")) && !(seperated[i].equals("NEWTOPIC"))){
                                Description=Description+" "+seperated[i];
                                i++;
                            }
                            tempTopic.set_description(Description);
                        }
                        //TODO: Figure out what this isn't working
                        if(i<max && seperated[i].equals("NEWQUESTION")) {
                            //NEWQUESTION means the start of the new question within this topic, add to array list
                            Question tempQuestion= new Question();
                            ArrayList<Reply> replies= new ArrayList<Reply>();
                            tempQuestion.setReplies(replies);
                            i++;
                            //cannot be a new topic or new question starting (maybe need to add in new reply too)?
                            while(i<max && !(seperated[i].equals("NEWTOPIC")) && !(seperated[i].equals("NEWQUESTION"))){
                                //Add new question to the array list for the topic
                                if(i<max && seperated[i].equals("QUESTIONTITLE")){
                                    //header for question
                                    i++;
                                    String title="";
                                    while(i<max && !(seperated[i].equals("QUESTIONDESCRIPTION"))){
                                        title=title+seperated[i]+ " ";
                                        i++;
                                    }
                                    tempQuestion.setQuestionTitle(title);

                                }
                                if(i<max && seperated[i].equals("QUESTIONDESCRIPTION")){
                                    //question
                                    i++;
                                    String desc="";
                                    while(i<max && !(seperated[i].equals("QUESTIONUSER"))){
                                        desc=desc+seperated[i]+ " ";
                                        i++;
                                    }
                                    tempQuestion.setQuestionDescription(desc);

                                }
                                if(i<max && seperated[i].equals("QUESTIONUSER")){
                                    //username who created it
                                    i++;
                                    if(seperated[i].equals("QUESTIONUSERID")) {
                                        //do nothing
                                    }
                                    else{
                                        tempQuestion.setQuestionUsername(seperated[i]);
                                        i++;
                                    }
                                }
                                if(i<max && seperated[i].equals("QUESTIONUSERID")){
                                    //user id who created it
                                    i++;
                                    if(seperated[i].equals("POINTS")){
                                        //do nothing
                                    }
                                    else {
                                        tempQuestion.setOwnerID(seperated[i]);
                                        i++;
                                    }
                                }
                                if(i<max && seperated[i].equals("POINTS")){
                                    //upvotes
                                    i++;
                                    if(seperated[i].equals("ENDORSED")){
                                        //do nothing
                                    }
                                    else {
                                        tempQuestion.setStudentRating(seperated[i]);
                                        i++;
                                    }
                                }
                                if(i<max && seperated[i].equals("ENDORSED")){
                                    //if it is endorsed or not
                                    i++;
                                    if(seperated[i].equals("Yes")){
                                        //this question is endorsed
                                        tempQuestion.setEndorsed(true);
                                        i++;
                                    }
                                    else if(seperated[i].equals("No")){
                                        i++;
                                    }
                                    else{
                                        //do nothing
                                    }
                                }
                                if(i<max && seperated[i].equals("CREATION")){
                                    //timestamp
                                    i++;
                                    String time="";
                                    while(i<max && !(seperated[i].equals("QUESTIONID"))){
                                        time=time+seperated[i]+ " ";
                                        i++;
                                    }
                                    tempQuestion.setCreationTime(time);

                                }
                                if(i<max && seperated[i].equals("QUESTIONID")){
                                    //ID for the question
                                    i++;
                                    tempQuestion.setQuestionID(seperated[i]);
                                    i++;
                                }
                                if(i<max && seperated[i].equals("NEWREPLY")) {
                                    //Get all of the replies
                                    Reply tempR=new Reply();
                                    i++;
                                    while(i<max && !(seperated[i].equals("NEWREPLY")) && !(seperated[i].equals("NEWTOPIC")) && !(seperated[i].equals("NEWQUESTION"))){
                                        //Build a new reply
                                        if(i<max && seperated[i].equals("REPLYTXT")){
                                            i++;
                                            String reply="";
                                            while(i<max && !(seperated[i].equals("REPLYUSER"))){
                                                reply=reply+seperated[i]+" ";
                                                i++;
                                            }
                                            tempR.setReply(reply);

                                        }
                                        if(i<max && seperated[i].equals("REPLYUSER")){
                                            //username of author
                                            i++;
                                            if(seperated[i].equals("REPLYUSERID")){
                                                //do nothing
                                            }
                                            else {
                                                tempR.setReplyUsername(seperated[i]);
                                                i++;
                                            }

                                        }
                                        if(i<max && seperated[i].equals("REPLYUSERID")){
                                            //id of user
                                            i++;
                                            if(seperated[i].equals("POINTS")) {
                                                //do nothing
                                            }
                                            else{
                                                tempR.setReplyUserID(seperated[i]);
                                                i++;
                                            }
                                        }
                                        if(i<max && seperated[i].equals("POINTS")){
                                            i++;
                                            if(seperated[i].equals("ENDORSED")){
                                                //do nothing
                                            }
                                            else {
                                                tempR.setReplyRating(seperated[i]);
                                                i++;
                                            }
                                        }
                                        if(i<max && seperated[i].equals("ENDORSED")){
                                            i++;
                                            if(seperated[i].equals("Yes")){
                                                tempR.setReplyEndorsed(true);
                                                i++;
                                            }
                                            else if(seperated[i].equals("No")){
                                                i++;
                                            }
                                            else{
                                                //do nothing
                                            }
                                        }
                                        if(i<max && seperated[i].equals("CREATION")){
                                            //timestamp
                                            i++;
                                            String time="";
                                            while(i<max && !(seperated[i].equals("PARENT"))){
                                                time=time+seperated[i]+ " ";
                                                i++;
                                            }
                                            tempR.setReplyTimestamp(time);
                                        }
                                        if(i<max && seperated[i].equals("PARENT")){
                                            //timestamp
                                            i++;
                                            String replyParent=seperated[i];
                                            i++;
                                            if(replyParent.equals("0")){
                                                //this is not a reply but a place holder
                                                replyParent=null;
                                            }
                                            tempR.setReplyParent(replyParent);
                                        }
                                        if(i<max && seperated[i].equals("REPLYID")) {
                                            i++;
                                            String replyID=seperated[i];
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

}
