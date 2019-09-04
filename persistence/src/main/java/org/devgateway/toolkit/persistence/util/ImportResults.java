package org.devgateway.toolkit.persistence.util;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
@Service
public class ImportResults<T> {

    private boolean importOkFlag = true;

    private List<String> infoList = new ArrayList<>();

    private List<String> warnList = new ArrayList<>();

    private List<String> errorList = new ArrayList<>();

    private List<T> dataInstances = new ArrayList<>();



    public boolean isImportOkFlag() {
        return importOkFlag;
    }

    public void setImportOkFlag(boolean importOkFlag) {
        this.importOkFlag = importOkFlag;
    }

    public List<String> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<String> infoList) {
        this.infoList = infoList;
    }

    public List<String> getWarnList() {
        return warnList;
    }

    public void setWarnList(List<String> warnList) {
        this.warnList = warnList;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public List<T> getDataInstances() {
        return dataInstances;
    }

    public void setDataInstances(List<T> dataInstances) {
        this.dataInstances = dataInstances;
    }

    public void addInfo(final String info) {
        infoList.add(info);
    }

    public void addError(final String error) {
        errorList.add(error);
    }

    public void addWarn(final String warn) {
        warnList.add(warn);
    }

    public void addDataInstance(final T dataInstance) {
        dataInstances.add(dataInstance);
    }
}
