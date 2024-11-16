import java.util.ArrayList;
import java.util.List;

public class FactBase {
    private List<Fact> facts;

    public FactBase(List<Fact> facts){
        this.facts = facts ;
    }
    public FactBase(){
        facts = new ArrayList<>();
    }

    // methods
    public boolean contains(Fact f){
        for(Fact fact : facts){
            if(fact.equals(f))
                return true ;
        }
        return false ;
    }

    public void addFact(Fact f){
        if(!facts.contains(f))
            this.facts.add(f);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
         for(Fact f : facts){
            sb.append(f.toString()+" \n");
           
        }
        return sb.toString();
    }


    // getters ans setters 
    public List<Fact> getFacts() {
        return this.facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }

    
}
