package com.fabassignment.dicos.model.dictionary;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by benifabrice on 5/13/17.
 */

public class Definition implements Serializable, Comparable<Definition> {

    private String definition;
    private String permalink;
    @SerializedName("thumbs_up")
    private int thumbsUp;
    private String author;
    private String word;
    private int defid;
    @SerializedName("current_vote")
    private String currentVote;
    private String example;
    @SerializedName("thumbs_down")
    private int thumbsDown;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDefid(int defid) {
        this.defid = defid;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getDefid() {
        return defid;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public String getAuthor() {
        return author;
    }

    public String getCurrentVote() {
        return currentVote;
    }

    public String getDefinition() {
        return definition;
    }

    public String getExample() {
        return example;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getWord() {
        return word;
    }

    @Override
    public int compareTo(@NonNull Definition definition) {
        return 0;
    }

    public static final Comparator<Definition> ThumbsUpComparator = new Comparator<Definition>() {
        @Override
        public int compare(Definition definition, Definition t1) {
            return t1.getThumbsUp() - definition.getThumbsUp() ;
        }
    };

    public static final Comparator<Definition> ThumbsDownComparator = new Comparator<Definition>() {
        @Override
        public int compare(Definition definition, Definition t1) {
            return t1.getThumbsDown() - definition.getThumbsDown();
        }
    };
}
