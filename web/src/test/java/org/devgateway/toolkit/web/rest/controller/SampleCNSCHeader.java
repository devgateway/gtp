package org.devgateway.toolkit.web.rest.controller;

import org.devgateway.toolkit.persistence.dao.menu.CNSCHeader;
import org.devgateway.toolkit.persistence.dao.menu.MenuGroup;
import org.devgateway.toolkit.persistence.dao.menu.MenuLeaf;

/**
 * @author Nadejda Mandrescu
 */
public class SampleCNSCHeader {

    private CNSCHeader cnscHeader;

    public SampleCNSCHeader() {
        cnscHeader = new CNSCHeader(1L, "http://www.anacim.sn/cnsc/?s=");
        cnscHeader.setLogoUrl("http://www.anacim.sn/");

        MenuGroup root = new MenuGroup(1L, MenuGroup.ROOT, "CNSC Header", null);

        root.addItem(new MenuLeaf(2L, "Accueil", 1, "http://www.anacim.sn/cnsc/"));

        MenuGroup groupL1N1 = new MenuGroup(3L, "Rubriques", "Rubriques", 2);
        root.addItem(groupL1N1);
        groupL1N1.addItem(new MenuLeaf(4L, "Elevage", 1, "http://www.anacim.sn/cnsc/elevage/"));

        cnscHeader.setMenu(root);
    }

    public CNSCHeader getCNSCHeader() {
        return cnscHeader;
    }
}
