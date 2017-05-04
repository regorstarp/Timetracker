

import java.io.Serializable;
import java.util.Date;
/**
 * Contains all the information of a period of time
 * Startd and end date, and also the duration
 * It is able to tell if a given timelapse intersects with
 * its own timelapse.
 * @author rogerprats
 *
 */

@SuppressWarnings("serial")
public class TimeLapse implements Serializable {
    /**
     * Start date of the timelapse.
     * @uml.property  name="startDate"
     */
    private Date startDate;
    /**
     * Getter of the property <tt>startDate</tt>
     * @return  Returns the startDate.
     * @uml.property  name="startDate"
     */
    public final Date getStartDate() {
        return startDate;
    }
    /**
     * Setter of the property <tt>startDate</tt>
     * @param aStartDate  The startDate to set.
     * @uml.property  name="startDate"
     */
    public final void setStartDate(final Date aStartDate) {
        this.startDate = aStartDate;
    }
    /**
     * End date of the timelapse
     * @uml.property  name="endDate"
     */
    private Date endDate;
    /**
     * Getter of the property <tt>endDate</tt>
     * @return  Returns the endDate.
     * @uml.property  name="endDate"
     */
    public final Date getEndDate() {
        return endDate;
    }
    /**
     * Setter of the property <tt>endDate</tt>
     * @param endDate  The endDate to set.
     * @uml.property  name="endDate"
     */
    public final void setEndDate(final Date newEndDate) {
        this.endDate = newEndDate;
    }
    /**
     * Duration of the timelapse
     * @uml.property  name="duration"
     */
    private long duration = 0;
    /**
     * Getter of the property <tt>duration</tt>
     * @return  Returns the duration.
     * @uml.property  name="duration"
     */
    public final long getDuration() {
        return duration;
    }
    /**
     * Updates the duration of the timelapse by adding the
     * given time passed to the current duration.
     */
    public final void update(final long timePassed) {
        //pre
        assert (timePassed > 0) : "timePassed not greater than 0";
        // to check later in post
        Date preStartDate = getStartDate();
        Date preEndDate = getEndDate();
        long preDuration = getDuration();
        this.duration += timePassed;
        //post
        assert (this.duration == preDuration + timePassed)
        : "duration not updated correctly";
        assert (preStartDate == getStartDate()) : "start date changed";
        assert (preEndDate == getEndDate()) : "end date changed";
        checkInvariant();
    }
    /**
     * Tells whether or not a given timelapse intersects with
     * its own timelapse.
     */
    public final Boolean intersect(final TimeLapse timeLapse) {
        //(StartA <= EndB) and (EndA >= StartB)
        if (timeLapse == null) {
            throw new IllegalArgumentException(
                    "null timelapse");
        }
        assert (getStartDate().before(getEndDate()))
        : "start date after end date";
        assert (timeLapse.getStartDate().before(timeLapse.getEndDate()))
        : "start date after end date";
        return (getStartDate().compareTo(timeLapse.getEndDate()) <= 0
        && getEndDate().compareTo(timeLapse.getStartDate()) >= 0);
    }
    /**
     * Checks that the class invariants are always respected.
     */
    private void checkInvariant() {
        assert (getStartDate() != null && getEndDate() != null
                && getDuration() >= 0)
                : "Dates can't be null and duration has to be bigger than 0";
        assert (getStartDate() != null) : "null start date";
        assert (getEndDate() != null) : "null end date";
        assert (getDuration() >= 0) : "negative duration";
        if (!getStartDate().equals(getEndDate())) {
            assert (getStartDate().before(getEndDate()))
            : "start date after end date";
        }
        }
    }
