package com.example.SpringEmailDemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailModel {

    String to;

    String subJect;

    String body;

    List<String> cc = new ArrayList<>();

    List<String> bcc = new ArrayList<>();

    List<File> file = new ArrayList<>();

    public MailModel(String to, String subJect, String body) {
        super();
        this.to = to;
        this.subJect = subJect;
        this.body = body;
    }
}
