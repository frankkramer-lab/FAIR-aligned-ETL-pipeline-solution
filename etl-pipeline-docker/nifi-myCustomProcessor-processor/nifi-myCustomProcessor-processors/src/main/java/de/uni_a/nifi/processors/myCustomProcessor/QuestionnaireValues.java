package de.uni_a.nifi.processors.myCustomProcessor;


import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;
import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.model.QuestionnaireResponse;
import org.hl7.fhir.r4.model.StringType;

import java.util.Map;

import static java.util.Map.entry;

public class QuestionnaireValues {
    static final String questionnaireID = "WAVES1";
    static final String questionnaireTitle = "KFDM WAVES";
    static final PublicationStatus questionnaireStatus = PublicationStatus.DRAFT;
    static final Boolean questionnaireExperimental = true;

    static final String questionnaireDescription = "Sehr geehrte, liebe Patientin, sehr geehrter, lieber Patient, wir freuen uns, " +
            "dass Sie sich die Zeit zur Teilnahme an dieser Studie mit Fragen zu Ihrer Brustkrebserkrankung nehmen." +
            "Sie leisten damit einen wichtigen Beitrag zur Qualitätsverbesserung patientenorientierter" +
            "Versorgungsstrukturen. Die Inhalte wurden gemeinsam von Vertretern aus Patientenorganisationen, " +
            "Ärzte, spezialisierten Pflegekräften, Psychologen und Spezialisten auf dem Gebiet der Kommunikation" +
            " erstell. Mit Ihren Antworten zu allgemeinen, aber auch speziellen Fragen zu Ihrer Erkrankung," +
            " Ihren Erfahrungen im Austausch mit Ärzten und Pflege sowie Ihren Wünschen und Bedürfnissen ist es" +
            " uns möglich, ein Bild der aktuellen Versorgung zu erheben. Wichtige Erkenntnisse wird auch der Vergleich" +
            " liefern, wie Pflegekräfte und Ärzte die Versorgung einschätzen. Daraus sollen für die Zukunft gemeinsam" +
            " erarbeitete Lösungsansätze entstehen. Die Befragung richtet sich an Brustkrebs-Erkrankte jedes Stadiums …" +
            " Die Angaben zum Datenschutz haben Sie bereits in der uns rückgesendeten Patienteninformation erhalten … " +
            "…Aus Gründen der Praktikabilität und Lesbarkeit wurden alle Fragen zu Geschlechtern in der männlichen Form " +
            "verfasst. Es wird darauf hingewiesen, dass diese auch für die weibliche und neutrale Form in gleicher " +
            "Weise gelten. Die Antwortmöglichkeiten sind mit der Angabe „ja/nein/unbekannt“ zu beantworten. Teilweise" +
            " sind Mehrfachantworten möglich oder Sie werden gebeten, die Antwort auf einer Skala von 1=“trifft gar " +
            "nicht zu“ bis 5=“ trifft voll und ganz zu“ zu bewerten, auf die Sie an entsprechender Stelle hingewiesen" +
            " und weitergeleitet werden. Bei offenen Antworten werden Sie gebeten, bitte ein Stichwort mit Ihrer" +
            " höchsten Priorität zu nennen. Bei Mehrfachnennungen bitte einfügen: keine der Antworten trifft zu." +
            " Daten Erstgespräch - Diagnosemitteilung (Erstdiagnose) In diesem Abschnitt möchten wir gerne mehr " +
            "über die Rahmenbedingungen Ihrer Gespräche im Rahmen der ersten Diagnosemitteilung erfahren, ihr " +
            "Erleben dieser Situation (emotional, Eindruck des Arztes) und die Ihnen überlieferte Information. ";

    static final String item_1_text = "Rahmenbedingungen des Gespräches";
    static final String item_1_link_id = "1";

    // item 1.1
    static final String item_1_1_text = "Wer hat Sie als Erstes über Ihre Diagnose Brustkrebs aufgklärt?";
    static final String item_1_1_link_id = "1.1";
    static final Questionnaire.QuestionnaireItemAnswerOptionComponent[] item_1_1_answers = {
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Hausarzt")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Radiologe")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Frauenarzt in der Niederlassung")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Frauenarzt einer Klinik")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Onkologe in der Niederlassung")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Onkologe einer Klinik")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("unbekannt"))
    };

    // item 1.2
    static final String item_1_2_text = "In wlecher Form wurden Sie über die Diagnose informiert?";
    static final String item_1_2_link_id = "1.2";
    static final Questionnaire.QuestionnaireItemAnswerOptionComponent[] item_1_2_answers = {
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("in einem persönlichen Gespräch")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("telefonisch")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("in schriftlicher Form (Brief)")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("per Email")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("unbekannt"))
    };
    // item 1.3
    static final String item_1_3_text = "Ist in Ihrer Familie eine Krebserkrankung bekannt?";
    static final String item_1_3_link_id = "1.3";
    // item 2
    static final String item_2_text = "Erleben während des Gespräches";
    static final String item_2_link_id = "2";
    // item 2.1
    static final String item_2_1_text = "Was war Ihre erste Reaktion auf den Befund? (Mehrfachnennung möglich)";
    static final String item_2_1_name = "erste_reaktion_auf_befund_";
    static final String item_2_1_link_id = "2.1";
    static final Questionnaire.QuestionnaireItemAnswerOptionComponent[] item_2_1_answers = {
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich war geschockt")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich fühlte mich hilflos")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich hatte es geahnt / befürchtet")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich hatte Angst")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich war wütend")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich fühlte mich überfordert und alleingelassen")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich schaff das, egal wie")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich war nicht aufnahmefähig")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Anderes vorherrschendes Gefühl"))
    };
    // group 2_2
    static final String item_2_2_text = "Wie erlebten Sie Ihren Arzt während des Diagnosegesprächs? (1=trifft gar nicht zu, 5=trifft voll und ganz zu)";
    static final String item_2_2_link_id = "2.2";
    static final String item_2_2_1_name = "bewertung_arzt_sachlich";
    static final String item_2_2_1_text = "Sachlich:";
    static final String item_2_2_1_link_id = "2.2.1";
    static final String item_2_2_2_name = "bewertung_arzt_gestresst";
    static final String item_2_2_2_text = "Gestresst:";
    static final String item_2_2_2_link_id = "2.2.2";
    static final String item_2_2_3_name = "bewertung_arzt_besorgt";
    static final String item_2_2_3_text = "Besorgt:";
    static final String item_2_2_3_link_id = "2.2.3";
    static final String item_2_2_4_name = "bewertung_arzt_klar";
    static final String item_2_2_4_text = "Klar:";
    static final String item_2_2_4_link_id = "2.2.4";
    static final String item_2_2_5_name = "bewertung_arzt_hilfsbereit_unterst_tzend";
    static final String item_2_2_5_text = "Hilfsbereit, unterstützend";
    static final String item_2_2_5_link_id = "2.2.5";
    static final String item_2_2_6_name = "bewertung_arzt_einf_hlsam_mitf_hlend";
    static final String item_2_2_6_text = "Einfühlsam, mitfühlend";
    static final String item_2_2_6_link_id = "2.2.6";
    static final Coding[] scaleCodings = {
            new Coding("http://example.org/CodeSystem/fuenf-punkte-skala-cs", "1", "Trifft gar nicht zu"),
            new Coding("http://example.org/CodeSystem/fuenf-punkte-skala-cs", "2", "Trifft eher nicht zu"),
            new Coding("http://example.org/CodeSystem/fuenf-punkte-skala-cs", "3", "Trifft teilweise zu"),
            new Coding("http://example.org/CodeSystem/fuenf-punkte-skala-cs", "4", "Trifft eher zu"),
            new Coding("http://example.org/CodeSystem/fuenf-punkte-skala-cs", "5", "Trifft voll und ganz zu")
    };
    static final Questionnaire.QuestionnaireItemAnswerOptionComponent[] scaleAnswers = {
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("0")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("1")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("2")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("3")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("4"))
    };
    static final Map<String, Coding> scaleMap = Map.ofEntries(
            entry("0", scaleCodings[0]),
            entry("1", scaleCodings[1]),
            entry("2", scaleCodings[2]),
            entry("3", scaleCodings[3]),
            entry("4", scaleCodings[4])
    );

    // item 2_3
    static final String item_2_3_text = "Wie gut haben Sie sich im Allgemeinen aufgeklärt gefühlt bei der Erstdiagnose?";
    static final String item_2_3_link_id = "2.3";
    static final Questionnaire.QuestionnaireItemAnswerOptionComponent[] item_2_3_answers = {
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#1 (\"sehr gut\")")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#gut")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#befriedigend")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#ausreichend")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#mangelhaft"))
    };
    // item 2_4
    static final String item_2_4_text = "Wie lange dauerte das Erstgespräch ungefähr?";
    static final String item_2_4_link_id = "2.4";
    static final Questionnaire.QuestionnaireItemAnswerOptionComponent[] item_2_4_answers = {
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#10min")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#15in")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#20min")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#30min")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#>30min"))
    };
    // item 2_5
    static final String item_2_5_text = "Konnte der behandelnde Arzt Ihnen den Befund verständlich erklären?";
    static final String item_2_5_link_id = "2.5";
    static final Questionnaire.QuestionnaireItemAnswerOptionComponent[] item_2_5_answers = {
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ja, ich hatte den Befund vollständig verstanden")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ja, ich hatte das Wesentliche verstanden")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Nein, ich konnte den Befund nur teilweise verstehen")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Nein, ich habe nichts verstanden")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich war mir unsicher, ob ich alles verstanden hatte"))
    };
    // item 2_6
    static final String item_2_6_text = "Welche Emotionen hatten Sie während des ärztlichen Aufklärungsgesprächs? (Mehrfachnennung möglich)";
    static final String item_2_6_link_id = "2.6";
    static final String item_2_6_name = "emotionen_aufkl";
    static final Questionnaire.QuestionnaireItemAnswerOptionComponent[] item_2_6_answers = {
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("es hat mich verwirrt")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("ich fühlte mich gut beraten")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("ich empfand es ermutigend")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("ich empfand es richtungsweisend")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("es hat mich verängstigt"))
    };
    // item 2_7
    static final String item_2_7_text = "Wenn Sie die Befundmitteilung als unverständlich erlebt haben, was hätten Sie sich gewünscht, um den Befund besser zu verstehen? (Mehrfachantworten möglich)";
    static final String item_2_7_link_id = "2.7";
    static final String item_2_7_name = "wunsch_besseres_";
    static final Questionnaire.QuestionnaireItemAnswerOptionComponent[] item_2_7_answers = {
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich hatte den Befund vollständig verstanden")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Verwendung weniger Fremdwörter")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Der Arzt hätte langsamer sprechen können")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich hätte mir mehr Zeit zum Überlegen gewünscht")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Ich hätte mir mehr Anschauungsmaterial gewünscht")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("Sonstiges"))
    };
    // item 2_8
    static final String item_2_8_text = "Sonstiges bitte hier eintragen:";
    static final String item_2_8_link_id = "2.8";
    static final String item_2_8_name = "wunsch_besseres_verstndnis_sonstiges";

    // item 2_8_last
    static final String item_2_9_text = "Wie aufnahmefähig waren Sie nach Ihrer eigenen Einschätzung zum Zeitpunkt der Mitteilung der Diagnose und weiteren empfohlenen Schritte auf einer Skala von 0-10 (0: nicht aufnahmefähig; 10: sehr gut aufnahmefähig)";
    static final String item_2_9_link_id = "2.9";
    static final Questionnaire.QuestionnaireItemAnswerOptionComponent[] item_2_9_answers = {
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#0")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#1")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#2")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#3")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#4")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#5")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#6")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#7")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#8")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#9")),
            new Questionnaire.QuestionnaireItemAnswerOptionComponent(new StringType("null#10"))
    };
    static final Map<String, QuestionnaireResponse.QuestionnaireResponseStatus> statusMap = Map.ofEntries(
            entry("0", QuestionnaireResponse.QuestionnaireResponseStatus.INPROGRESS),
            entry("1", QuestionnaireResponse.QuestionnaireResponseStatus.COMPLETED),
            entry("2", QuestionnaireResponse.QuestionnaireResponseStatus.AMENDED),
            entry("3", QuestionnaireResponse.QuestionnaireResponseStatus.ENTEREDINERROR),
            entry("4", QuestionnaireResponse.QuestionnaireResponseStatus.STOPPED)
    );
}