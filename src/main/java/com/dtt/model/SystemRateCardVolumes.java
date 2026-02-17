package com.dtt.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_rate_card_volumes")
public class SystemRateCardVolumes implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "system_service_pk_fk", nullable = false)
    private SystemRateCard systemService;

    @Column(name = "min_volume", nullable = false)
    private Integer minVolume;

    @Column(name = "max_volume", nullable = false)
    private Integer maxVolume;

    @Column(name = "price_per_transaction", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerTransaction;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

	@Column(name = "status")
	private String status;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SystemRateCard getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemRateCard systemService) {
		this.systemService = systemService;
	}

	public Integer getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(Integer minVolume) {
		this.minVolume = minVolume;
	}

	public Integer getMaxVolume() {
		return maxVolume;
	}

	public void setMaxVolume(Integer maxVolume) {
		this.maxVolume = maxVolume;
	}

	public BigDecimal getPricePerTransaction() {
		return pricePerTransaction;
	}

	public void setPricePerTransaction(BigDecimal pricePerTransaction) {
		this.pricePerTransaction = pricePerTransaction;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "{" +
				"id=" + id +
				", systemService=" + systemService +
				", minVolume=" + minVolume +
				", maxVolume=" + maxVolume +
				", pricePerTransaction=" + pricePerTransaction +
				", createdOn=" + createdOn +
				", updatedOn=" + updatedOn +
				", status='" + status + '\'' +
				'}';
	}
}

