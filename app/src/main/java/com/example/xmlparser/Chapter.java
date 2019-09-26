package com.example.xmlparser;

import androidx.annotation.NonNull;

public class Chapter {
    String verse;

    public Chapter(String verse) {
        this.verse = verse;
    }

    public String getVerse() {
        return verse;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "verse='" + verse + '\'' +
                '}';
    }
}
