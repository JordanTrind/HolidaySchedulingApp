package mypackage;

import javax.swing.*;
import java.util.Arrays;

public class passwordHandler {
    public String passwordReqCheck(char[] firstPass, char[] secondPass) {
        String status;
        Boolean digitFlag = false;
        Boolean upperFlag = false;
        Boolean lowerFlag = false;
        if ((Arrays.equals(firstPass, secondPass)) && (firstPass.length != 0 || secondPass.length != 0)) {
            for (int i = 0; i < firstPass.length; i++) {
                if (Character.isDigit(firstPass[i])) {
                    digitFlag = true;
                } else if(Character.isUpperCase(firstPass[i])) {
                    upperFlag = true;
                } else if(Character.isLowerCase(firstPass[i])) {
                    lowerFlag = true;
                }
            }
            if (digitFlag && upperFlag && lowerFlag) {
                status = "success";
            } else {
                status = "passwordreqerror";
            }
        } else {
            status = "passwordmatcherror";
        }
        return status;
    }
}
