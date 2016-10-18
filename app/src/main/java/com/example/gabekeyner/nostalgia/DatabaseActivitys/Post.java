package com.example.gabekeyner.nostalgia.DatabaseActivitys;

/**
 * Created by GabeKeyner on 10/17/2016.
 */

public class Post {

    public String imageURL;
    public String title;

    public Post(String imageURL, String title) {
        this.imageURL = imageURL;
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }


    public static class Comment {

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
    public static class Group {

        public String groupTitle;
        public int numPeople;

        @Override
        public String toString() {
            return "Group{" +
                    "groupTitle='" + groupTitle + '\'' +
                    ", numPeople=" + numPeople +
                    '}';
        }
    }
}
