/**
 *  Copyright (C) 2017 HUN.lemon. All right reserved.
 *
 *  
 *  https://lemon2013.github.io
 *
 *  created on 2017年5月24日 下午2:30:12
 *
 */
package com.cn.ipv6cloud.ceph.s3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.twonote.rgwadmin4j.RgwAdmin;
import org.twonote.rgwadmin4j.RgwAdminBuilder;
import org.twonote.rgwadmin4j.model.BucketInfo;
import org.twonote.rgwadmin4j.model.Quota;
import org.twonote.rgwadmin4j.model.UsageInfo;
import org.twonote.rgwadmin4j.model.User;

import com.cn.ipv6cloud.ceph.s3.models.UserInfo;
import com.cn.tbmsm.util.ConstantsUtil;

/**
 * 
 * <p>
 * Title: CephRGWAdmin
 * </p>
 * <p>
 * Description: ceph提供的原生restful接口，提供管理员相关操作
 * </p>
 * <p>
 * Company: HUN.lemon
 * </p>
 * 
 * @author lemon
 * @date 2017年5月24日
 *
 */
public class CephRGWAdmin {
	private RgwAdmin client;
	private String accessKey;
	private String secretKey;
	private String endpoint;

	/**
	 * 
	 * <p>
	 * Title: CephRGWAdmin
	 * </p>
	 * <p>
	 * Description: 构造器
	 * </p>
	 * 
	 * @param accessKey
	 * @param secretKey
	 * @param endpoint
	 */
	public CephRGWAdmin(String accessKey, String secretKey, String endpoint) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.endpoint = endpoint;
		client = new RgwAdminBuilder().accessKey(accessKey).secretKey(secretKey).endpoint(endpoint).build();
	}

	/**
	 * 
	 * <p>
	 * Title: createS3User
	 * </p>
	 * <p>
	 * Description: 创建S3用户
	 * </p>
	 * 
	 * @param userId
	 *            用户id，唯一
	 * @return 返回为null表示创建用户失败
	 */
	public User createS3User(String userId) {

		if (getUserInfo(userId) == null) {
			return client.createUser(userId);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * <p>
	 * Title: createS3User
	 * </p>
	 * <p>
	 * Description: 创建S3用户
	 * </p>
	 * 
	 * @param userId
	 * @param parameters
	 *            <ul>
	 *            <li>display-name: 别名，昵称
	 *            <li>email: 用户邮箱(仅简单格式判断)
	 *            <li>key-type: 选择密钥类型(swift, s3) *
	 *            <li>access-key: 指定访问密钥.
	 *            <li>secret-key: 指定密钥
	 *            <li>user-caps: 用户功能，如: usage=read, write; users=read
	 *            <li>generate-key: 生成一个新的密钥对并添加到现有的密钥环，默认为True， 如: True [True]
	 *            <li>max-buckets: 最大bucket拥有数默认为1000， Example: 500 [1000]
	 *            <li>suspended: 指定用户是否应该被暂停，默认不暂停， 如: False [False]
	 *            </ul>
	 * 
	 * @return
	 */
	public User createS3User(String userId, Map<String, String> parameters) {
		if (getUserInfo(userId) == null) {
			return client.createUser(userId, parameters);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * Title: modifyUser
	 * </p>
	 * <p>
	 * Description: 修改用户信息
	 * </p>
	 * 
	 * @param userId
	 *            用户id
	 * @param parameters
	 *            用户相应详细信息
	 *            <ul>
	 *            <li>display-name: 别名，昵称
	 *            <li>email: 用户邮箱(仅简单格式判断)
	 *            <li>key-type: 选择密钥类型(swift, s3) *
	 *            <li>access-key: 指定访问密钥.
	 *            <li>secret-key: 指定密钥
	 *            <li>user-caps: 用户功能，如: usage=read, write; users=read
	 *            <li>generate-key: 生成一个新的密钥对并添加到现有的密钥环，默认为True， 如: True [True]
	 *            <li>max-buckets: 最大bucket拥有数默认为1000， Example: 500 [1000]
	 *            <li>suspended: 指定用户是否应该被暂停，默认不暂停， 如: False [False]
	 *            </ul>
	 * @return
	 */
	public User modifyUser(String userId, Map<String, String> parameters) {
		return client.modifyUser(userId, parameters);
	}

	/**
	 * 
	 * <p>
	 * Title: removeUser
	 * </p>
	 * <p>
	 * Description: 移除S3用户,即使用户id不存在也不会报错或报异常
	 * </p>
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public void removeUser(String userId) {
		client.removeUser(userId);
	}

	/**
	 * 
	 * <p>
	 * Title: getUserInfo
	 * </p>
	 * <p>
	 * Description: 获取用户信息
	 * </p>
	 * 
	 * @param userId
	 * @return 如果返回null，表示获取用户失败
	 */
	public User getUserInfo(String userId) {
		return client.getUserInfo(userId).orElse(null);
	}

	/**
	 * 
	 * <p>
	 * Title: listBucket
	 * </p>
	 * <p>
	 * Description: 获取用户bucket信息
	 * </p>
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public List<String> listBucket(String userId) {
		return client.listBucket(userId);
	}

	/**
	 * 
	 * <p>
	 * Title: getNumOfObject
	 * </p>
	 * <p>
	 * Description: 获取用户的对象数
	 * </p>
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public String getNumOfObject(String userId) {
		BigInteger size = new BigInteger("0");
		List<BucketInfo> lstBucketInfo = client.listBucketInfo(userId);
		for (BucketInfo bucketinfo : lstBucketInfo) {
			
			BigInteger temp = new BigInteger("0");
			try {
				temp = new BigInteger(String.valueOf(bucketinfo.getUsage().getRgwMain().getNum_objects()));
			} catch (NullPointerException e) {

			}
			size = size.add(temp);
		}
		return size.toString();
	}
	/**
	 * 
	 * <p>
	 * Title: getNumOfObject
	 * </p>
	 * <p>
	 * Description: 获取用户对象数
	 * </p>
	 * @param listBucketInfo 用户桶信息列表
	 * @return
	 */
	public String getNumOfObject(List<BucketInfo> listBucketInfo) {
		BigInteger size = new BigInteger("0");
		for (BucketInfo bucketinfo : listBucketInfo) {
			BigInteger temp = new BigInteger("0");
			if(bucketinfo.getUsage().getRgwMain()!=null){
				temp = new BigInteger(String.valueOf(bucketinfo.getUsage().getRgwMain().getNum_objects()));
			} 
			size = size.add(temp);
		}
		return size.toString();
	}
	/**
	 * 
	 * <p>
	 * Title: getUsedSize
	 * </p>
	 * <p>
	 * Description: 获取用户已用量
	 * </p>
	 * 
	 * @param userId
	 * @return
	 */
	public String getUsedSize(String userId) {
		BigInteger size = new BigInteger("0");
		BigInteger base = new BigInteger("1024");
		List<BucketInfo> lstBucketInfo = client.listBucketInfo(userId);
		for (BucketInfo bucketinfo : lstBucketInfo) {
			BigInteger temp = new BigInteger("0");
			if(bucketinfo.getUsage().getRgwMain()!=null) {
				temp = new BigInteger(String.valueOf(bucketinfo.getUsage().getRgwMain().getSize_kb_actual()));
			} 
			size = size.add(temp.multiply(base));
		}
		return size.toString();
	}
	/**
	 * 
	 * <p>
	 * Title: getUsedSize
	 * </p>
	 * <p>
	 * Description: 获取用户使用信息
	 * </p>
	 * @param listBucketInfo 用户桶信息列表
	 * @return
	 */
	public String getUsedSize(List<BucketInfo> listBucketInfo) {
		BigInteger size = new BigInteger("0");
		BigInteger base = new BigInteger("1024");
		for (BucketInfo bucketinfo : listBucketInfo) {
			BigInteger temp = new BigInteger("0");
			if(bucketinfo.getUsage().getRgwMain()!=null) {
				temp = new BigInteger(String.valueOf(bucketinfo.getUsage().getRgwMain().getSize_kb_actual()));
			}
			size = size.add(temp);
		}
		return size.multiply(base).toString();
	}
	/**
	 * 
	 * <p>
	 * Title: listUser
	 * </p>
	 * <p>
	 * Description: 获取用户列表信息
	 * </p>
	 * 
	 * @return
	 */
	public List<UserInfo> listUser() {
		List<UserInfo> userlst = new ArrayList<UserInfo>();
		List<String> userid = client.listUser();
		for (String id : userid) {
			User user = getUserInfo(id);
			List<BucketInfo> lstBucketInfo = listUserBucketInfo(id);
			Optional<Quota> quota= client.getUserQuota(id);
			UserInfo userinfo = new UserInfo(user);
			userinfo.setCreatedbuckets(lstBucketInfo.size());
			userinfo.setSize(getUsedSize(lstBucketInfo));
			userinfo.setObjectssize(getNumOfObject(lstBucketInfo));
			userinfo.setMaxObjects(quota.get().getMaxObjects());
			userinfo.setMaxSizeKb(quota.get().getMaxSizeKb());
			userinfo.setEnabled(quota.get().getEnabled());
			userlst.add(userinfo);
		}
		return userlst;
	}
	/**
	 * 
	 * <p>
	 * Title: getUsage
	 * </p>
	 * <p>
	 * Description: 使用信息
	 * </p>
	 * 
	 * @return
	 */
	public UsageInfo getUsage() {
		return client.getUsage().orElse(null);
	}

	/**
	 * 
	 * <p>
	 * Title: getClient
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	public RgwAdmin getClient() {
		return client;
	}

	/**
	 * 
	 * <p>
	 * Title: setClient
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param client
	 */
	public void setClient(RgwAdmin client) {
		this.client = client;
	}

	/**
	 * 
	 * <p>
	 * Title: getAccessKey
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * 
	 * <p>
	 * Title: setAccessKey
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param accessKey
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * 
	 * <p>
	 * Title: getSecretKey
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * 
	 * <p>
	 * Title: setSecretKey
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param secretKey
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * 
	 * <p>
	 * Title: getEndpoint
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * 
	 * <p>
	 * Title: setEndpoint
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param endpoint
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public Optional<UsageInfo> getUserUsage(String userId) {
		return client.getUserUsage(userId);
	}
	/**
	 * 
	 * <p>
	 * Title: getBucketInfo
	 * </p>
	 * <p>
	 * Description: 获取bucket信息
	 * </p>
	 * @param bucketName 
	 * @return
	 */
	public Optional<BucketInfo> getBucketInfo(String bucketName){
		return client.getBucketInfo(bucketName);
	}
	/**
	 * 
	 * <p>
	 * Title: listUserBucketInfo
	 * </p>
	 * <p>
	 * Description: 获取用户桶列表信息
	 * </p>
	 * @param userId
	 * @return
	 */
	public List<BucketInfo> listUserBucketInfo(String userId) {
		return client.listBucketInfo(userId);
		
	}
	public static void main(String[] args) {
		CephRGWAdmin admin = new CephRGWAdmin(ConstantsUtil.ADMIN_ACCESSKEY, ConstantsUtil.ADMIN_SECRETKEY,
				ConstantsUtil.ADMIN_ENDPOINT);
		admin.removeUser("test");
//		admin.listUser();
		System.out.println("1");
	}
}
