import java.util.*;

public class Fact {
    private String name;
    private List<String> arguments;
    private boolean value;

    public Fact(String name, List<String> arg, boolean vv){
        this.name = name ;
        this.arguments = arg;
        this.value = vv;
    }

    //methodes
    public boolean equals (Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fact fact = (Fact) obj;
        return name.equals(fact.name) && arguments.equals(fact.arguments);
    }


    public boolean detecterIncoherence(List<Fact> facts){
        // detecter une incoherence entre le fait et notre base de faits
        // le probleme de cette implementation et que les arguments de deux faits egaux doivent etre dans le meme ordre pour pouvoir detecter qu'ils sont egaux
        for (Fact f : facts){
            if(f.name.equals(this.name) && f.arguments.equals(this.arguments) && f.value != this.value)
                return true;
        }
        return false ;
    }

    public String toString(){
        StringBuilder db = new StringBuilder();
        if(!this.value)
            db.append("NOT ");
        db.append(this.name+ " (");
        for(String arg : arguments){
            db.append(arg+",");
        }
        if(this.arguments.size()!=0)
            db.deleteCharAt(db.length()-1);
        db.append(")");
        return db.toString();
    }

    public Fact copy(){
        return new Fact(this.name, this.arguments, this.value);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, arguments);
    } // tu return un objet maius le type est int



    // getters and setters 
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isValeurVerite() {
        return this.value;
    }

    public boolean getValue() {
        return this.value;
    }

    public void setValue(boolean valeurVerite) {
        this.value = valeurVerite;
    }
}
