package Games;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;


import java.util.*;

public class RockPaperScissors {

    public static String[] options = {"Rock", "Paper", "Scissors"};

    public static int userWon(int computer, int user) {
        if (user == 0) {
            if (computer == 1) {
                return -1;
            } else if (computer == 2) {
                return 1;
            } else {
                return 0;
            }
        } else if (user == 1) { //Paper
            if (computer == 2) {
                return -1;
            } else if (computer == 1) {
                return 1;
            } else {
                return 0;
            }
        } else { //Scissors
            if (computer == 0) {
                return -1;
            } else if (computer == 1) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        int compPick = random.nextInt(options.length);
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose between \n" +
                "(1) for Rock \n" +
                "(2) for Paper \n" +
                "(3) for Scissors");
        int userPick = sc.nextInt() - 1;
        if (userWon(compPick, userPick) == 1) {
            System.out.println("The computer picked " + options[compPick] +
                    " and you picked " + options[userPick] +
                    ", You won! :)");
        } else if (userWon(compPick, userPick) == -1) {
            System.out.println("The computer picked " + options[compPick] +
                    " and you picked " + options[userPick] +
                    ", You lost :(");
        } else {
            System.out.println("You and the computer both picked " + options[userPick] +
                    ", It's a tie");
        }
    }
}
