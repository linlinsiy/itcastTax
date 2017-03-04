package cn.itcast.test.entity;

/**
 * TRolePri entity. @author MyEclipse Persistence Tools
 */

public class TRolePri implements java.io.Serializable {

	// Fields

	private TRolePriId id;

	// Constructors

	/** default constructor */
	public TRolePri() {
	}

	/** full constructor */
	public TRolePri(TRolePriId id) {
		this.id = id;
	}

	// Property accessors

	public TRolePriId getId() {
		return this.id;
	}

	public void setId(TRolePriId id) {
		this.id = id;
	}

}