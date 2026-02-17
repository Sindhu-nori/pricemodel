package com.dtt.model;

import java.io.Serializable;
import org.hibernate.annotations.NaturalId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "client_projects",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "client_project_id")
    }
)
public class ClientProjects implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_project_pk")
    private Long id;

    @Column(name = "client_project_name", nullable = false, length = 100)
    private String clientProjectName;

    @NaturalId
    @Column(name = "client_project_id", nullable = false, length = 50, unique = true)
    private String clientProjectId;
    
//    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
//    private List<SystemRateCard> systemRateCards;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientProjectName() {
		return clientProjectName;
	}

	public void setClientProjectName(String clientProjectName) {
		this.clientProjectName = clientProjectName;
	}

	public String getClientProjectId() {
		return clientProjectId;
	}

	public void setClientProjectId(String clientProjectId) {
		this.clientProjectId = clientProjectId;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", clientProjectName=" + clientProjectName + ", clientProjectId="
				+ clientProjectId + "]";
	}
	

}
