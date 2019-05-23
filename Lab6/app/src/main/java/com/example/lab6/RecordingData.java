package com.example.lab6;

import java.io.File;

public class RecordingData {

    String title;
    String nameAndSurname;
    String date;
    File wavFile;

    public RecordingData(String title, String name, String date, File wav) {
        this.title = title;
        this.nameAndSurname = name;
        this.date = date;
        this.wavFile=wav;
    }

}
