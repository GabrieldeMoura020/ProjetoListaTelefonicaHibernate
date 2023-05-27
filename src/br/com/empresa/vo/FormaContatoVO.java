package br.com.empresa.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name = "forcon")
public class FormaContatoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	// CÓDIGO DO CONTATO
	@Id
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "sq_forcon", sequenceName = "sq_forcon", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sq_forcon")
	private BigInteger id;

	// TIPO DE CONTATO
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 1)
	@Column(name = "tipcon", nullable = false, length = 1)
	private String tipcon;

	// DDD DO CONTATO
	@Basic(optional = true)
	@Size(max = 2)
	@Column(name = "dddcon", nullable = true, length = 2)
	private String dddcon;

	// NÚMERO DO CONTATO
	@Basic(optional = true)
	@Column(name = "numcon", nullable = true, length = 10)
	private String numcon;

	// EMAIL DE CONTATO
	@Basic(optional = true)
	@Column(name = "emacon", nullable = true, length = 400)
	private String emacon;

	// CONTATO
	@JoinColumn(name = "contat", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ContatoVO contat;
	

	public FormaContatoVO() {

		super();

	}

	public FormaContatoVO(BigInteger id) {
		super();
		this.id = id;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getTipcon() {
		return tipcon;
	}

	public void setTipcon(String tipcon) {
		this.tipcon = tipcon;
	}

	public String getDddcon() {
		return dddcon;
	}

	public void setDddcon(String dddcon) {
		this.dddcon = dddcon;
	}

	public String getNumcon() {
		return numcon;
	}

	public void setNumcon(String numcon) {
		this.numcon = numcon;
	}

	public String getEmacon() {
		return emacon;
	}

	public void setEmacon(String emacon) {
		this.emacon = emacon;
	}

	public ContatoVO getContat() {
		return contat;
	}

	public void setContat(ContatoVO contat) {
		this.contat = contat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormaContatoVO other = (FormaContatoVO) obj;
		return Objects.equals(contat, other.contat);
	}

	@Override
	public String toString() {
		return "ProdutoVO [id=" + id + "]";
	}

}
