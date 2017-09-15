/** This file is to help group members access the data by giving them common SQL queries 
Written by Stephanie Engelhardt on 09/15/2017 **/

/* This query selects all of the threads and their replies and joins them together
to allow access to both of them. */
SELECT thread.*, reply.* FROM threads thread
LEFT OUTER JOIN replies reply ON thread.ID=reply.thread_id;
