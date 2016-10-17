package com.example.gabekeyner.nostalgia.DatabaseActivitys;

/**
 * Created by GabeKeyner on 10/17/2016.
 */

public class Comment {

        public String groupName;
        public String userName;
        public String comment;


        @Override
        public String toString() {
                return "Comment{" +
                        "groupName='" + groupName + '\'' +
                        ", userName='" + userName + '\'' +
                        ", comment='" + comment + '\'' +
                        '}';
        }
}
