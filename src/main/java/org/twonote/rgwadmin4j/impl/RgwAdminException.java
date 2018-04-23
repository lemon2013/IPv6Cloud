package org.twonote.rgwadmin4j.impl;

/**
 * Represents Radosgw admin errors
 *
 * <p>
 * Created by petertc on 3/24/17.
 */
public class RgwAdminException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2244425431079508389L;
	private final int statusCode;

	public RgwAdminException(int statusCode) {
		this.statusCode = statusCode;
	}

	public RgwAdminException(int statusCode, String messageCode) {
		super(messageCode);
		this.statusCode = statusCode;
	}

	public RgwAdminException(int statusCode, String messageCode, Throwable cause) {
		super(messageCode, cause);
		this.statusCode = statusCode;
	}

	public int status() {
		return statusCode;
	}
}
