package verarbeitung;

import java.time.LocalDate;

/**
 * Created by Tobias on 25/10/17.
 */
public class Student extends Kunde {
    /**
     * Definition des Namens der Universitaet von Student
     */
    public String uniname; //Name
    /**
     * Definition des Studienfaches von Student
     */
    public String studienfach; //Studienfach
    /**
     * Definition Datums des voraussichtlichen Studienendes von Student
     */
    public LocalDate studienende; //Studienende
    /**
     * Semesternummer des letzten bescheinigten Semesters von Student
     */
    public int letzteStudienbescheinigungSemester; //Letztes bescheinigtes Semester
    /**
     * Status des Vorhandenseins von einer aktuellen Semesterbescheinigung von Student
     */
    public boolean aktuelleStudienBescheinigung; //liegt eine aktuelle Studienbescheinigung vor?
    /**
     * Semesternummer der erhaltenen Studienbescheinigung von Student
     */
    public int studienbescheinigung;


    /**
     * Konstruktor fuer Student
     * Fuer Beschreibung der Parameter siehe oben.
     * @param uniname
     * @param studienfach
     * @param studienende
     * @param letzteStudienbescheinigungSemester
     * @param aktuelleStudienBescheinigung
     */
    public Student(String uniname, String studienfach, LocalDate studienende, int letzteStudienbescheinigungSemester, boolean aktuelleStudienBescheinigung){
        this.uniname = uniname;
        this.studienfach = studienfach;
        this.studienende = studienende;
        this.letzteStudienbescheinigungSemester = letzteStudienbescheinigungSemester;
        this.aktuelleStudienBescheinigung = aktuelleStudienBescheinigung;

    }

    /**
     * Gibt die Eckdaten des Studenten aus
     * @return
     */
    public String toString() {
        return "Name der Universität: "+ this.uniname + "⁄n" + "Name des Studienfaches: " + this.studienfach;
    }

    /**
     *Veraendert Vorhandenseins status von true auf false oder false auf true.
     * @param aktuelleStudienBescheinigung
     */
    public void setAktuelleStudienBescheinigung(boolean aktuelleStudienBescheinigung) {
        this.aktuelleStudienBescheinigung = aktuelleStudienBescheinigung;
    }


    /**
     * Unterscheidet zwischen erhaltener und gespeicherter Studienbescheinigungsversion als Integer.
     * Wenn gleich: Aktuelle Bescheinigung vorhanden                    -> set true
     * Wenn erhalten ist groeßer als vorhanden: Bescheinigung update    -> set true
     * Wenn vorhanden ist groeßer erhalten: Veraltete bescheinigung     -> set false
     * @param letzteStudienbescheinigungSemester
     * @param studienbescheinigung
     */
    public void studienbescheinigungAktuell(int letzteStudienbescheinigungSemester, int studienbescheinigung ){
        if (letzteStudienbescheinigungSemester == studienbescheinigung){
            System.out.println("Aktuelle Studienbescheinigung bereits vorhanden");
            setAktuelleStudienBescheinigung(true);
        }
        else if (letzteStudienbescheinigungSemester <= studienbescheinigung){
            System.out.println("Neue Studienbescheinigung updated");
            setAktuelleStudienBescheinigung(true);
        }
        else if(letzteStudienbescheinigungSemester >= studienbescheinigung){
            System.out.println("Veraltete Studienbescheinigung");
            setAktuelleStudienBescheinigung(false);
        }
    }
}
