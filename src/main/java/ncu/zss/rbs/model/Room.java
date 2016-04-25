package ncu.zss.rbs.model;

public class Room extends RoomKey {
    private Short capacity;

    private Boolean hasmultimedia;

    public Short getCapacity() {
        return capacity;
    }

    public void setCapacity(Short capacity) {
        this.capacity = capacity;
    }

    public Boolean getHasmultimedia() {
        return hasmultimedia;
    }

    public void setHasmultimedia(Boolean hasmultimedia) {
        this.hasmultimedia = hasmultimedia;
    }
}