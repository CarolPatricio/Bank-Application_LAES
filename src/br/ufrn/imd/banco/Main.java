package br.ufrn.imd.banco;

import br.ufrn.imd.banco.view.ConsoleView;
import br.ufrn.imd.banco.controller.BankController;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        
        BankController controller = new BankController(view);
        
        controller.start();
    }
}