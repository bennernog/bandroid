package mobile.bny.batadase;

/**
 * Created by BNY on 11/11/2017.
 */

public class BatadaseColumn {

    public enum DataType{
        NULL,               // The null value
        INTEGER,            // Any number which is no floating point number
        REAL,               // Floating-point numbers (8-Byte IEEE 754 - i.e. double precision)
        TEXT,               // Any String and also single characters (UTF-8, UTF-16BE or UTF-16LE)
        BLOB                // A binary blob of data
    }

    private String Key;
    public String getKey() {
        return Key;
    }

    private DataType Type;
    public DataType getType() {
        return Type;
    }

    private boolean IsPrimary;
    public boolean isPrimary() {
        return IsPrimary;
    }
    public void setPrimary(boolean primary) {
        IsPrimary = primary;
    }

    public BatadaseColumn(String key, DataType type, boolean isPrimary) {
        Key = key;
        Type = type;
        IsPrimary = isPrimary;
    }

    public BatadaseColumn(String key, DataType type) {
        Key = key;
        Type = type;
        IsPrimary = false;
    }

    @Override
    public String toString() {
        if(IsPrimary)
            return String.format("%s %s PRIMARY KEY", Key, Type.name());
        else
            return String.format("%s %s", Key, Type.name());
    }
}