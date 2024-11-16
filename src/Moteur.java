import java.util.ArrayList;
import java.util.List;

public class Moteur {
    private RuleBase rb ;
    private FactBase fb;
    Trace trace;

    public Moteur(RuleBase rb,FactBase fb){
       this.rb=rb;
       this.fb=fb;
       trace=new Trace(fb,rb);
    }

    public List<Rule>  RulesApplicables(){
        List<Rule> rules=new ArrayList<>();
        for(Rule r: rb.getRules()){
            if(r.isActive()){ //inutile de reparcourir les Rules deja utilisé
                if(r.estApplicable(fb)){
                    rules.add(r);
                }
            }
        }
        return rules;
    }

    public Rule critereNbrPrem(){ //retourne la Rule applicable ayant le plus de premisses à satisfaire
        List<Rule> ens=RulesApplicables();
        if(ens.size()==1){
            return ens.get(0);
        }
        else if(ens.size()==0){
            return null;
        }
        int maxPrem=ens.get(0).getNbrPremisses();
        int indRule=0;
        for(int i=1;i<ens.size();i++){ 
            if(ens.get(i).getNbrPremisses()>maxPrem){
                maxPrem=ens.get(i).getNbrPremisses();
                indRule=i;
            }
        }
        return ens.get(indRule);
    }
    public Rule criterePremRecent(){ //retourne la Rule applicable comportant comme premisses les faits déduit les plus récent
        List<Rule> ens=RulesApplicables();
        int sum=0;
        int most_recent=0;
        int indRule=0;
        if(ens.size()==1){ //si un seul choix possible pas besoin de faire de traitement
            return ens.get(0);
        }else if(ens.size()==0){ return null;}
        //lorsque on ajoute un nouveau fait à la bf on met a jour tour avec le tour ou il a ete deduit
        for(int i=0;i<ens.size();i++){ 
            for(Fact f : ens.get(i).getPremisses()){
                //en faisant la sum des attribus tour des premisses d'un regle on obtient un score comparatif 
                sum+=f.getTour();
            }
            if(sum>most_recent){
                most_recent=sum;
                indRule=i;
            }
        }
    
        return ens.get(indRule);
    }


    // methods
    public void addRule(Rule r) {this.rb.addRule(r); }

    void chainageAvant(Criteres c){
        trace.add("CHAINAGE AVANT en utilisant le critére ");
        Rule r;
        int tour=1;
        boolean newFact=true;
        do{
            if(c==Criteres.critereNbr){
                r=critereNbrPrem();
                trace.add("regles ayant le plus de prémisses à satisfaire");
            }else{
                r=criterePremRecent();
                trace.add("regle comportant comme prémisses les faits déduits le splus récents");
            }
            if(r!=null){
                trace.add(r);
                //on ajoute le consequent de la regle dans la base de fait
                Fact conclusion = r.getConclusion();
                if(!fb.contains(conclusion)){
                    Fact f= new Fact (conclusion.getName(), conclusion.getArguments(), true);
                    f.updateTour(tour);
                    fb.addFact(f);
                    trace.add(f);
                }
                r.disable(); //une fois utilisé on desactive la régle
                tour++;
            }
            else {
                 newFact=false;
                 trace.add("\n il n'y a plus de régle à appliquer . \nFIN");
            }
        }while(newFact);
    }

    void chainageAvant(Criteres c,Fact fait){
        trace.add("CHAINAGE AVANT en utilisant le critére ");
        Rule r;
        int tour=1;
        boolean newFact=true;
        boolean faitAveree=false;
        do{
            if(c==Criteres.critereNbr){
                r=critereNbrPrem();
                trace.add("regles ayant le plus de prémisses à satisfaire");
            }else{
                r=criterePremRecent();
                trace.add("regle comportant comme prémisses les faits déduits le splus récents");
            }
            if(r!=null){
                trace.add(r);
                //on ajoute le consequent de la regle dans la base de fait
                Fact conclusion = r.getConclusion();
                if(!fb.contains(conclusion)){
                    Fact f= new Fact (conclusion.getName(), conclusion.getArguments(), true);
                    f.updateTour(tour);
                    fb.addFact(f);
                    trace.add(f);
                }
                r.disable(); //une fois utilisé on desactive la régle
                tour++;
                if(fb.contains(fait)){
                    faitAveree=true;
                }
            }
            else {
                 newFact=false;
                 trace.add("\n il n'y a plus de régle à appliquer . \nFIN");
            }
        }while(newFact && !(faitAveree));  
    }


    public List<Rule> concluantButApplicable(Fact f){ //retourne la liste de regle APPLICABLE qui ont comme conclusion le but 
        List<Rule> ens=RulesApplicables();
        List<Rule> c =new ArrayList<>();
        for(Rule r : ens){
            if(r.getConclusion().equals(f)){
                c.add(r);
            }
        }
        return c;

    }
    
    public List<Rule> concluantBut(Fact f){ //retourne la liste de regle qui ont comme conclusion le but 
        List<Rule> c =new ArrayList<>();
        for(Rule r : rb.getRules()){
            if(r.getConclusion().equals(f)){
                c.add(r);
            }
        }
        return c;
    }

    public void chainageArriere(Fact fait) {
        trace.add("CHAINAGE ARRIERE pour satisfaire le but : ");
        trace.add(fait.toString());
    
        // Vérifier si le fait est déjà prouvé
        if (fb.contains(fait)) {
            trace.add("Le fait est déjà prouvé : " + fait.toString());
            return;
        }
    
        // Trouver les règles concluant ce but
        List<Rule> rules = concluantButApplicable(fait);
        
        if (rules.size() > 0) { 
            // Au moins une règle conclut le but
            Rule rule = rules.get(0); // On utilise la première règle applicable
            trace.add("Le but est vérifié par la règle : " + rule.toString());
            fb.addFact(fait); // Ajouter le fait prouvé à la base de faits
            rule.disable(); // Désactiver la règle pour éviter de la réutiliser
        } else {
            // Aucun règle ne conclut directement ce but, examiner les prémisses
            List<Rule> allRules = concluantBut(fait); // Récupérer toutes les règles concluant ce but
            if (allRules.isEmpty()) {
                trace.add("Aucune règle ne peut conclure le but : " + fait.toString());
                return; // Aucun moyen de prouver ce but
            }
    
            for (Rule rule : allRules) {
                trace.add("Examiner la règle : " + rule.toString());
                List<Fact> sousButs = rule.getPremisses();
    
                boolean allPremissesVerified = true;
                for (Fact sousBut : sousButs) {
                    trace.add("Nouveau sous-but : " + sousBut.toString());
                    chainageArriere(sousBut); // Appel récursif pour prouver le sous-but
    
                    if (!fb.contains(sousBut)) {
                        allPremissesVerified = false;
                        trace.add("Le sous-but n'a pas pu être prouvé : " + sousBut.toString());
                        break; // Si une prémisse échoue, pas besoin de vérifier les autres
                    }
                }
    
                if (allPremissesVerified) {
                    trace.add("Les prémisses de la règle sont vérifiées, le but est prouvé : " + fait.toString());
                    fb.addFact(fait);
                    rule.disable();
                    return; // Le but est prouvé, on arrête
                }
            }
    
            trace.add("Le but n'a pas pu être prouvé : " + fait.toString());
        }
    }
    

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("moteur d'inference 1 " +"\n");
        return sb.toString();
    }


    
    
}
