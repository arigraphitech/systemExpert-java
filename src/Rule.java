import java.util.List;

public class Rule {
    private List<Fact> premisses ;
    private Fact conclusion ;
    boolean active;

    public Rule(List<Fact> p, Fact c){
        this.premisses = p;
        this.conclusion = c ;
        this.active=true;
    }

    // methods
    // est ce que la regle erst applicable par rapport a notre base de fait actuelle
    public boolean estApplicable(FactBase fb){
        if(active){
        List<Fact> liste = fb.getFacts();
        for(Fact premisse : premisses){
            boolean conditionSAt = false ;
            for (Fact fait : liste){
                if(fait.equals(premisse))
                {
                    conditionSAt = true;
                    //on devrait pas ajouter un continue ici ?
                }
            }
            if(!conditionSAt)
                return false ;
        }
        return true ;}
        return false;
    }
    


    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Fact f : premisses){
            sb.append(f.toString()+" Et ");
        }
        sb.deleteCharAt(sb.length() -2); //enleve le dernier ET
        sb.deleteCharAt(sb.length() -2);

        sb.append(" => "+this.conclusion.toString());
        return sb.toString();
    }

    // getters and setters
    public List<Fact> getPremisses() {
        return this.premisses;
    }

    public int getNbrPremisses(){
        return premisses.size();
    }

    public void setPremisses(List<Fact> premisses) {
        this.premisses = premisses;
    }

    public Fact getConclusion() {
        return this.conclusion;
    }

    public void setConclusion(Fact conclusion) {
        this.conclusion = conclusion;
    }

    public void disable(){
        this.active=false;
    }

    public boolean isActive(){
        return active;
    }
    

}
