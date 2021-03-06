package ca.ubc.cpsc304.model;

import java.sql.*;

public class RentModel {
    private final int rid;
    private final String vlicense;
    private final String dlicense;
    private final Timestamp fromDateTime;
    private final Timestamp toDateTime;
    private final int odometer;
    private final String cardName;
    private final String cardNo;
    private final String expDate;
    private final int confNo;

    public RentModel (int rid, String vlicense, String dlicense, Timestamp fromDateTime, Timestamp toDateTime,
                      int odometer, String cardName, String cardNo, String expDate, int confNo) {
        this.rid = rid;
        this.vlicense = vlicense;
        this.dlicense = dlicense;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.odometer = odometer;
        this.cardName = cardName;
        this.cardNo = cardNo;
        this.expDate = expDate;
        this.confNo = confNo;
    }

    public int getRid() {
        return rid;
    }

    public String getVlicense() {
        return vlicense;
    }

    public String getDlicense() {
        return dlicense;
    }

    public Timestamp getFromDateTime() {
        return fromDateTime;
    }

    public Timestamp getToDateTime() {
        return toDateTime;
    }

    public int getOdometer() {
        return odometer;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public int getConfNo() {
        return confNo;
    }
}
