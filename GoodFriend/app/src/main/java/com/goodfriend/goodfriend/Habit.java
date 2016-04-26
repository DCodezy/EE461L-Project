package com.goodfriend.goodfriend;

import java.util.List;


public abstract class Habit {

    public enum UserState {
        HIGH_RISK, MED_RISK, NORMAL
    }

    protected List relevantUserQuestions;
    protected List userAnswers;
    protected List pingQuestions;
    protected List<List> stateAdvice;

    public UserState currentState;

    public abstract void recalculateState(int days, int stress);

    public void setState(UserState s){ currentState = s;}

    public UserState getState(){
        return currentState;
    }

}
