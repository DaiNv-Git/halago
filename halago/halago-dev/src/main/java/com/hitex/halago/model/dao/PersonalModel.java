package com.hitex.halago.model.dao;

import com.hitex.halago.model.Personal;

import java.util.List;

public class PersonalModel extends Personal {
    private int score;
    private String nameRank;
    private int scoreRank;

    public List<String> getHistoryProject() {
        return historyProject;
    }

    public void setHistoryProject(List<String> historyProject) {
        this.historyProject = historyProject;
    }

    private List<String> historyProject;



    public int getScoreRank() {
        return scoreRank;
    }

    public void setScoreRank(int scoreRank) {
        this.scoreRank = scoreRank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNameRank() {
        return nameRank;
    }

    public void setNameRank(String nameRank) {
        this.nameRank = nameRank;
    }
}
