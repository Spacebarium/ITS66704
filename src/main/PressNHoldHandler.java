package main;

public class PressNHoldHandler {
    final int pressNHoldCd = 10;
    int pressNHold;
    boolean firstPress = true;

    public PressNHoldHandler(){

    }

    public boolean canPressNHold(){
        if (firstPress){
            firstPress = false;
            return true;
        }
        if (pressNHold == pressNHoldCd) {
            return true;
        }
        else{
            pressNHold ++;
            return false;
        }
    }

    public boolean cannotPressNHold(){
        if (firstPress){
            firstPress = false;
            return true;
        }
        else
            return  false;
    }
}
