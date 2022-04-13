package model;

import util.Util;

import java.time.LocalTime;

import static util.Util.COMPANY_GROTTY;
import static util.Util.COMPANY_POSH;

public class BusCompany implements Comparable<BusCompany> {
    private String companyName;
    private LocalTime timeDeparture;
    private LocalTime timeArrival;

    public BusCompany(String[] list) {
        for (String s : list) {
            String[] object = s.split(" ");
            this.companyName = object[0];
            this.timeDeparture = Util.parseTime(object[1]);
            this.timeArrival = Util.parseTime(object[2]);
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public LocalTime getTimeDeparture() {
        return timeDeparture;
    }

    public LocalTime getTimeArrival() {
        return timeArrival;
    }

    @Override
    public String toString() {
        return getCompanyName() + " " + getTimeDeparture() + " " + getTimeArrival();
    }

    @Override
    public int compareTo(BusCompany that) {
        final int comparisonResult;

        if (this.isBetterThan(that)) {
            comparisonResult = 1;
        } else if (that.isBetterThan(this)) {
            comparisonResult = -1;
        } else if (this.hasSameSpeedAs(that)) {
            if (COMPANY_POSH.equals(this.companyName) && COMPANY_GROTTY.equals(that.companyName)) {
                comparisonResult = 1;
            } else if (COMPANY_GROTTY.equals(this.companyName) && COMPANY_POSH.equals(that.companyName)) {
                comparisonResult = -1;
            } else {
                comparisonResult = 0;
            }
        } else {
            comparisonResult = 0;
        }
        return comparisonResult;
    }

    private boolean isBetterThan(BusCompany that) {
        return (this.timeDeparture.equals(that.timeDeparture) && this.timeArrival.isBefore(that.timeArrival))
                || (this.timeDeparture.isAfter(that.timeDeparture) && this.timeArrival.equals(that.timeArrival))
                || (this.timeDeparture.isAfter(that.timeDeparture) && this.timeArrival.isBefore(that.timeArrival));
    }

    private boolean hasSameSpeedAs(BusCompany that) {
        return this.timeDeparture.equals(that.timeDeparture)
                && this.timeArrival.equals(that.timeArrival);
    }
}
