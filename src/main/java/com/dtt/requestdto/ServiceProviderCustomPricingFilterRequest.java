package com.dtt.requestdto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceProviderCustomPricingFilterRequest implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("organizationId")
	private String organizationId; // stakeholderId
	
	@JsonProperty("serviceName")
    private String serviceName;

	@JsonProperty("page")
    private int page = 0;
	
	@JsonProperty("size")
    private int size = 10;
	
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return " [organizationId=" + organizationId + ", serviceName="
				+ serviceName + ", page=" + page + ", size=" + size + "]";
	}
}
