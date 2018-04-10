package verarbeitung;

public class Kontofabrik {
    private Konto konto;
    public Konto getKonto(){
        return this.konto;
    }
    public Kontofabrik(Konto konto){
        this.konto = konto;
    }
}
