package dev.mattrm.mc.gametools.data;

import dev.mattrm.mc.gametools.Service;

import java.util.ArrayList;
import java.util.List;

public class DataService extends Service {
    private static final DataService INSTANCE = new DataService();

    private final List<IDataManager> dataManagerList = new ArrayList<>();

    public static DataService getInstance() {
        return INSTANCE;
    }

    public void loadAll() {
        this.dataManagerList.forEach(IDataManager::loadData);
    }

    public void saveAll() {
        this.dataManagerList.forEach(IDataManager::saveData);
    }

    public void registerDataManager(IDataManager dataManager) {
        this.dataManagerList.add(dataManager);
        dataManager.loadData();
    }
}
