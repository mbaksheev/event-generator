package org.example.eventgenerator.common.event;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Represents Interaction Event.
 */
public interface Event {
    UUID getInteractionId();
    String getEventType();
    DateTime getEventTimeStamp();
    DateTime getCreateTime();
    DateTime getDeliveryTime();
    DateTime getEndTime();
    String getServiceType();
    String getOriginationPage();
    String getAgentId();
    String getEndReason();
    String getOriginationChannel();
}
