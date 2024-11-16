import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        // Création de la base de faits

         FactBase factBase = new FactBase();
              // Faits
              Fact fact1 = new Fact("maladie", List.of("x"), true);
              Fact fact2 = new Fact("grippe", List.of("x"), false);
              Fact fact3 = new Fact("symptome", List.of("x"), "forteFievre");
              Fact fact4 = new Fact("malade", List.of("john", "x"), true);
              Fact fact5 = new Fact("symptome", List.of("x"), "toux");
              Fact fact6 = new Fact("symptome", List.of("x"), "essouflement");
      
              // Ajouter des faits à la base
              factBase.addFact(fact1);
              factBase.addFact(fact3);
              factBase.addFact(fact4);
              factBase.addFact(fact5);
              factBase.addFact(fact6);
      
              // Création de la base de règles
              Rule r1 = new Rule(List.of(fact1, fact2, fact3), new Fact("maladie", List.of("x"), "rhume"));
              Rule r2 = new Rule(List.of(fact3), new Fact("malade", List.of("john", "x"), true));
              Rule r3 = new Rule(List.of(fact5), new Fact("maladie", List.of("x"), "infection"));
              Rule r5 = new Rule(List.of(fact1,fact6), fact2);
              Rule r4 = new Rule(List.of(fact1, fact5,fact2), new Fact("maladie", List.of("x"), "bronchite"));

        RuleBase ruleBase = new RuleBase( List.of(r1, r2, r3, r4,r5));
        // Création de la base de connaissances
        Moteur moteur = new Moteur(ruleBase,factBase);
     
        // Application des règles
         
        //moteur.chainageAvant(Criteres.critereIndex);
        moteur.chainageArriere(new Fact("maladie", List.of("x"), "bronchite"));
        moteur.trace.tracer();
 
    }
}
