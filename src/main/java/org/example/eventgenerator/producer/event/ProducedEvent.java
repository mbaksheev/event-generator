package org.example.eventgenerator.producer.event;

import org.example.eventgenerator.common.event.Event;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Event implementation.
 */
public class ProducedEvent implements Event {
    private final UUID interactionId;
    private final String eventType;
    private final DateTime eventTimeStamp;
    private final DateTime createTimeStamp;
    private final DateTime deliveryTime;
    private final DateTime endTime;
    private final String serviceType;
    private final String originationPage;
    private final String agentId;
    private final String endReason;
    private final String originationChannel;

    ProducedEvent(UUID interactionId,
                         String eventType,
                         DateTime eventTimeStamp,
                         DateTime createTimeStamp,
                         DateTime deliveryTime,
                         DateTime endTime,
                         String serviceType,
                         String originationPage,
                         String agentId,
                         String endReason,
                         String originationChannel) {
        this.interactionId = interactionId;
        this.eventType = eventType;
        this.eventTimeStamp = eventTimeStamp;
        this.createTimeStamp = createTimeStamp;
        this.deliveryTime = deliveryTime;
        this.endTime = endTime;
        this.serviceType = serviceType;
        this.originationPage = originationPage;
        this.agentId = agentId;
        this.endReason = endReason;
        this.originationChannel = originationChannel;
    }

    @Override
    public UUID getInteractionId() {
        return interactionId;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public DateTime getEventTimeStamp() {
        return eventTimeStamp;
    }

    @Override
    public DateTime getCreateTime() {
        return createTimeStamp;
    }

    @Override
    public DateTime getDeliveryTime() {
        return deliveryTime;
    }

    @Override
    public DateTime getEndTime() {
        return endTime;
    }

    @Override
    public String getServiceType() {
        return serviceType;
    }

    @Override
    public String getOriginationPage() {
        return originationPage;
    }

    @Override
    public String getAgentId() {
        return agentId;
    }

    @Override
    public String getEndReason() {
        return endReason;
    }

    @Override
    public String getOriginationChannel() {
        return originationChannel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProducedEvent that = (ProducedEvent) o;

        if (interactionId != null ? !interactionId.equals(that.interactionId) : that.interactionId != null)
            return false;
        if (eventType != null ? !eventType.equals(that.eventType) : that.eventType != null) return false;
        if (eventTimeStamp != null ? !eventTimeStamp.equals(that.eventTimeStamp) : that.eventTimeStamp != null)
            return false;
        if (createTimeStamp != null ? !createTimeStamp.equals(that.createTimeStamp) : that.createTimeStamp != null)
            return false;
        if (deliveryTime != null ? !deliveryTime.equals(that.deliveryTime) : that.deliveryTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (serviceType != null ? !serviceType.equals(that.serviceType) : that.serviceType != null) return false;
        if (originationPage != null ? !originationPage.equals(that.originationPage) : that.originationPage != null)
            return false;
        if (agentId != null ? !agentId.equals(that.agentId) : that.agentId != null) return false;
        if (endReason != null ? !endReason.equals(that.endReason) : that.endReason != null) return false;
        return originationChannel != null ? originationChannel.equals(that.originationChannel) : that.originationChannel == null;

    }

    @Override
    public int hashCode() {
        int result = interactionId != null ? interactionId.hashCode() : 0;
        result = 31 * result + (eventType != null ? eventType.hashCode() : 0);
        result = 31 * result + (eventTimeStamp != null ? eventTimeStamp.hashCode() : 0);
        result = 31 * result + (createTimeStamp != null ? createTimeStamp.hashCode() : 0);
        result = 31 * result + (deliveryTime != null ? deliveryTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        result = 31 * result + (originationPage != null ? originationPage.hashCode() : 0);
        result = 31 * result + (agentId != null ? agentId.hashCode() : 0);
        result = 31 * result + (endReason != null ? endReason.hashCode() : 0);
        result = 31 * result + (originationChannel != null ? originationChannel.hashCode() : 0);
        return result;
    }
}
