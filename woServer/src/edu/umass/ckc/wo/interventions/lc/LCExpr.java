package edu.umass.ckc.wo.interventions.lc;

import edu.umass.ckc.wo.db.DbPedagogy;
import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.event.tutorhut.AttemptEvent;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.tutor.Settings;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 1/25/16
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class LCExpr {
    private LCFn fn;
//    private String fn;
//    private List<Object> args;
    private String relop;   // possible are: =, !=, <, <= ,>,>=
    private int valType;
    private String strVal;
    private int iVal;
    private double dVal;

    public static int STR = 0;
    public static int INT = 1;
    public static int DOUB = 2;
    public static int BOOL = 3;

    public LCExpr (String fn, Class[] args) {
        this.fn=new LCFn(fn,args);
        this.valType = BOOL;

    }

    public LCExpr(String fn,  Class[] args, String relop, String strVal) {
        this.fn=new LCFn(fn,args);
        this.relop = relop;
        this.valType = STR;
        this.strVal = strVal;
    }

    public LCExpr(String fn,  Class[] args, String relop, int iVal) {
        this.fn=new LCFn(fn,args);
        this.relop = relop;
        this.valType = INT;
        this.iVal = iVal;
    }

    public LCExpr(String fn,  Class[] args, String relop, double dVal) {
        this.fn=new LCFn(fn,args);
        this.relop = relop;
        this.valType = DOUB;
        this.dVal = dVal;
    }

    public boolean eval (SessionManager smgr, SessionEvent ev) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // use the fn to call a method in LCAccessors  .  Use the relop to compare the result to the value
        LCAccessors lca = new LCAccessors(smgr,ev);
        Object val = lca.eval(this.fn);
        if (valType == BOOL)
            return ((Boolean) val) == true;
        else if (relop.equals("=") && valType == INT )
            return ((Integer) val) == this.iVal;
        else if (relop.equals("=") && valType == DOUB)
            return ((Double) val) == this.dVal;
        else if (relop.equals("=") && valType == STR)
            return ((String) val).equals(this.strVal);
        else if (relop.equals("!=") && valType == INT)
            return ((Integer) val) == this.iVal;
        else if (relop.equals("!=") && valType == DOUB)
            return ((Double) val) != this.dVal;
        else if (relop.equals("!=") && valType == STR)
            return !((String) val).equals(this.strVal);
        else if (relop.equals("<") && valType == INT)
            return ((Integer) val) < this.iVal;
        else if (relop.equals("<") && valType == DOUB)
            return ((Double) val) < this.dVal;
        else if (relop.equals("<=") && valType == INT)
            return ((Integer) val) <= this.iVal;
        else if (relop.equals("<=") && valType == DOUB)
            return ((Double) val) <= this.dVal;
        else if (relop.equals(">") && valType == INT)
            return ((Integer) val) > this.iVal;
        else if (relop.equals(">") && valType == DOUB)
            return ((Double) val) > this.dVal;
        else if (relop.equals(">=") && valType == INT)
            return ((Integer) val) >= this.iVal;
        else if (relop.equals(">=") && valType == DOUB)
            return ((Double) val) >= this.dVal;

        return false;
    }

    public boolean evalPrint (SessionManager smgr, SessionEvent ev) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        boolean b = eval(smgr,ev);
        System.out.println(this.toString() +"is: " + b);
        return b ;
    }

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = SessionManager.getAConnection();
            Settings.lessonMap = DbPedagogy.buildAllLessons(conn);
            Settings.loginMap = DbPedagogy.buildAllLoginSequences(conn);
            Settings.pedagogyGroups = DbPedagogy.buildAllPedagogies(conn);
            SessionManager smgr = new SessionManager(conn);
            smgr.attemptSessionCreation("dm","dm",System.currentTimeMillis(),true);

            System.out.println("Testing LCExpr with user dm/dm studId="+ smgr.getStudentId());

            SessionEvent atEv = new AttemptEvent("test",false,10,20,smgr.getSessionNum());
            LCExpr e1 = new LCExpr("curTopicMastery",null, "<", 0.5);
            e1.evalPrint(smgr,null);
            Class[] args1 = new Class[] {SessionEvent.class};
            LCExpr e2 = new LCExpr("isAttemptEvent",args1);
            e2.evalPrint(smgr,null);
            LCExpr e3 = new LCExpr("curProbNumIncorrectAttempts",null, ">=", 0);
            e3.evalPrint(smgr,null);
            LCExpr e4 = new LCExpr("solvedOnAttempt",null, "=", 1);
            e4.evalPrint(smgr,null);
            LCExpr e5 = new LCExpr("timeToSolve",null, ">=", 5000);
            e5.evalPrint(smgr,null);
            LCExpr e6 = new LCExpr("curProbTimeToFirstAttempt",null, ">", 1000);
            e6.evalPrint(smgr,null);

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}