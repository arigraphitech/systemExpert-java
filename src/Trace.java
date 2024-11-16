public class Trace {
    
    StringBuilder chaine=new StringBuilder();

    public Trace(FactBase fb,RuleBase rb){
        chaine.append("systeme expert : \n la base de fait :\n").append(fb.toString()).append("\nla base de regle : \n ").append(rb.toString()).append("\n");
    }

    void add(String txt){
        this.chaine.append("\n").append(txt);
    }
     void add(Fact f){
        this.chaine.append("\nla base de fait a été étendu avec l'ajout du fait : ").append(f.toString());
    }
    void add(Rule r){
        this.chaine.append("\nla régle choisis est : \n").append(r.toString());
    }
    void tracer(){
        System.out.println(chaine.toString());
    }
}
