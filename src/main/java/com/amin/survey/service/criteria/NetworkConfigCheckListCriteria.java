package com.amin.survey.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.amin.survey.domain.NetworkConfigCheckList} entity. This class is used
 * in {@link com.amin.survey.web.rest.NetworkConfigCheckListResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /network-config-check-lists?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NetworkConfigCheckListCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter dhcp;

    private BooleanFilter dns;

    private BooleanFilter activeDirectory;

    private BooleanFilter sharedDrives;

    private BooleanFilter mailServer;

    private BooleanFilter firewalls;

    private BooleanFilter loadBalancing;

    private BooleanFilter networkMonitoring;

    private BooleanFilter antivirus;

    private BooleanFilter integratedSystems;

    private BooleanFilter antiSpam;

    private BooleanFilter wpa;

    private BooleanFilter autoBackup;

    private BooleanFilter physicalSecurity;

    private BooleanFilter storageServer;

    private BooleanFilter securityAudit;

    private BooleanFilter disasterRecovery;

    private BooleanFilter proxyServer;

    private BooleanFilter wdsServer;

    private LongFilter formId;

    private Boolean distinct;

    public NetworkConfigCheckListCriteria() {}

    public NetworkConfigCheckListCriteria(NetworkConfigCheckListCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dhcp = other.dhcp == null ? null : other.dhcp.copy();
        this.dns = other.dns == null ? null : other.dns.copy();
        this.activeDirectory = other.activeDirectory == null ? null : other.activeDirectory.copy();
        this.sharedDrives = other.sharedDrives == null ? null : other.sharedDrives.copy();
        this.mailServer = other.mailServer == null ? null : other.mailServer.copy();
        this.firewalls = other.firewalls == null ? null : other.firewalls.copy();
        this.loadBalancing = other.loadBalancing == null ? null : other.loadBalancing.copy();
        this.networkMonitoring = other.networkMonitoring == null ? null : other.networkMonitoring.copy();
        this.antivirus = other.antivirus == null ? null : other.antivirus.copy();
        this.integratedSystems = other.integratedSystems == null ? null : other.integratedSystems.copy();
        this.antiSpam = other.antiSpam == null ? null : other.antiSpam.copy();
        this.wpa = other.wpa == null ? null : other.wpa.copy();
        this.autoBackup = other.autoBackup == null ? null : other.autoBackup.copy();
        this.physicalSecurity = other.physicalSecurity == null ? null : other.physicalSecurity.copy();
        this.storageServer = other.storageServer == null ? null : other.storageServer.copy();
        this.securityAudit = other.securityAudit == null ? null : other.securityAudit.copy();
        this.disasterRecovery = other.disasterRecovery == null ? null : other.disasterRecovery.copy();
        this.proxyServer = other.proxyServer == null ? null : other.proxyServer.copy();
        this.wdsServer = other.wdsServer == null ? null : other.wdsServer.copy();
        this.formId = other.formId == null ? null : other.formId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NetworkConfigCheckListCriteria copy() {
        return new NetworkConfigCheckListCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getDhcp() {
        return dhcp;
    }

    public BooleanFilter dhcp() {
        if (dhcp == null) {
            dhcp = new BooleanFilter();
        }
        return dhcp;
    }

    public void setDhcp(BooleanFilter dhcp) {
        this.dhcp = dhcp;
    }

    public BooleanFilter getDns() {
        return dns;
    }

    public BooleanFilter dns() {
        if (dns == null) {
            dns = new BooleanFilter();
        }
        return dns;
    }

    public void setDns(BooleanFilter dns) {
        this.dns = dns;
    }

    public BooleanFilter getActiveDirectory() {
        return activeDirectory;
    }

    public BooleanFilter activeDirectory() {
        if (activeDirectory == null) {
            activeDirectory = new BooleanFilter();
        }
        return activeDirectory;
    }

    public void setActiveDirectory(BooleanFilter activeDirectory) {
        this.activeDirectory = activeDirectory;
    }

    public BooleanFilter getSharedDrives() {
        return sharedDrives;
    }

    public BooleanFilter sharedDrives() {
        if (sharedDrives == null) {
            sharedDrives = new BooleanFilter();
        }
        return sharedDrives;
    }

    public void setSharedDrives(BooleanFilter sharedDrives) {
        this.sharedDrives = sharedDrives;
    }

    public BooleanFilter getMailServer() {
        return mailServer;
    }

    public BooleanFilter mailServer() {
        if (mailServer == null) {
            mailServer = new BooleanFilter();
        }
        return mailServer;
    }

    public void setMailServer(BooleanFilter mailServer) {
        this.mailServer = mailServer;
    }

    public BooleanFilter getFirewalls() {
        return firewalls;
    }

    public BooleanFilter firewalls() {
        if (firewalls == null) {
            firewalls = new BooleanFilter();
        }
        return firewalls;
    }

    public void setFirewalls(BooleanFilter firewalls) {
        this.firewalls = firewalls;
    }

    public BooleanFilter getLoadBalancing() {
        return loadBalancing;
    }

    public BooleanFilter loadBalancing() {
        if (loadBalancing == null) {
            loadBalancing = new BooleanFilter();
        }
        return loadBalancing;
    }

    public void setLoadBalancing(BooleanFilter loadBalancing) {
        this.loadBalancing = loadBalancing;
    }

    public BooleanFilter getNetworkMonitoring() {
        return networkMonitoring;
    }

    public BooleanFilter networkMonitoring() {
        if (networkMonitoring == null) {
            networkMonitoring = new BooleanFilter();
        }
        return networkMonitoring;
    }

    public void setNetworkMonitoring(BooleanFilter networkMonitoring) {
        this.networkMonitoring = networkMonitoring;
    }

    public BooleanFilter getAntivirus() {
        return antivirus;
    }

    public BooleanFilter antivirus() {
        if (antivirus == null) {
            antivirus = new BooleanFilter();
        }
        return antivirus;
    }

    public void setAntivirus(BooleanFilter antivirus) {
        this.antivirus = antivirus;
    }

    public BooleanFilter getIntegratedSystems() {
        return integratedSystems;
    }

    public BooleanFilter integratedSystems() {
        if (integratedSystems == null) {
            integratedSystems = new BooleanFilter();
        }
        return integratedSystems;
    }

    public void setIntegratedSystems(BooleanFilter integratedSystems) {
        this.integratedSystems = integratedSystems;
    }

    public BooleanFilter getAntiSpam() {
        return antiSpam;
    }

    public BooleanFilter antiSpam() {
        if (antiSpam == null) {
            antiSpam = new BooleanFilter();
        }
        return antiSpam;
    }

    public void setAntiSpam(BooleanFilter antiSpam) {
        this.antiSpam = antiSpam;
    }

    public BooleanFilter getWpa() {
        return wpa;
    }

    public BooleanFilter wpa() {
        if (wpa == null) {
            wpa = new BooleanFilter();
        }
        return wpa;
    }

    public void setWpa(BooleanFilter wpa) {
        this.wpa = wpa;
    }

    public BooleanFilter getAutoBackup() {
        return autoBackup;
    }

    public BooleanFilter autoBackup() {
        if (autoBackup == null) {
            autoBackup = new BooleanFilter();
        }
        return autoBackup;
    }

    public void setAutoBackup(BooleanFilter autoBackup) {
        this.autoBackup = autoBackup;
    }

    public BooleanFilter getPhysicalSecurity() {
        return physicalSecurity;
    }

    public BooleanFilter physicalSecurity() {
        if (physicalSecurity == null) {
            physicalSecurity = new BooleanFilter();
        }
        return physicalSecurity;
    }

    public void setPhysicalSecurity(BooleanFilter physicalSecurity) {
        this.physicalSecurity = physicalSecurity;
    }

    public BooleanFilter getStorageServer() {
        return storageServer;
    }

    public BooleanFilter storageServer() {
        if (storageServer == null) {
            storageServer = new BooleanFilter();
        }
        return storageServer;
    }

    public void setStorageServer(BooleanFilter storageServer) {
        this.storageServer = storageServer;
    }

    public BooleanFilter getSecurityAudit() {
        return securityAudit;
    }

    public BooleanFilter securityAudit() {
        if (securityAudit == null) {
            securityAudit = new BooleanFilter();
        }
        return securityAudit;
    }

    public void setSecurityAudit(BooleanFilter securityAudit) {
        this.securityAudit = securityAudit;
    }

    public BooleanFilter getDisasterRecovery() {
        return disasterRecovery;
    }

    public BooleanFilter disasterRecovery() {
        if (disasterRecovery == null) {
            disasterRecovery = new BooleanFilter();
        }
        return disasterRecovery;
    }

    public void setDisasterRecovery(BooleanFilter disasterRecovery) {
        this.disasterRecovery = disasterRecovery;
    }

    public BooleanFilter getProxyServer() {
        return proxyServer;
    }

    public BooleanFilter proxyServer() {
        if (proxyServer == null) {
            proxyServer = new BooleanFilter();
        }
        return proxyServer;
    }

    public void setProxyServer(BooleanFilter proxyServer) {
        this.proxyServer = proxyServer;
    }

    public BooleanFilter getWdsServer() {
        return wdsServer;
    }

    public BooleanFilter wdsServer() {
        if (wdsServer == null) {
            wdsServer = new BooleanFilter();
        }
        return wdsServer;
    }

    public void setWdsServer(BooleanFilter wdsServer) {
        this.wdsServer = wdsServer;
    }

    public LongFilter getFormId() {
        return formId;
    }

    public LongFilter formId() {
        if (formId == null) {
            formId = new LongFilter();
        }
        return formId;
    }

    public void setFormId(LongFilter formId) {
        this.formId = formId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NetworkConfigCheckListCriteria that = (NetworkConfigCheckListCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dhcp, that.dhcp) &&
            Objects.equals(dns, that.dns) &&
            Objects.equals(activeDirectory, that.activeDirectory) &&
            Objects.equals(sharedDrives, that.sharedDrives) &&
            Objects.equals(mailServer, that.mailServer) &&
            Objects.equals(firewalls, that.firewalls) &&
            Objects.equals(loadBalancing, that.loadBalancing) &&
            Objects.equals(networkMonitoring, that.networkMonitoring) &&
            Objects.equals(antivirus, that.antivirus) &&
            Objects.equals(integratedSystems, that.integratedSystems) &&
            Objects.equals(antiSpam, that.antiSpam) &&
            Objects.equals(wpa, that.wpa) &&
            Objects.equals(autoBackup, that.autoBackup) &&
            Objects.equals(physicalSecurity, that.physicalSecurity) &&
            Objects.equals(storageServer, that.storageServer) &&
            Objects.equals(securityAudit, that.securityAudit) &&
            Objects.equals(disasterRecovery, that.disasterRecovery) &&
            Objects.equals(proxyServer, that.proxyServer) &&
            Objects.equals(wdsServer, that.wdsServer) &&
            Objects.equals(formId, that.formId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dhcp,
            dns,
            activeDirectory,
            sharedDrives,
            mailServer,
            firewalls,
            loadBalancing,
            networkMonitoring,
            antivirus,
            integratedSystems,
            antiSpam,
            wpa,
            autoBackup,
            physicalSecurity,
            storageServer,
            securityAudit,
            disasterRecovery,
            proxyServer,
            wdsServer,
            formId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NetworkConfigCheckListCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dhcp != null ? "dhcp=" + dhcp + ", " : "") +
            (dns != null ? "dns=" + dns + ", " : "") +
            (activeDirectory != null ? "activeDirectory=" + activeDirectory + ", " : "") +
            (sharedDrives != null ? "sharedDrives=" + sharedDrives + ", " : "") +
            (mailServer != null ? "mailServer=" + mailServer + ", " : "") +
            (firewalls != null ? "firewalls=" + firewalls + ", " : "") +
            (loadBalancing != null ? "loadBalancing=" + loadBalancing + ", " : "") +
            (networkMonitoring != null ? "networkMonitoring=" + networkMonitoring + ", " : "") +
            (antivirus != null ? "antivirus=" + antivirus + ", " : "") +
            (integratedSystems != null ? "integratedSystems=" + integratedSystems + ", " : "") +
            (antiSpam != null ? "antiSpam=" + antiSpam + ", " : "") +
            (wpa != null ? "wpa=" + wpa + ", " : "") +
            (autoBackup != null ? "autoBackup=" + autoBackup + ", " : "") +
            (physicalSecurity != null ? "physicalSecurity=" + physicalSecurity + ", " : "") +
            (storageServer != null ? "storageServer=" + storageServer + ", " : "") +
            (securityAudit != null ? "securityAudit=" + securityAudit + ", " : "") +
            (disasterRecovery != null ? "disasterRecovery=" + disasterRecovery + ", " : "") +
            (proxyServer != null ? "proxyServer=" + proxyServer + ", " : "") +
            (wdsServer != null ? "wdsServer=" + wdsServer + ", " : "") +
            (formId != null ? "formId=" + formId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
