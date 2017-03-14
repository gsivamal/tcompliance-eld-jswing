package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rule {
    private StringProperty ruleValue = new SimpleStringProperty();

    public Rule(String rule) {
        setRuleValue( rule );
    }

    public String getRuleValue() {
        return ruleValue.get();
    }

    public StringProperty ruleValueProperty() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue.set( ruleValue );
    }
}
