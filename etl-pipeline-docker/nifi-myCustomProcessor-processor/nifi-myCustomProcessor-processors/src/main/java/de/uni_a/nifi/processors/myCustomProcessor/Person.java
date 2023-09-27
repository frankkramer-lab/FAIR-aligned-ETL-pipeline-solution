package de.uni_a.nifi.processors.myCustomProcessor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {

    @JsonProperty("record_id")
    private String record_id;
    @JsonProperty("geburtsdatum")
    private String birthDate;
    @JsonProperty("alter")
    private String age;
    @JsonProperty("geschlecht")
    private String gender;
    @JsonProperty("familienstand")
    private String maritalStatus;
    @JsonProperty("bundesland")
    private String federalState;
    @JsonProperty("groesse")
    private String bodyheight;
    @JsonProperty("gewicht")
    private String bodyweight;
    @JsonProperty("aktbefinden")
    private String currentState;

    @Override
    public String toString() {
       return "Patient Nr. " +
                this.record_id + "\n" +
                "Geb.Datum: " + this.birthDate + "\n" +
                "Alter: " + this.age + "\n" +
                "Gender " + this.gender + "\n" +
                "fam.Status " + this.maritalStatus + "\n" +
                "Bundesland " + this.federalState + "\n" +
                "Groesse " + this.bodyheight + "\n" +
                "Gewicht " + this.bodyweight + "\n" +
                "Status " + this.currentState + "\n";
    }
}
