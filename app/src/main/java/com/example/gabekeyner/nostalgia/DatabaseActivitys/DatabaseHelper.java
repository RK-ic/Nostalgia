package com.example.gabekeyner.nostalgia.DatabaseActivitys;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GabeKeyner on 10/12/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "NostalgiaDataBase";
    public static final String POST_TABLE_NAME = "posts";
    public static final String GROUPS_TABLE_NAME = "groups";
    public static final String COMMENTS_TABLE_NAME = "comments";

    public static final String[] POSTS_COLUMNS = new String[]{"imageURL", "title"};
    public static final String[] GROUPS_COLUMNS = new String[]{"groupTitle", "numPeople"};
    public static final String[] COMMENTS_COLUMNS = new String[]{"groupName", "userName", "comment"};

    public static final String CREATE_POST_TABLE =
            "CREATE TABLE IF NOT EXISTS " + POST_TABLE_NAME + " (" +
                    "imageURL TEXT," +
                    "title TEXT," +
                    ")";

    public static final String CREATE_GROUPS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + GROUPS_TABLE_NAME + " (" +
                    "groupTitle TEXT," +
                    "numPeople INTEGER," +
                    ")";

    public static final String CREATE_COMMENTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + COMMENTS_TABLE_NAME + " (" +
                    "groupName TEXT," +
                    "userName TEXT," +
                    "comment TEXT," +
                    ")";

    public static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);

        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(POST_TABLE_NAME);
        db.execSQL(GROUPS_TABLE_NAME);
        db.execSQL(COMMENTS_TABLE_NAME);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GROUPS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COMMENTS_TABLE_NAME);

        this.onCreate(db);
    }


    //Post Picture Section
    public void addPost(ContentValues CV) {
        Cursor c = null;
        String query = "select * from " + POST_TABLE_NAME +
                "WHERE imageURL = \"" + CV.getAsString("imageURL") + "\" " +
                "AND title = \"" + CV.getAsString("title") + "\"";
        c = getReadableDatabase().rawQuery(query, null);
        if (c.moveToFirst()) {
            return;
        }
        getWritableDatabase().insert(POST_TABLE_NAME, null, CV);
    }

    public Post[] getAllPosts() {
        Cursor cursor = getReadableDatabase().query(
                POST_TABLE_NAME,
                POSTS_COLUMNS,
                null,
                null,
                null,
                null,
                null
        );
        Post[] posts = new Post[cursor.getCount()];
        for (int i = 0; i < posts.length; i++) {
            cursor.moveToPosition(i);
            posts[i] = new Post();
            posts[i].imageURL = cursor.getString(cursor.getColumnIndex(POSTS_COLUMNS[0]));
            posts[i].title = cursor.getString(cursor.getColumnIndex(POSTS_COLUMNS[1]));
        }
        return posts;
    }

    public void deleteAllPosts() {
        getWritableDatabase().delete(POST_TABLE_NAME, null, null);
    }

    //Group Section
    public void addGroup(ContentValues CV) {
        Cursor c = null;
        String query = "select * from " + GROUPS_TABLE_NAME +
                "WHERE groupTitle = \"" + CV.getAsString("groupTitle") + "\" " +
                "AND numPeople = \"" + CV.getAsString("numPeople") + "\"";
        c = getReadableDatabase().rawQuery(query, null);
        if (c.moveToFirst()) {
            return;
        }
        getWritableDatabase().insert(GROUPS_TABLE_NAME, null, CV);
    }
    public Group[] getAllGroup() {
        Cursor cursor = getReadableDatabase().query(
                GROUPS_TABLE_NAME,
                GROUPS_COLUMNS,
                null,
                null,
                null,
                null,
                null
        );
       Group[] groups = new Group[cursor.getCount()];
        for (int i = 0; i <groups.length; i++) {
            cursor.moveToPosition(i);
            groups[i]= new Group();
            groups[i].groupTitle = cursor.getString(cursor.getColumnIndex(GROUPS_COLUMNS[0]));
            groups[i].numPeople = cursor.getInt(cursor.getColumnIndex(GROUPS_COLUMNS[1]));

        }
        return groups;
    }

    public void deleteAllGroups (){
        getWritableDatabase().delete(GROUPS_TABLE_NAME,null,null);

    }


    //COMMENT SECTION

    public void deleteAllComments() {
        getWritableDatabase().delete(COMMENTS_TABLE_NAME, null, null);
    }

    public void addComment(ContentValues CV) {
        Cursor c = null;
        String query = "select * from " + COMMENTS_TABLE_NAME +
                " WHERE groupName = \"" + CV.getAsString("groupName") + "\" " +
                " AND userName = \"" + CV.getAsString("userName") + "\" " +
                " AND comment = \"" + CV.getAsString("comment") + "\" ";
        c = getReadableDatabase().rawQuery(query, null);
        if (c.moveToFirst()) {
            return;
        }
        getWritableDatabase().insert(COMMENTS_TABLE_NAME, null, CV);
    }

    public Post.Comment[] getCommentForPost(String groupName) {
        String selection = "groupName = \"" + groupName + "\"";

        Cursor cursor = getReadableDatabase().query(
                COMMENTS_TABLE_NAME,
                COMMENTS_COLUMNS,
                selection,
                null,
                null,
                null,
                null
        );
        Post.Comment[] comments = new Post.Comment[cursor.getCount()];

        for (int i = 0; i < comments.length; i++) {
            cursor.moveToPosition(i);
            comments[i] = new Post.Comment();
            comments[i].groupName = cursor.getString(cursor.getColumnIndex("groupName"));
            comments[i].userName = cursor.getString(cursor.getColumnIndex("userName"));
            comments[i].comment = cursor.getString(cursor.getColumnIndex("comment"));

        }
        return comments;

    }
    public int getPostsTableSize(){
        return getAllPosts().length;
    }

}
