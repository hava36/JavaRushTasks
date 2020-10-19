package com.javarush.task.task39.task3911;

import java.util.*;

public class Software {
    int currentVersion;

    private Map<Integer, String> versionHistoryMap = new LinkedHashMap<>();

    public void addNewVersion(int version, String description) {
        if (version > currentVersion) {
            versionHistoryMap.put(version, description);
            currentVersion = version;
        }
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public Map<Integer, String> getVersionHistoryMap() {
        return Collections.unmodifiableMap(versionHistoryMap);
    }

    public boolean rollback(int rollbackVersion) {
        boolean rollback = true;
        if (versionHistoryMap.containsKey(rollbackVersion)) {
            currentVersion = rollbackVersion;
            int index = rollbackVersion + 1;
            while (versionHistoryMap.size() > rollbackVersion) {
                if (versionHistoryMap.containsKey(index)) {
                    versionHistoryMap.remove(index);
                    rollback = true;
                }
                index++;
            }
        } else {
            rollback = false;
        }
        return rollback;
    }
}
