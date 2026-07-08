package gr.aueb.cf;

import gr.aueb.cf.view.ConsoleView;
import gr.aueb.cf.controller.BankController;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        
        BankController controller = new BankController(view);
        
        controller.start();
    }
}