public class Parser {
    Moteur m;

    /*format d'entree :
     * Faits : Exemple : nom(arg1,..,argn)= valeur ou nom(arg1,..) ou not nom(arg1,..)
     * Regles : exemple : fait1 ET nom(arg1)=string ET .... ALORS faitn ou fait1 ^ fait2 ...=> faitx
     * methode appele : chainage avant ou chainage arriere
     * possibilite d'afficher les explication a la demande : trace  ou rien
     */
   void parse(String s){
    s = s.trim(); // Supprimer les espaces inutiles au début/fin
    for(int i=0;i<s.length();i++){

        while(s.charAt(i)==' '){
            continue;
        }


    }
    
   }
}
