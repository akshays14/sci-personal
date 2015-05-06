package com.sciencescape.ds.db.mysql.coredb;

/**
 * Class to contain fields related to venue entity.
 *
 * @author akshay
 * @version 0.1
 */
public class VenueFields {

    /** !< publisher name */
    private String publisher;
    /** !< venue name */
    private String venue;

    /**
     * @param pPublisher publisher name
     * @param pVenue venue name
     */
    public VenueFields(final String pPublisher, final String pVenue) {
        this.publisher = pPublisher;
        this.venue = pVenue;
    }

    /**
     * @return the publisher
     */
    public final String getPublisher() {
        return publisher;
    }

    /**
     * @param pPublisher the publisher to set
     */
    public final void setPublisher(final String pPublisher) {
        this.publisher = pPublisher;
    }

    /**
     * @return the venue
     */
    public final String getVenue() {
        return venue;
    }

    /**
     * @param pVenue the venue to set
     */
    public final void setVenue(final String pVenue) {
        this.venue = pVenue;
    }
}
