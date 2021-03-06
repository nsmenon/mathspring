package edu.umass.ckc.wo.beans;
import edu.umass.ckc.wo.content.CCStandard;


import java.util.Set;

/**
 * <p> Created by IntelliJ IDEA.
 * User: david
 * Date: Jul 17, 2008
 * Time: 10:30:17 AM
 */

public class Topic implements Comparable<Topic> {
    private int id;
    private String name;
    private String summary;
    private int seqPos;
    private int oldSeqPos;
    private Set<CCStandard> ccStandards;
    private int numProbs;
    private int[] problemsByGrade;

    public static final String ID = "id";
    public static final String INTRO = "intro";
    public static final String SUMMARY = "summary";
    public static final String DESCRIPTION = "description";
    public static final String TYPE = "type";
    public static final String ACTIVE = "active";

    public Topic () {
    }

    public Topic (int id, String name) {
        this();
        this.name = name;
        this.id = id;
    }

    public Topic (int id, String name, String summary) {
        this(id,name);
        setSummary(summary);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName (String n) {
        this.name = n;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeqPos() {
        return seqPos;
    }

    public void setSeqPos(int seqPos) {
        this.seqPos = seqPos;
    }

    public int getOldSeqPos() {
        return oldSeqPos;
    }

    public void setOldSeqPos(int oldSeqPos) {
        this.oldSeqPos = oldSeqPos;
    }

    public Set<CCStandard> getCcStandards() {
        return ccStandards;
    }

    public void setCcStandards(Set<CCStandard> ccStandards) {
        this.ccStandards = ccStandards;
    }

    /**
     * @return The Common Core Standards associated with this topic, as a comma-separated string.
     */
    public String getStandards () {
        if (this.ccStandards == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (CCStandard std : this.ccStandards) {
            sb.append(std.getCode() + ",");
        }
        if (sb.length()>0)
            sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public int getNumProbs() {
        return numProbs;
    }

    public void setNumProbs(int numProbs) {
        this.numProbs = numProbs;
    }

    public int[] getProblemsByGrade() { return problemsByGrade; }

    public void setNumProbsByGrade(int[] problemsByGrade) {
        this.problemsByGrade = problemsByGrade;
    }

    // sort based on the previous indices set on the topics
    public int compareTo (Topic other) {
        int diff =  this.getOldSeqPos() - other.getOldSeqPos();
        return diff;
    }

    public String toString () {
        return " id: " + this.id + " curPos: " + this.getSeqPos() + " prevPos: " + this.getOldSeqPos() +  " name: " + this.name ;
    }
}
