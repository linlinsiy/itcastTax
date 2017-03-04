package cn.itcast.test.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * TPrivilege entity. @author MyEclipse Persistence Tools
 */

public class TPrivilege implements java.io.Serializable {

	// Fields

	private String priId;
	private String name;
	private Set TRolePris = new HashSet(0);

	// Constructors

	/** default constructor */
	public TPrivilege() {
	}

	/** minimal constructor */
	public TPrivilege(String name) {
		this.name = name;
	}

	/** full constructor */
	public TPrivilege(String name, Set TRolePris) {
		this.name = name;
		this.TRolePris = TRolePris;
	}

	// Property accessors

	public String getPriId() {
		return this.priId;
	}

	public void setPriId(String priId) {
		this.priId = priId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getTRolePris() {
		return this.TRolePris;
	}

	public void setTRolePris(Set TRolePris) {
		this.TRolePris = TRolePris;
	}

}