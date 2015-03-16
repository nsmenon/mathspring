package edu.umass.ckc.wo.tutor.response;

import edu.umass.ckc.wo.event.SessionEvent;
import edu.umass.ckc.wo.tutor.pedModel.EndOfTopicInfo;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 3/11/15
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class EndOfTopicEvent extends InternalEvent {
    private EndOfTopicInfo endOfTopicInfo;
    private int topicId;

    public EndOfTopicEvent(SessionEvent sessionEvent, EndOfTopicInfo info, int topicId) {
        super(sessionEvent,"EndOfTopic");
        this.endOfTopicInfo=info;
        this.topicId=topicId;
    }

    public int getTopicId() {
        return topicId;
    }
}
