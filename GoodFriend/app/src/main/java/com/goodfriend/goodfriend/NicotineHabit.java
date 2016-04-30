package com.goodfriend.goodfriend;

import java.util.Arrays;

public class NicotineHabit extends Habit{

    //days / relapse percent with drug aid
    private int[][] aidedData = {{0,7},{2,18},{4,29},{6,32},{8,38},{10,41},{12,43},{20,50},{30,58},
            {40,62},{45,64},{60,66},{90,73},{120,74},{180,75},{240,76},{300,79},{360,83}};

    //days / relapse percent with no drug aids
    private int[][] unaidedData = {{0,20},{2,26},{4,28},{6,36},{8,40},{10,48},{12,52},{20,61},{30,65},
            {40,71},{45,74},{60,77},{90,81},{120,83},{180,85},{240,86},{300,87},{360,90}};

    private int[] milestones = {0,2,4,6,12,30,60,90,300};

    private static boolean aid;

    private NicotineHabit(boolean aid){
        this.aid = aid;
    }


    public int getPercent(int days){
        int upper = -1, lower = -1, i;

        if(aid) {
            if(days > 360)
                return aidedData[aidedData.length][1];

            for (i = 0; i < aidedData.length; i++){
                if(aidedData[i][0] >= days){
                    upper = i;
                    break;
                }
            }
            for(int j = i-1; j >= 0 ; j--){
                if(aidedData[j][0] <= days){
                    lower = j;
                    break;
                }
            }

            if(upper == lower)
                return aidedData[upper][1];

            System.out.println(upper + " " + lower);
            int slope = (aidedData[upper][1] - aidedData[lower][1])/
                    (aidedData[upper][0] - aidedData[lower][0]);

            int percent = slope*days - slope*aidedData[lower][0] + aidedData[lower][1];

            return percent;
        }

        else{
            if(days > 360)
                return unaidedData[unaidedData.length][1];

            for (i = 0; i < aidedData.length; i++){
                if(unaidedData[i][0] >= days){
                    upper = i;
                    break;
                }
            }
            for(int j = i-1; j >= 0 ; j--){
                if(unaidedData[j][0] <= days){
                    lower = j;
                    break;
                }
            }

            if(upper == lower)
                return unaidedData[upper][1];

            int slope = (unaidedData[upper][1] - unaidedData[lower][1])/
                    (unaidedData[upper][0] - unaidedData[lower][0]);

            int percent = slope*days - slope*unaidedData[lower][0] + unaidedData[lower][1];

            return percent;
        }
    }

    public int getExpectedStress(int days){
        boolean isMilestone = false;
        int i, expectedStress = 0;
        for( i = 0; i < milestones.length; i++) {
            if (milestones[i] == days){
                isMilestone = true;
                break;
            }
        }
        if(isMilestone){
            if(aid){
                for(int q = 0; q < aidedData.length; q++){
                    if(aidedData[q][0] == milestones[i]){
                        expectedStress += (aidedData[q+1][1] - aidedData[q][1]);
                        break;
                    }
                }
            }
            else{
                for(int q = 0; q < unaidedData.length; q++){
                    if(unaidedData[q][0] == milestones[i]){
                        expectedStress += (unaidedData[q+1][1] - unaidedData[q][1]);
                        break;
                    }
                }
            }
        }
        else{
            int percent = getPercent(days);
            int prevPercent = 0;
            for(int j = days-1; j >= 0; j--){
                int pos = Arrays.binarySearch(milestones, j);
                if(pos > -1){
                    if(aid){
                        for(i = 0; i < aidedData.length; i++){
                            if(aidedData[i][0] == milestones[pos]){
                                prevPercent = aidedData[i][1];
                                break;
                            }
                        }
                        break;
                    }
                    else{
                        for(i = 0; i < unaidedData.length; i++){
                            if(unaidedData[i][0] == milestones[pos]){
                                prevPercent = unaidedData[i][1];
                                break;
                            }
                        }
                        break;
                    }
                }
            }

            expectedStress += (percent - prevPercent);
        }

        if(expectedStress > 10)
            expectedStress = 10;

        return expectedStress;

    }

    @Override
    public void recalculateState(int days, int stress) {
        int expected = getExpectedStress(days);
        //UserState cur = getState();

        if(stress >= 8){
            setState(UserState.HIGH_RISK);
        }

        else if(stress > expected){
            setState(UserState.MED_RISK);
        }
        else{
            setState(UserState.NORMAL);
        }
    }
}