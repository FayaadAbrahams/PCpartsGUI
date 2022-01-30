package za.ac.cput.adp2.mymavendb.domain;

public class Parts {

    private String pcPartID;
    private String pcPartName;
    private String pcPartPrice;

    public Parts(String pcPartID, String pcPartName, String pcPartPrice) {
        this.pcPartID = pcPartID;
        this.pcPartName = pcPartName;
        this.pcPartPrice = pcPartPrice;
    }

    @Override
    public String toString() {
        return "PC Part ID" + getPcPartID() + "PC Part Name: " + getPcPartName() + "\nPC part Price: R" + getPcPartPrice();
    }

    public String getPcPartID() {
        return pcPartID;
    }

    public void setPcPartID(String pcPartID) {
        this.pcPartID = pcPartID;
    }

    public String getPcPartName() {
        return pcPartName;
    }

    public void setPcPartName(String pcPartName) {
        this.pcPartName = pcPartName;
    }

    public String getPcPartPrice() {
        return pcPartPrice;
    }

    public void setPcPartPrice(String pcPartPrice) {
        this.pcPartPrice = pcPartPrice;
    }

}
