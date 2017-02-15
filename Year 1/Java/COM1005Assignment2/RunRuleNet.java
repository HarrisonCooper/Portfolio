
/**
 * RunRuleNet.java
 * create a rule network & make deductions with it
 * @author Phil Green
 * First Version 23/1/08
 */

import sheffield.*;
import java.util.*;
import pmatch.*;


public class RunRuleNet{
 public static void main(String[] arg) {
   // create object for output
   EasyWriter scr = new EasyWriter();
   
   //make some rules
   
   //grandfather
   ArrayList<String> gfantes= new ArrayList<String>();
   gfantes.add("?gf father of ?p");
   gfantes.add("?p parent of ?c");
   RuleNode gfrule = new RuleNode ("grandfather rule",gfantes, "?gf grandfather of ?c");
   ArrayList<RuleNode> gfsuccs = new ArrayList<RuleNode>();
   gfrule.setSuccessors(gfsuccs);
   
   //uncle
   ArrayList<String> uantes = new ArrayList<String>();
   //uantes.add("?a is male");
   uantes.add("?a brother of ?p");
   uantes.add("?p parent of ?c");
   RuleNode urule = new RuleNode ("uncle rule", uantes, "?a uncle of ?c");
   ArrayList<RuleNode> usuccs = new ArrayList<RuleNode>();
   urule.setSuccessors(usuccs);
   
   //brother
   ArrayList<String> broantes = new ArrayList<String>();
   broantes.add("?bro sibling of ?s");
   RuleNode brorule = new RuleNode ("brother rule", broantes, "?bro brother of ?s");
   ArrayList<RuleNode> brosuccs = new ArrayList<RuleNode>();
   brorule.setSuccessors(brosuccs);
   
   //father-is-parent
   ArrayList<String> fpantes = new ArrayList<String>();
   fpantes.add("?f father of ?c");                                                                             
   RuleNode fprule = new RuleNode ("father-is-parent rule", fpantes, "?f parent of ?c");
   ArrayList<RuleNode> fpsuccs = new ArrayList<RuleNode>();
   fpsuccs.add(gfrule);
   fpsuccs.add(brorule);
   fpsuccs.add(urule);
   fprule.setSuccessors(fpsuccs);
   
   //father-is-male
   ArrayList<String> fmantes = new ArrayList<String>();
   fmantes.add("?f father of ?c");                                                                             
   RuleNode fmrule = new RuleNode ("father-is-male rule", fmantes, "?f is male");
   ArrayList<RuleNode> fmsuccs = new ArrayList<RuleNode>();
   fmrule.setSuccessors(fmsuccs);
   
   //aunt
   ArrayList<String> aantes = new ArrayList<String>();
   aantes.add("?a is female");
   aantes.add("?a sister of ?p");
   aantes.add("?p parent of ?c");
   RuleNode arule = new RuleNode ("aunt rule", aantes, "?a aunt of ?c");
   ArrayList<RuleNode> asuccs = new ArrayList<RuleNode>();
   arule.setSuccessors(asuccs);
   
   //sister
   ArrayList<String> sisantes = new ArrayList<String>();
   sisantes.add("?sis sibling of ?sib");
   sisantes.add("?sis is female");
   RuleNode sisrule = new RuleNode ("sister rule", sisantes, "?sis sister of ?sib");
   ArrayList<RuleNode> sissuccs = new ArrayList<RuleNode>();
   sisrule.setSuccessors(sissuccs);
   
   //mother-is-parent
   ArrayList<String> mpantes = new ArrayList<String>();
   mpantes.add("?m mother of ?c");                                                                             
   RuleNode mprule = new RuleNode ("mother-is-parent rule", mpantes, "?m parent of ?c");
   ArrayList<RuleNode> mpsuccs = new ArrayList<RuleNode>();
   mpsuccs.add(sisrule);
   mpsuccs.add(arule);
   mprule.setSuccessors(mpsuccs);
   
   //mother-is-female
   ArrayList<String> mfantes = new ArrayList<String>();
   mfantes.add("?m mother of ?c");                                                                             
   RuleNode mfrule = new RuleNode ("mother-is-female rule", mfantes, "?m is female");
   ArrayList<RuleNode> mfsuccs = new ArrayList<RuleNode>();
   mfrule.setSuccessors(mfsuccs);
   
   //siblings
   ArrayList<String> sibantes = new ArrayList<String>();
   sibantes.add("?m parent of ?x");
   sibantes.add("?m parent of ?y");
   RuleNode sibrule = new RuleNode ("sibling rule", sibantes, "?x sibling of ?y");
   ArrayList<RuleNode> sibsuccs = new ArrayList<RuleNode>();
   sibrule.setSuccessors(sibsuccs);
   
   //cousins
   ArrayList<String> cantes = new ArrayList<String>();
   cantes.add("?a aunt of ?c");
   cantes.add("?a parent of ?v");
   RuleNode crule = new RuleNode ("cousin rule", cantes, "?c cousin of ?v");
   ArrayList<RuleNode> csuccs = new ArrayList<RuleNode>();
   crule.setSuccessors(csuccs);
   
   ArrayList<String> coantes = new ArrayList<String>();
   coantes.add("?u uncle of ?c");
   coantes.add("?u parent of ?v");
   RuleNode corule = new RuleNode ("cousin2 rule", coantes, "?c cousin of ?v");
   ArrayList<RuleNode> cosuccs = new ArrayList<RuleNode>();
   corule.setSuccessors(cosuccs);
   
   //nephews
   ArrayList<String> nantes = new ArrayList<String>();
   nantes.add("?u uncle of ?c");
   RuleNode nrule = new RuleNode ("nephew rule", nantes, "?c nephew of ?u");
   ArrayList<RuleNode> nsuccs = new ArrayList<RuleNode>();
   nrule.setSuccessors(nsuccs);
   
   ArrayList<String> neantes = new ArrayList<String>();
   neantes.add("?a aunt of ?c");
   RuleNode nerule = new RuleNode ("nephew2 rule", neantes, "?c nephew of ?a");
   ArrayList<RuleNode> nesuccs = new ArrayList<RuleNode>();
   nerule.setSuccessors(nesuccs);
   
   // the set of rulenodes
   
   ArrayList<RuleNode> rset = new ArrayList<RuleNode>();
   rset.add(fprule);
   rset.add(gfrule);
   //all of my custom rules.
   rset.add(sisrule);
   rset.add(sibrule);
   rset.add(mprule);
   rset.add(fmrule);
   rset.add(mfrule);
   rset.add(arule);
   rset.add(brorule);
   rset.add(urule);
   rset.add(crule);
   rset.add(corule);
   rset.add(nrule);
   rset.add(nerule);
   
   
   // make the rule net
   RuleNet rs = new RuleNet(rset);
   //initialise it - set up initial tokens
   rs.initialise(); 
   
   //add facts
   ArrayList<String> facts = new ArrayList<String>();
//   rs.addFact("H7 father of H8");
//   rs.addFact("H8 father of E");
   rs.addFact("Jill mother of David");
   rs.addFact("Jill mother of Shula");
   rs.addFact("David father of Pip");
   rs.addFact("Shula mother of Daniel");
   rs.addFact("David brother of Shula");
  }
}