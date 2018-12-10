package mobile.bny.batadase;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by BNY on 11/11/2017.
 */

public class BatadaseTable {

    public BatadaseTable(@NonNull String name) {
        this(name, new BatadaseColumn[0]);
    }

    public BatadaseTable(@NonNull String name, BatadaseColumn... columns) {
        Name = name;
        hasMultiplePrimaryKeys=false;
        addColumns(columns);
    }

    private ArrayList<BatadaseColumn> columnArrayList, primaries;
    public BatadaseColumn[] getColumns() {
        return columnArrayList.toArray(new BatadaseColumn[0]);
    }

    private String Name;
    public String getName() {
        return Name;
    }

    public void addColumn(@NonNull BatadaseColumn column){
        if(columnArrayList == null){
            columnArrayList = new ArrayList<>();
            primaries = new ArrayList<>();
        }
        columnArrayList.add(column);

        if(column.isPrimary())
            primaries.add(column);

        hasMultiplePrimaryKeys = primaries.size()>1;
    }

    public void addColumns(@NonNull BatadaseColumn... columns){
        if(columns.length == 0)
            return;

        for (BatadaseColumn column : columns)
            addColumn(column);
    }

    public void removeColumn(BatadaseColumn column){
        if(columnArrayList == null)
            return;
        columnArrayList.remove(column);
        if(column.isPrimary())
            primaries.remove(column);
    }

    public void removeColumns(BatadaseColumn... columns){
        if(columns.length == 0)
            return;

        for (BatadaseColumn column : columns)
            removeColumn(column);
    }

    @Override
    public String toString() {
        if(columnArrayList == null || columnArrayList.size() == 0)
            return String.format(
                    "DROP TABLE IF EXISTS %s ",
                    Name);

        ArrayList<String> entries = new ArrayList<>();
        if (hasMultiplePrimaryKeys){

            final ArrayList<BatadaseColumn> temp = removePrimary();

            for(BatadaseColumn column : temp)
                entries.add(column.toString());

            entries.add(getPrimaries());

        } else {
            for(BatadaseColumn column : columnArrayList)
                entries.add(column.toString());
        }

        StringBuilder builder = new StringBuilder(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s (%s",
                        Name,
                        entries.get(0)));

        for (int i = 1; i < entries.size(); i++) {
            builder.append(", ");
            builder.append(entries.get(i));
        }
            builder.append(")");

        return builder.toString();
    }

    private boolean hasMultiplePrimaryKeys;
    private String getPrimaries(){

        StringBuilder builder = new StringBuilder("PRIMARY KEY(");

            builder.append(primaries.get(0).getKey());

        for (int i = 1; i < primaries.size(); i++) {

            builder.append(", ");
            builder.append(primaries.get(i).getKey());
        }
            builder.append(")");

        return builder.toString();
    }

    private ArrayList<BatadaseColumn> removePrimary(){
        ArrayList<BatadaseColumn> result = new ArrayList<>(columnArrayList);
        for (BatadaseColumn column : result) {
            column.setPrimary(false);
        }
        return result;
    }

    public String getPrimaryKey(){
        if(hasPrimaryKey())
            return primaries.get(0).getKey();
        else
            return null;
    }

    public BatadaseColumn getPrimaryColumn(){
        if(hasPrimaryKey())
            return primaries.get(0);
        else
            return null;
    }

    public boolean hasPrimaryKey(){
        return primaries != null && primaries.size() > 0;
    }
}