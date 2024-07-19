package org.program.trivia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {
    private String type;
    private String difficulty;
    private String category;
    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(List<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String unescapedQuestion = StringEscapeUtils.unescapeHtml4(this.question);
        sb.append("Question -- > ").append(unescapedQuestion).append("\n");
        List<String> answers = new ArrayList<>();
        boolean b = answers.addAll(incorrect_answers);
        if (b){
            answers.add(correct_answer);
            Collections.shuffle(answers);
        }
        int index = 1;
        for (String answer : answers){
            sb.append("\t").append((index ++)).append(". ").append(answer).append("\n");
        }
        return sb.toString();
    }
}
