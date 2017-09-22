/** This file is to help group members access the data by giving them common SQL queries 
Written by Stephanie Engelhardt on 09/15/2017 **/

/* This query selects all of the threads and their replies and joins them together
to allow access to both of them. Orders them in order of most upped thread, (then by most upped reply)*/
SELECT thread.*, reply.* FROM threads thread
LEFT OUTER JOIN replies reply ON thread.ID=reply.thread_id
ORDER BY thread.points, reply.points;
/* A condition can also be added here as "WHERE class_id= current class id" but we need to figure out 
how the systems will interact first before i can call that data. */

/* This query selects all of the flagged posts from the same university as the admin */
SELECT * FROM replies reply
	WHERE reply.flagged=1
    /*I may need to change this from the sys_user, I'm not sure yet how we will access the user's ID*/
	AND @sys_user.ID = (SELECT university_id FROM users
		WHERE users.ID= reply.owner_id);

/*This query selects all of the threads (aka questions) that a user has created*/
SELECT * FROM threads
WHERE threads.owner_id= @sys_user.ID;

/* Shows all of the topics from the class that the user is in */
SELECT * FROM topics
WHERE topics.class_id= @sys_user.class_id;

/* This is the user login information:
it will have to be written dyanmically, so you will save what the user inputs and then compare it.
sql= 'SELECT * FROM users WHERE users.username=' + input of username 'AND users.pass=' input of password ';';
If the user does NOT put in the correct information, it will return back with no results found. If
it does match, then the users information will be returned */

