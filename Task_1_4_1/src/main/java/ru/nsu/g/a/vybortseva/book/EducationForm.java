package ru.nsu.g.a.vybortseva.book;

public enum EducationForm {
    PAID("платная"),
    BUDGET("бюджет");

    private final String form;

    EducationForm(String form) {
        this.form = form;
    }

    public String getForm() {
        return form;
    }
}
