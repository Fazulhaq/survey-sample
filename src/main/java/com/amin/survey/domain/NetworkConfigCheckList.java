package com.amin.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A NetworkConfigCheckList.
 */
@Entity
@Table(name = "network_config_check_list")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NetworkConfigCheckList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dhcp")
    private Boolean dhcp;

    @Column(name = "dns")
    private Boolean dns;

    @Column(name = "active_directory")
    private Boolean activeDirectory;

    @Column(name = "shared_drives")
    private Boolean sharedDrives;

    @Column(name = "mail_server")
    private Boolean mailServer;

    @Column(name = "firewalls")
    private Boolean firewalls;

    @Column(name = "load_balancing")
    private Boolean loadBalancing;

    @Column(name = "network_monitoring")
    private Boolean networkMonitoring;

    @Column(name = "antivirus")
    private Boolean antivirus;

    @Column(name = "integrated_systems")
    private Boolean integratedSystems;

    @Column(name = "anti_spam")
    private Boolean antiSpam;

    @Column(name = "wpa")
    private Boolean wpa;

    @Column(name = "auto_backup")
    private Boolean autoBackup;

    @Column(name = "physical_security")
    private Boolean physicalSecurity;

    @Column(name = "storage_server")
    private Boolean storageServer;

    @Column(name = "security_audit")
    private Boolean securityAudit;

    @Column(name = "disaster_recovery")
    private Boolean disasterRecovery;

    @Column(name = "proxy_server")
    private Boolean proxyServer;

    @Column(name = "wds_server")
    private Boolean wdsServer;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "form_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = { "user", "organization" }, allowSetters = true)
    private Form form;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NetworkConfigCheckList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDhcp() {
        return this.dhcp;
    }

    public NetworkConfigCheckList dhcp(Boolean dhcp) {
        this.setDhcp(dhcp);
        return this;
    }

    public void setDhcp(Boolean dhcp) {
        this.dhcp = dhcp;
    }

    public Boolean getDns() {
        return this.dns;
    }

    public NetworkConfigCheckList dns(Boolean dns) {
        this.setDns(dns);
        return this;
    }

    public void setDns(Boolean dns) {
        this.dns = dns;
    }

    public Boolean getActiveDirectory() {
        return this.activeDirectory;
    }

    public NetworkConfigCheckList activeDirectory(Boolean activeDirectory) {
        this.setActiveDirectory(activeDirectory);
        return this;
    }

    public void setActiveDirectory(Boolean activeDirectory) {
        this.activeDirectory = activeDirectory;
    }

    public Boolean getSharedDrives() {
        return this.sharedDrives;
    }

    public NetworkConfigCheckList sharedDrives(Boolean sharedDrives) {
        this.setSharedDrives(sharedDrives);
        return this;
    }

    public void setSharedDrives(Boolean sharedDrives) {
        this.sharedDrives = sharedDrives;
    }

    public Boolean getMailServer() {
        return this.mailServer;
    }

    public NetworkConfigCheckList mailServer(Boolean mailServer) {
        this.setMailServer(mailServer);
        return this;
    }

    public void setMailServer(Boolean mailServer) {
        this.mailServer = mailServer;
    }

    public Boolean getFirewalls() {
        return this.firewalls;
    }

    public NetworkConfigCheckList firewalls(Boolean firewalls) {
        this.setFirewalls(firewalls);
        return this;
    }

    public void setFirewalls(Boolean firewalls) {
        this.firewalls = firewalls;
    }

    public Boolean getLoadBalancing() {
        return this.loadBalancing;
    }

    public NetworkConfigCheckList loadBalancing(Boolean loadBalancing) {
        this.setLoadBalancing(loadBalancing);
        return this;
    }

    public void setLoadBalancing(Boolean loadBalancing) {
        this.loadBalancing = loadBalancing;
    }

    public Boolean getNetworkMonitoring() {
        return this.networkMonitoring;
    }

    public NetworkConfigCheckList networkMonitoring(Boolean networkMonitoring) {
        this.setNetworkMonitoring(networkMonitoring);
        return this;
    }

    public void setNetworkMonitoring(Boolean networkMonitoring) {
        this.networkMonitoring = networkMonitoring;
    }

    public Boolean getAntivirus() {
        return this.antivirus;
    }

    public NetworkConfigCheckList antivirus(Boolean antivirus) {
        this.setAntivirus(antivirus);
        return this;
    }

    public void setAntivirus(Boolean antivirus) {
        this.antivirus = antivirus;
    }

    public Boolean getIntegratedSystems() {
        return this.integratedSystems;
    }

    public NetworkConfigCheckList integratedSystems(Boolean integratedSystems) {
        this.setIntegratedSystems(integratedSystems);
        return this;
    }

    public void setIntegratedSystems(Boolean integratedSystems) {
        this.integratedSystems = integratedSystems;
    }

    public Boolean getAntiSpam() {
        return this.antiSpam;
    }

    public NetworkConfigCheckList antiSpam(Boolean antiSpam) {
        this.setAntiSpam(antiSpam);
        return this;
    }

    public void setAntiSpam(Boolean antiSpam) {
        this.antiSpam = antiSpam;
    }

    public Boolean getWpa() {
        return this.wpa;
    }

    public NetworkConfigCheckList wpa(Boolean wpa) {
        this.setWpa(wpa);
        return this;
    }

    public void setWpa(Boolean wpa) {
        this.wpa = wpa;
    }

    public Boolean getAutoBackup() {
        return this.autoBackup;
    }

    public NetworkConfigCheckList autoBackup(Boolean autoBackup) {
        this.setAutoBackup(autoBackup);
        return this;
    }

    public void setAutoBackup(Boolean autoBackup) {
        this.autoBackup = autoBackup;
    }

    public Boolean getPhysicalSecurity() {
        return this.physicalSecurity;
    }

    public NetworkConfigCheckList physicalSecurity(Boolean physicalSecurity) {
        this.setPhysicalSecurity(physicalSecurity);
        return this;
    }

    public void setPhysicalSecurity(Boolean physicalSecurity) {
        this.physicalSecurity = physicalSecurity;
    }

    public Boolean getStorageServer() {
        return this.storageServer;
    }

    public NetworkConfigCheckList storageServer(Boolean storageServer) {
        this.setStorageServer(storageServer);
        return this;
    }

    public void setStorageServer(Boolean storageServer) {
        this.storageServer = storageServer;
    }

    public Boolean getSecurityAudit() {
        return this.securityAudit;
    }

    public NetworkConfigCheckList securityAudit(Boolean securityAudit) {
        this.setSecurityAudit(securityAudit);
        return this;
    }

    public void setSecurityAudit(Boolean securityAudit) {
        this.securityAudit = securityAudit;
    }

    public Boolean getDisasterRecovery() {
        return this.disasterRecovery;
    }

    public NetworkConfigCheckList disasterRecovery(Boolean disasterRecovery) {
        this.setDisasterRecovery(disasterRecovery);
        return this;
    }

    public void setDisasterRecovery(Boolean disasterRecovery) {
        this.disasterRecovery = disasterRecovery;
    }

    public Boolean getProxyServer() {
        return this.proxyServer;
    }

    public NetworkConfigCheckList proxyServer(Boolean proxyServer) {
        this.setProxyServer(proxyServer);
        return this;
    }

    public void setProxyServer(Boolean proxyServer) {
        this.proxyServer = proxyServer;
    }

    public Boolean getWdsServer() {
        return this.wdsServer;
    }

    public NetworkConfigCheckList wdsServer(Boolean wdsServer) {
        this.setWdsServer(wdsServer);
        return this;
    }

    public void setWdsServer(Boolean wdsServer) {
        this.wdsServer = wdsServer;
    }

    public Form getForm() {
        return this.form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public NetworkConfigCheckList form(Form form) {
        this.setForm(form);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NetworkConfigCheckList)) {
            return false;
        }
        return getId() != null && getId().equals(((NetworkConfigCheckList) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NetworkConfigCheckList{" +
            "id=" + getId() +
            ", dhcp='" + getDhcp() + "'" +
            ", dns='" + getDns() + "'" +
            ", activeDirectory='" + getActiveDirectory() + "'" +
            ", sharedDrives='" + getSharedDrives() + "'" +
            ", mailServer='" + getMailServer() + "'" +
            ", firewalls='" + getFirewalls() + "'" +
            ", loadBalancing='" + getLoadBalancing() + "'" +
            ", networkMonitoring='" + getNetworkMonitoring() + "'" +
            ", antivirus='" + getAntivirus() + "'" +
            ", integratedSystems='" + getIntegratedSystems() + "'" +
            ", antiSpam='" + getAntiSpam() + "'" +
            ", wpa='" + getWpa() + "'" +
            ", autoBackup='" + getAutoBackup() + "'" +
            ", physicalSecurity='" + getPhysicalSecurity() + "'" +
            ", storageServer='" + getStorageServer() + "'" +
            ", securityAudit='" + getSecurityAudit() + "'" +
            ", disasterRecovery='" + getDisasterRecovery() + "'" +
            ", proxyServer='" + getProxyServer() + "'" +
            ", wdsServer='" + getWdsServer() + "'" +
            "}";
    }
}
