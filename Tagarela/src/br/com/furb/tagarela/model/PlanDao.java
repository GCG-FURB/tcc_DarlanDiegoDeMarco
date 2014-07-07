package br.com.furb.tagarela.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import br.com.furb.tagarela.model.Plan;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PLAN.
*/
public class PlanDao extends AbstractDao<Plan, Long> {

    public static final String TABLENAME = "PLAN";

    /**
     * Properties of entity Plan.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ServerID = new Property(0, Integer.class, "serverID", false, "SERVER_ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property PlanType = new Property(2, Integer.class, "planType", false, "PLAN_TYPE");
        public final static Property Layout = new Property(3, Integer.class, "layout", false, "LAYOUT");
        public final static Property Description = new Property(4, String.class, "description", false, "DESCRIPTION");
        public final static Property UserID = new Property(5, Integer.class, "userID", false, "USER_ID");
        public final static Property PatientID = new Property(6, Integer.class, "patientID", false, "PATIENT_ID");
        public final static Property IsSynchronized = new Property(7, Boolean.class, "isSynchronized", false, "IS_SYNCHRONIZED");
        public final static Property Id = new Property(8, Long.class, "id", true, "_id");
    };


    public PlanDao(DaoConfig config) {
        super(config);
    }
    
    public PlanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PLAN' (" + //
                "'SERVER_ID' INTEGER," + // 0: serverID
                "'NAME' TEXT," + // 1: name
                "'PLAN_TYPE' INTEGER," + // 2: planType
                "'LAYOUT' INTEGER," + // 3: layout
                "'DESCRIPTION' TEXT," + // 4: description
                "'USER_ID' INTEGER," + // 5: userID
                "'PATIENT_ID' INTEGER," + // 6: patientID
                "'IS_SYNCHRONIZED' INTEGER," + // 7: isSynchronized
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT );"); // 8: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PLAN'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Plan entity) {
        stmt.clearBindings();
 
        Integer serverID = entity.getServerID();
        if (serverID != null) {
            stmt.bindLong(1, serverID);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        Integer planType = entity.getPlanType();
        if (planType != null) {
            stmt.bindLong(3, planType);
        }
 
        Integer layout = entity.getLayout();
        if (layout != null) {
            stmt.bindLong(4, layout);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }
 
        Integer userID = entity.getUserID();
        if (userID != null) {
            stmt.bindLong(6, userID);
        }
 
        Integer patientID = entity.getPatientID();
        if (patientID != null) {
            stmt.bindLong(7, patientID);
        }
 
        Boolean isSynchronized = entity.getIsSynchronized();
        if (isSynchronized != null) {
            stmt.bindLong(8, isSynchronized ? 1l: 0l);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(9, id);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8);
    }    

    /** @inheritdoc */
    @Override
    public Plan readEntity(Cursor cursor, int offset) {
        Plan entity = new Plan( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // serverID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // planType
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // layout
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // description
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // userID
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // patientID
            cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0, // isSynchronized
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8) // id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Plan entity, int offset) {
        entity.setServerID(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPlanType(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setLayout(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUserID(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setPatientID(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setIsSynchronized(cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0);
        entity.setId(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Plan entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Plan entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
