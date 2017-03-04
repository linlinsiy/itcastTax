package cn.itcast.test.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * TRole entity. @author MyEclipse Persistence Tools
 */

public class TRole implements java.io.Serializable {

	// Fields

	private String roleId;
	private String name;
	private Set TRolePris = new HashSet(0);
	private Set TEmpRoles = new HashSet(0);

	// Constructors

	/** default constructor */
	public TRole() {
	}

	/** minimal constructor */
	public TRole(String name) {
		this.name = name;
	}

	/** full constructor */
	public TRole(String name, Set TRolePris, Set TEmpRoles) {
		this.name = name;
		this.TRolePris = TRolePris;
		this.TEmpRoles = TEmpRoles;
	}

	// Property accessors

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

	public Set getTEmpRoles() {
		return this.TEmpRoles;
	}

	public void setTEmpRoles(Set TEmpRoles) {
		this.TEmpRoles = TEmpRoles;
	}

}