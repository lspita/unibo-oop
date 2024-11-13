package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

public class DrawNumberStdoutView implements DrawNumberView {

    @Override
    public void setController(DrawNumberController observer) {
        return;
    }

    @Override
    public void start() {
        return;
    }

    @Override
    public void result(DrawResult res) {
        System.out.println(res.getDescription());
    }    
}
