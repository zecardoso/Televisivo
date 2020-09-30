package com.televisivo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TAB_TOKEN")
public class VerificarToken implements Serializable{

    private static final long serialVersionUID = -5170812526674501838L;

    private static final int DATA_EXPIRADA = 60 * 24;
    private Long id;
    private String token;
    private Usuario usuario;
    private Date dataExpirada;

    public VerificarToken() {
    }

    public VerificarToken(final String token) {
        this.token = token;
        this.dataExpirada = calcularPrazoDataExpirada(DATA_EXPIRADA);
    }

    public VerificarToken(final String token, final Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
        this.dataExpirada = calcularPrazoDataExpirada(DATA_EXPIRADA);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    
	public void setId(Long id) {
		this.id = id;
	}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id", foreignKey = @ForeignKey(name = "FK_VERIFIQUE_USUARIO"))
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataExpirada() {
        return dataExpirada;
    }

    public void setDataExpirada(Date dataExpirada) {
        this.dataExpirada = dataExpirada;
    }

    private Date calcularPrazoDataExpirada(int prazoEmMinutos) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, prazoEmMinutos);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.dataExpirada = calcularPrazoDataExpirada(DATA_EXPIRADA);
    }

   
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [String=").append(token).append("]").append("[Expires").append(dataExpirada).append("]");
        return builder.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerificarToken other = (VerificarToken) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}