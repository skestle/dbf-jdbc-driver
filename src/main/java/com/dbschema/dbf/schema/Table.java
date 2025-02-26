package com.dbschema.dbf.schema;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Wise Coders GmbH https://wisecoders.com
 * Driver is used in the DbSchema Database Designer https://dbschema.com
 * Free to be used by everyone.
 * Code modifications allowed only to GitHub repository https://github.com/wise-coders/dbf-jdbc-driver
 */
public class Table {


    public final String name;
    public final List<DBFField> fields = new ArrayList<>();


    Table(String name ){
        this.name = name;
    }

    public Table(File rootFolder, File tableFile) {
        String path = rootFolder.toURI().relativize(tableFile.toURI()).getPath();
        if ( path.toLowerCase().endsWith(".dbf")){
            path = path.substring(0, path.length() - ".dbf".length());
        }
        this.name = path;
    }

    public void addField( DBFField field ){
        fields.add( field );
    }

    public DBFField createField(String name, String type, int length, int decimal  ) {
        DBFField field = new DBFField();
        if ( name.length() > 10 ){
            name = name.substring(0, 10);
        }
        field.setName( name );

        switch (type.toLowerCase() ){
            case "double":
            case "decimal":
                field.setType( DBFDataType.NUMERIC );
                field.setLength( length);
                field.setDecimalCount( decimal);
                break;
            case "float": field.setType( DBFDataType.FLOATING_POINT ); break;
            case "int": field.setType( DBFDataType.AUTOINCREMENT ); break;
            case "bigint": field.setType( DBFDataType.NUMERIC ); break;
            case "boolean": field.setType( DBFDataType.LOGICAL ); break;
            case "date": field.setType( DBFDataType.DATE ); break;
            case "bit": field.setType( DBFDataType.NULL_FLAGS ); break;
            case "longvarchar":field.setType( DBFDataType.MEMO ); break;
            case "timestamp": field.setType( DBFDataType.TIMESTAMP ); break;
            case "timestampwithtimezone": field.setType( DBFDataType.TIMESTAMP_DBASE7 ); break;
            default : field.setType( DBFDataType.CHARACTER ); break;
        }
        fields.add( field );
        return field;
    }

    public DBFField[] getDBFFields(){
        return fields.toArray( new DBFField[]{});
    }

    @Override
    public String toString() {
        String ret =  name + "(\n";
        for ( DBFField field : fields ){
            ret += field.getName() + " " + field.getType() + " (" +field.getLength() + ", " + field.getDecimalCount() + " ) " + ( field.isNullable() ? "" : "NOT NULL ") + "\n";
        }
        ret +=")";
        return ret;
    }

}



