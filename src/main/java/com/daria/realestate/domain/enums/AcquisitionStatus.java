package com.daria.realestate.domain.enums;

public enum AcquisitionStatus {
    /**
     * The estate is open for buyers
     */
    OPEN,
    /**
     *  The estate has a potential buyer and the documents are being signed
     */
    ON_HOLD,
    /**
     * The estate is sold, no longer available
     */
    SOLD
}
