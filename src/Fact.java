import java.util.*;

public class Fact {
    private String predicate;
    private List<String> arguments;
    private Object value;
    int tour; //stocke le tour ou on a deduit ce fait

    public Fact(String predicate, List<String> arg, Object vv){
        this.predicate = predicate ;
        this.arguments = arg;
        this.value = vv;
        this.tour=0;
    }

    //methodes
    public boolean equals (Object obj){ //a revoir faux car 1 et non 0+
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fact fact = (Fact) obj;
        return predicate.equals(fact.predicate) && arguments.equals(fact.arguments) && ( ( value != null && value.equals(fact.value))|| value == null && value.equals( null));
    }


    void updateTour(int tour){
        this.tour=tour;
    }

    int getTour(){
        return tour;
    }

    public boolean detecterIncoherence(List<Fact> facts){
        // detecter une incoherence entre le fait et notre base de faits
        // le probleme de cette implementation et que les arguments de deux faits egaux doivent etre dans le meme ordre pour pouvoir detecter qu'ils sont egaux
        for (Fact f : facts){
            if(f.predicate.equals(this.predicate) && f.arguments.equals(this.arguments) && f.value != this.value)
                return true;
        }
        return false ;

        //on peut faire ça alors ?
        //mais la valeur not elle est propre à chaque arguments non ?
/*
        boolean same=true;
        for (Fact f : facts){
            if(f.predicate.equals(this.predicate) && f.value != this.value) {
                same=true;
                for(String arg: f.arguments){
                    if(! arguments.contains(arg)){
                        same=false;
                        break;
                    }
                }
                if(same){
                    return true;
                }
            }
                
        }
        return false ;
        
         */
    }

    public String toString(){
        StringBuilder db = new StringBuilder();
        if(this.value.equals(false))
            db.append("NOT ");
        db.append(this.predicate+ " (");
        for(String arg : arguments){
            db.append(arg+",");
        }
        if(this.arguments.size()!=0)
            db.deleteCharAt(db.length()-1);
        db.append(")");
        if(valeurString().length()>0){
            db.append("=");
            db.append(valeurString());
        }
        return db.toString();
    }

    String valeurString(){
        if(!( value!=null && value  instanceof Boolean)){
            return value.toString();
        }else{
            return "";
        }}

    @Override
    public int hashCode(){
        return Objects.hash(predicate, arguments);
    }



    // getters and setters 
    public String getpredicate() {
        return this.predicate;
    }

    public void setpredicate(String predicate) {
        this.predicate = predicate;
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isValeurVerite() {
        return this.value.equals(true);
    }

    public Object getValue() {
        return this.value;
    }
    public String getName() {
        return this.predicate;
    }

    public void setValue(Object value) {
        this.value =  value;
    }
}
