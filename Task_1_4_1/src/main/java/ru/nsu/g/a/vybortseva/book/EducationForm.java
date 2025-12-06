package ru.nsu.g.a.vybortseva.book;

/**
 * Represents the form of education for a student.
 * Can be either budget-funded or paid.
 */
public enum EducationForm {
    PAID("платная"),
    BUDGET("бюджет");

    private final String form;

    /**
     * Creates an education form with its description.
     */
    EducationForm(String form) {
        this.form = form;
    }

    /**
     * Returns the textual representation of the education form.
     */
    public String getForm() {
        return form;
    }
}
