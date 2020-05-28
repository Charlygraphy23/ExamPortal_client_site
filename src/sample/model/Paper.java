package sample.model;

import java.util.ArrayList;
import java.util.List;

public class Paper {

    private int qId;
    private String question;
    private List<String> answars;

    public Paper() {
    }

    public Paper(String question, ArrayList<String> answars) {
        this.question = question;
        this.answars = answars;
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswars() {
        return answars;
    }

    public void setAnswars(List<String> answars) {
        this.answars = answars;
    }
}
