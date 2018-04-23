package com.cn.ipv6cloud.ceph.s3.models;

import org.twonote.rgwadmin4j.model.User;

public class UserInfo {
	private String userId;
	private String displayName;
	private String email;
	private Integer maxBuckets;
	private String accessKey;
	private String secretKey;
	private int createdbuckets;
	private String size;
	private String objectssize;
	private boolean enabled;
	private long maxObjects;
	private long maxSizeKb;

	public UserInfo(User user) {
		userId = user.getUserId();
		displayName = user.getDisplayName();
		email = user.getEmail();
		maxBuckets = user.getMaxBuckets();
		accessKey = user.getS3Credentials().get(0).getAccessKey();
		secretKey = user.getS3Credentials().get(0).getSecretKey();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getMaxBuckets() {
		return maxBuckets;
	}

	public void setMaxBuckets(Integer maxBuckets) {
		this.maxBuckets = maxBuckets;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public int getCreatedbuckets() {
		return createdbuckets;
	}

	public void setCreatedbuckets(int createdbuckets) {
		this.createdbuckets = createdbuckets;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getObjectssize() {
		return objectssize;
	}

	public void setObjectssize(String objectsszie) {
		this.objectssize = objectsszie;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public long getMaxObjects() {
		return maxObjects;
	}

	public void setMaxObjects(long maxObjects) {
		this.maxObjects = maxObjects;
	}

	public long getMaxSizeKb() {
		return maxSizeKb;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setMaxSizeKb(long maxSizeKb) {
		this.maxSizeKb = maxSizeKb;
	}

}
