package com.lateralus.codebreaker.model.screen;

import com.lateralus.codebreaker.controller.CodeBreakerController;
import com.lateralus.codebreaker.view.CodeBreakerView;

public class CodeBreakerScreen {

    private Object model;
    private CodeBreakerView view;
    private CodeBreakerController controller;

    public CodeBreakerScreen(Object model, CodeBreakerView view, CodeBreakerController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public CodeBreakerView getView() {
        return view;
    }

    public void setView(CodeBreakerView view) {
        this.view = view;
    }

    public CodeBreakerController getController() {
        return controller;
    }

    public void setController(CodeBreakerController controller) {
        this.controller = controller;
    }
}
