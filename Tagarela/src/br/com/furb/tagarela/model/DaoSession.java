package br.com.furb.tagarela.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import br.com.furb.tagarela.model.User;
import br.com.furb.tagarela.model.Category;
import br.com.furb.tagarela.model.Symbol;
import br.com.furb.tagarela.model.Plan;
import br.com.furb.tagarela.model.SymbolPlan;
import br.com.furb.tagarela.model.GroupPlan;
import br.com.furb.tagarela.model.GroupPlanRelationship;
import br.com.furb.tagarela.model.Observation;
import br.com.furb.tagarela.model.SymbolHistoric;

import br.com.furb.tagarela.model.UserDao;
import br.com.furb.tagarela.model.CategoryDao;
import br.com.furb.tagarela.model.SymbolDao;
import br.com.furb.tagarela.model.PlanDao;
import br.com.furb.tagarela.model.SymbolPlanDao;
import br.com.furb.tagarela.model.GroupPlanDao;
import br.com.furb.tagarela.model.GroupPlanRelationshipDao;
import br.com.furb.tagarela.model.ObservationDao;
import br.com.furb.tagarela.model.SymbolHistoricDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig categoryDaoConfig;
    private final DaoConfig symbolDaoConfig;
    private final DaoConfig planDaoConfig;
    private final DaoConfig symbolPlanDaoConfig;
    private final DaoConfig groupPlanDaoConfig;
    private final DaoConfig groupPlanRelationshipDaoConfig;
    private final DaoConfig observationDaoConfig;
    private final DaoConfig symbolHistoricDaoConfig;

    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final SymbolDao symbolDao;
    private final PlanDao planDao;
    private final SymbolPlanDao symbolPlanDao;
    private final GroupPlanDao groupPlanDao;
    private final GroupPlanRelationshipDao groupPlanRelationshipDao;
    private final ObservationDao observationDao;
    private final SymbolHistoricDao symbolHistoricDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        categoryDaoConfig = daoConfigMap.get(CategoryDao.class).clone();
        categoryDaoConfig.initIdentityScope(type);

        symbolDaoConfig = daoConfigMap.get(SymbolDao.class).clone();
        symbolDaoConfig.initIdentityScope(type);

        planDaoConfig = daoConfigMap.get(PlanDao.class).clone();
        planDaoConfig.initIdentityScope(type);

        symbolPlanDaoConfig = daoConfigMap.get(SymbolPlanDao.class).clone();
        symbolPlanDaoConfig.initIdentityScope(type);

        groupPlanDaoConfig = daoConfigMap.get(GroupPlanDao.class).clone();
        groupPlanDaoConfig.initIdentityScope(type);

        groupPlanRelationshipDaoConfig = daoConfigMap.get(GroupPlanRelationshipDao.class).clone();
        groupPlanRelationshipDaoConfig.initIdentityScope(type);

        observationDaoConfig = daoConfigMap.get(ObservationDao.class).clone();
        observationDaoConfig.initIdentityScope(type);

        symbolHistoricDaoConfig = daoConfigMap.get(SymbolHistoricDao.class).clone();
        symbolHistoricDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        categoryDao = new CategoryDao(categoryDaoConfig, this);
        symbolDao = new SymbolDao(symbolDaoConfig, this);
        planDao = new PlanDao(planDaoConfig, this);
        symbolPlanDao = new SymbolPlanDao(symbolPlanDaoConfig, this);
        groupPlanDao = new GroupPlanDao(groupPlanDaoConfig, this);
        groupPlanRelationshipDao = new GroupPlanRelationshipDao(groupPlanRelationshipDaoConfig, this);
        observationDao = new ObservationDao(observationDaoConfig, this);
        symbolHistoricDao = new SymbolHistoricDao(symbolHistoricDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(Category.class, categoryDao);
        registerDao(Symbol.class, symbolDao);
        registerDao(Plan.class, planDao);
        registerDao(SymbolPlan.class, symbolPlanDao);
        registerDao(GroupPlan.class, groupPlanDao);
        registerDao(GroupPlanRelationship.class, groupPlanRelationshipDao);
        registerDao(Observation.class, observationDao);
        registerDao(SymbolHistoric.class, symbolHistoricDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        categoryDaoConfig.getIdentityScope().clear();
        symbolDaoConfig.getIdentityScope().clear();
        planDaoConfig.getIdentityScope().clear();
        symbolPlanDaoConfig.getIdentityScope().clear();
        groupPlanDaoConfig.getIdentityScope().clear();
        groupPlanRelationshipDaoConfig.getIdentityScope().clear();
        observationDaoConfig.getIdentityScope().clear();
        symbolHistoricDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public SymbolDao getSymbolDao() {
        return symbolDao;
    }

    public PlanDao getPlanDao() {
        return planDao;
    }

    public SymbolPlanDao getSymbolPlanDao() {
        return symbolPlanDao;
    }

    public GroupPlanDao getGroupPlanDao() {
        return groupPlanDao;
    }

    public GroupPlanRelationshipDao getGroupPlanRelationshipDao() {
        return groupPlanRelationshipDao;
    }

    public ObservationDao getObservationDao() {
        return observationDao;
    }

    public SymbolHistoricDao getSymbolHistoricDao() {
        return symbolHistoricDao;
    }

}
