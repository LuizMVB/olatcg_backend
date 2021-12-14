package br.com.olatcg_backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SEQUENCE")
public class Sequence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_SEQUENCE")
    private Long id;

    @Column(name = "BASES")
    private String bases;

    @Column(name = "TYPE")
    private String type;

    public Sequence() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBases() {
        return bases;
    }

    public void setBases(String bases) {
        this.bases = bases;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
