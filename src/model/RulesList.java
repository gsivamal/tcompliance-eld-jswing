package model;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class RulesList extends SimpleListProperty<Rule>{

    public RulesList(){
        super( FXCollections.observableArrayList() );
    }

}
