/**
 *  Copyright (C) 2017 HUN.lemon. All right reserved.
 *
 *  
 *  https://lemon2013.github.io
 *
 *  created on 2017年5月24日 下午2:45:11
 *
 */
package com.cn.ipv6cloud.ceph.s3;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.cn.tbmsm.util.ConstantsUtil;
/**
 * <p>
 * 	Title: CephRGWS3
 * </p>
 * <p>
 * Description: 封装S3对应方法
 * </p>
 * <p>
 * Company: HUN.lemon
 * </p>
 * @author lemon
 * @date 2017年11月24日
 * 
 */
public class CephRGWS3 {
	/**
	 * S3客户端
	 */
	private AmazonS3 connection;
	/**
	 * 
	 * <p>
	 * Title: 构造函数
	 * </p>
	 * <p>
	 * Description:构造函数，根据访问密钥与安全密钥，访问端点生成S3操作客户端
	 * </p>
	 * @param accessKey 访问密钥
	 * @param secretKey 安全密钥
	 * @param endpoint 端点
	 */
	public CephRGWS3(String accessKey, String secretKey, String endpoint) {
		AWSCredentials credentials = null;
		credentials = new BasicAWSCredentials(accessKey, secretKey);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		connection = new AmazonS3Client(credentials, clientConfig);
		connection.setEndpoint(endpoint);
		S3ClientOptions s3options = new S3ClientOptions();
		s3options.setPathStyleAccess(true);
		connection.setS3ClientOptions(s3options);
	}
	/**
	 * 
	 * <p>
	 * Title: createBucket
	 * </p>
	 * <p>
	 * Description: 创建bucket
	 * </p>
	 * @param name bucket名称
	 * @return 返回为null表示创建失败
	 */
	public Bucket createBucket(String name) {
		try {
			return connection.createBucket(name);
		} catch (AmazonClientException e) {
			return null;
		}
	}
	/**
	 * 
	 * <p>
	 * Title: deleteBucket
	 * </p>
	 * <p>
	 * Description: 删除bucket
	 * </p>
	 * @param name 桶名
	 * @return false表示删除失败，桶不存在或者连接错误
	 */
	public boolean deleteBucket(String name) {
		try {
			if (connection.doesBucketExist(name)) {
				connection.deleteBucket(name);
				return true;
			} else {
				return false;
			}
		} catch (AmazonClientException e) {
			return false;
		}
	}
	/**
	 * 
	 * <p>
	 * Title: listBuckets
	 * </p>
	 * <p>
	 * Description: 获取S3用户所有桶信息
	 * </p>
	 * @return 返回桶列表
	 */
	public List<Bucket> listBuckets() {
		List<Bucket> lst = new ArrayList<Bucket>();
		try {
			lst = connection.listBuckets();
		} catch (AmazonClientException e) {
			System.out.println("获取用户桶列表失败");
		}
		return lst;
	}
	/**
	 * 
	 * <p>
	 * Title: getNumOfBuckets
	 * </p>
	 * <p>
	 * Description: 获取S3用户桶数目
	 * </p>
	 * @return 返回-1表示获取失败
	 */
	public int getNumOfBuckets() {
		try {
			return connection.listBuckets().size();
		} catch (AmazonClientException e) {
			return -1;
		}
	}

	/**
	 * 获取所有文件(不包含索引文件，共享文件)
	 * 
	 * @return
	 */
	/**
	 * 
	 * <p>
	 * Title: listAllfile
	 * </p>
	 * <p>
	 * Description: 获取所有文件(不包含索引文件，共享文件)
	 * </p>
	 * @return
	 */
	public List<S3ObjectSummary> listAllfile() {
		List<Bucket> bucketlst = new ArrayList<Bucket>();
		try {
			bucketlst = connection.listBuckets();
		} catch (AmazonClientException e) {

		}
		List<S3ObjectSummary> objList = new ArrayList<S3ObjectSummary>();
		for (Bucket bucket : bucketlst) {
			if (!bucket.getName().equals("share") && (!bucket.getName().equals("indexs"))) {
				try {
					ObjectListing objects = connection.listObjects(bucket.getName());
					objList.addAll(objects.getObjectSummaries());
				} catch (AmazonClientException e) {

				}

			}
		}
		return objList;
	}
	/**
	 * 
	 * <p>
	 * Title: listsharefile
	 * </p>
	 * <p>
	 * Description: 获取共享文件
	 * </p>
	 * @return 返回null表示获取失败
	 */
	public List<S3ObjectSummary> listsharefile() {
		List<S3ObjectSummary> objList = new ArrayList<S3ObjectSummary>();
		try {
			ObjectListing objects = connection.listObjects("share");
			objList.addAll(objects.getObjectSummaries());
			return objList;
		} catch (AmazonClientException e) {
			return null;
		}
	}
	/**
	 * 
	 * <p>
	 * Title: copyObject
	 * </p>
	 * <p>
	 * Description: 文件复制
	 * </p>
	 * @param srcBucketName  源文件bucket
	 * @param srcObjName 源文件
	 * @param desBucketName 目标bucket
	 * @param desObjName 目标文件
	 * @return 返回false表示复制文件失败
	 */
	public boolean copyObject(String srcBucketName, String srcObjName, String desBucketName, String desObjName) {
		try {
			connection.copyObject(srcBucketName, srcObjName, desBucketName, desObjName);
		} catch (AmazonClientException e) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 * <p>
	 * Title: getObject
	 * </p>
	 * <p>
	 * Description: 下载文件
	 * </p>
	 * @param bucketName 桶名
	 * @param objectName 文件名
	 * @param filePath 下载路径(文件存放路径)
	 * @return
	 */
	public boolean getObject(String bucketName, String objectName, String filePath) {
		try {
			connection.getObject(new GetObjectRequest(bucketName, objectName), new File(filePath));
			return true;
		} catch (AmazonClientException e) {
			return false;
		}
	}
	/**
	 * 
	 * <p>
	 * Title: getFileUrl
	 * </p>
	 * <p>
	 * Description: 获取公共下载链接，对外访问
	 * </p>
	 * @param bucketName 桶名
	 * @param objName 文件名
	 * @return 返回null表示获取下载链接失败
	 */
	public String getFileUrl(String bucketName, String objName) {
		// 获取一个request
		GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, objName);
		try {
			URL url = connection.generatePresignedUrl(urlRequest);
			return url.toString();
		} catch (AmazonClientException e) {
			return null;
		}
	}
	/**
	 * 
	 * <p>
	 * Title: deleteObject
	 * </p>
	 * <p>
	 * Description: 删除文件
	 * </p>
	 * @param bucketName 桶名
	 * @param objectName 文件名
	 * @return 返回false表示删除失败
	 */
	public boolean deleteObject(String bucketName, String objectName) {
		try {
			connection.deleteObject(bucketName, objectName);
			return true;
		}catch(AmazonClientException e) {
			return false;
		}
	}
	/**
	 * 
	 * <p>
	 * Title: uploadObject
	 * </p>
	 * <p>
	 * Description: 文件上传
	 * </p>
	 * @param bucktName 桶名
	 * @param objectName 文件名
	 * @param file 文件
	 * @return 返回false表示上传文件失败
	 */
	public boolean uploadObject(String bucktName, String objectName, File file) {
		try {
			connection.putObject(bucktName, objectName, file);
			return true;
		}catch(AmazonClientException e) {
			return false;
		}
	}
	/**
	 * 
	 * <p>
	 * Title: main
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param args
	 */
	public static void main(String[] args) {
		CephRGWS3 client = new CephRGWS3(ConstantsUtil.ADMIN_ACCESSKEY, ConstantsUtil.ADMIN_SECRETKEY,
				ConstantsUtil.ENDPOINT);
		client.getFileUrl("bucket01", " 2017本科毕业设计课题汇总.xls");
	}
}
